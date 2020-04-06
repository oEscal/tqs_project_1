package com.tqs1.api.controller;

import com.fasterxml.jackson.databind.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Message;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;
import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.HttpClient;
import com.tqs1.api.utils.BuildBreezometerLink;
import com.tqs1.api.utils.JsonSamples;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "breezometer.token=test_token")
class ConditionsControllerIT {

    // for air quality
    private String[] expectedColor = {"#96D62B", "#8AD130"},
            expectedCategory = {"Good air quality", "Excellent air quality"},
            expectedPollutant = {"o3", "pm25"};
    private int[] expectedScore = {67, 90};

    // for pollutants
    private String[] expectedSimpleName = {"O3", "CO"},
            expectedFullName = {"Ozone", "Carbon monoxide"},
            expectedPollutantColor = {"#84CF33", "#009E3A"},
            expectedPollutantCategory = {"Good air quality", "Excellent air quality"};
    private int[] expectedPollutantScore = {67, 99};
    private String[] expectedUnits = {"ppb", "ppb"};
    private double[] expectedValue = {41.46, 171.91};

    @Value("${breezometer.token}")
    private String breezometerToken;

    @Value("${breezometer.all_features}")
    private String breezometerAllFeatures;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private HttpClient httpClient;


    @BeforeEach
    void setup() throws IOException, URISyntaxException {
        BuildBreezometerLink linkBuilder = new BuildBreezometerLink(breezometerToken, breezometerAllFeatures);
        String json;

        // for current conditions test
        json = JsonSamples.jsonAirQualityOnePollutantData(expectedScore[0], expectedColor[0], expectedCategory[0],
                expectedPollutant[0], expectedSimpleName, expectedFullName, expectedPollutantScore, expectedPollutantColor,
                expectedPollutantCategory, expectedValue, expectedUnits);
        json = "{\n" +
                "    \"metadata\": null,\n" +
                "    \"data\": " + json + ",\n" +
                "    \"error\": null\n" +
                "}";
        when(httpClient.get(linkBuilder.createLinkString(BreezometerEndpoints.CURRENT_CONDITIONS, 10, 20))).thenReturn(json);

        // for hourly conditions test
        json = JsonSamples.jsonAirQualitySeveralPollutantData(expectedScore, expectedColor, expectedCategory,
                expectedPollutant, expectedSimpleName, expectedFullName, expectedPollutantScore, expectedPollutantColor,
                expectedPollutantCategory, expectedValue, expectedUnits);
        json = "{\n" +
                "    \"metadata\": null,\n" +
                "    \"data\": " + json  + ",\n" +
                "    \"error\": null\n" +
                "}";
        when(httpClient.get(linkBuilder.createLinkString(BreezometerEndpoints.FORECAST_HOURLY, 10, 20, 2))).thenReturn(json);
        when(httpClient.get(linkBuilder.createLinkString(BreezometerEndpoints.HISTORICAL_HOURLY, 10, 20, 2))).thenReturn(json);
    }

    @Test
    void testCurrentConditionsJsonObject() throws Exception {

        RequestBuilder request = get("/current").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20");

        ResultActions resultActions = mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.airQuality.dominantPollutant", is(expectedPollutant[0])))
                .andExpect(jsonPath("$.airQuality.pollutants", hasSize(2)))
                .andExpect(jsonPath("$.airQuality.pollutants[*].simpleName", containsInAnyOrder(expectedSimpleName)))
                .andExpect(jsonPath("$.airQuality.pollutants[*].concentration.value", containsInAnyOrder(expectedValue[0], expectedValue[1])))
                .andExpect(jsonPath("$", hasKey("detail")))
                .andExpect(jsonPath("$.multipleAirQuality", nullValue()))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void testForecastConditionsJsonObject() throws Exception {

        RequestBuilder request = get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "2");

        testHourlyConditionsJsonObject(request);
    }

    @Test
    void testHistoricalConditionsJsonObject() throws Exception {

        RequestBuilder request = get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "2");

        testHourlyConditionsJsonObject(request);
    }

    @Autowired
    JsonDeserializer<AirQuality> deserializer;

    @Test
    void testCurrentConditionsAirQualityObject() throws Exception {

        RequestBuilder request = get("/current").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20");

        ResultActions resultActions = mvc.perform(request);

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(
                new JSONParser().parse(resultActions.andReturn().getResponse().getContentAsString()));
        Message response = gson.fromJson(jsonElement, Message.class);

        // create a air quality object with two pollutants
        AirQuality expectedAirQuality = new AirQuality(expectedPollutant[0], expectedColor[0], expectedCategory[0],
                expectedScore[0]);
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[0], expectedFullName[0],
                expectedPollutantColor[0], expectedPollutantCategory[0], expectedPollutantScore[0],
                new PollutantConcentration(expectedValue[0], expectedUnits[0])));
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[1], expectedFullName[1],
                expectedPollutantColor[1], expectedPollutantCategory[1], expectedPollutantScore[1],
                new PollutantConcentration(expectedValue[1], expectedUnits[1])));

        assertThat(response.getAirQuality(), is(expectedAirQuality));
    }

    private void testHourlyConditionsJsonObject(RequestBuilder request) throws Exception {

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.multipleAirQuality", hasSize(2)))
                .andExpect(jsonPath("$.multipleAirQuality[*].dominantPollutant", containsInAnyOrder(expectedPollutant)))
                .andExpect(jsonPath("$.multipleAirQuality[*].pollutants", hasSize(2)))
                .andExpect(jsonPath("$.multipleAirQuality[0].pollutants[*].simpleName", containsInAnyOrder(expectedSimpleName)))
                .andExpect(jsonPath("$.multipleAirQuality[0].pollutants[*].concentration.value", containsInAnyOrder(expectedValue[0], expectedValue[1])))
                .andExpect(jsonPath("$.airQuality", nullValue()))
                .andExpect(jsonPath("$", hasKey("detail")))
                .andExpect(jsonPath("$.success", is(true)));
    }
}
