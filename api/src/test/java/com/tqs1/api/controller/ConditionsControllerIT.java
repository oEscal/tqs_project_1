package com.tqs1.api.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tqs1.api.model.*;
import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.HttpClient;
import com.tqs1.api.utils.BuildBreezometerLink;
import com.tqs1.api.utils.JsonSamples;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

    private static final int MAX_HOURS_FORECAST = 95;
    private static final int MAX_HOURS_HISTORY = 168;
    private static final int MAX_VALUE_COORDINATE = 90;
    private static final int MIN_VALUE_COORDINATE = -90;

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
    private Gson gson;


    @LocalServerPort
    int randomServerPort;

    @Autowired
    private MockMvc mvc;

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


    /**
     * Success tests
     **/

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

    @Test
    void testCurrentConditionsAirQualityObject() throws Exception {

        RequestBuilder request = get("/current").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20");

        ResultActions resultActions = mvc.perform(request);

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

    @Test
    void testForecastAirQualityObject() throws Exception {

        RequestBuilder request = get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "2");

        testHourlyAirQualityObject(request);
    }

    @Test
    void testHistoricalAirQualityObject() throws Exception {

        RequestBuilder request = get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "2");

        testHourlyAirQualityObject(request);
    }

    /**
     * Failure tests
     **/

    @Test
    void testForecastZeroHours() throws Exception {

        RequestBuilder request = get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "0");

        testReceiveErrorMessage(request, MessageErrorDetails.ZERO_HOURS_ERROR);
    }

    @Test
    void testHistoricalZeroHours() throws Exception {

        RequestBuilder request = get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "0");

        testReceiveErrorMessage(request, MessageErrorDetails.ZERO_HOURS_ERROR);
    }

    @Test
    void testForecastExceedMaxHours() throws Exception {

        RequestBuilder request = get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20")
                .param("hours", String.valueOf(MAX_HOURS_FORECAST + 1));

        testReceiveErrorMessage(request, MessageErrorDetails.MAX_HOURS_FORECAST_ERROR);
    }

    @Test
    void testHistoricalExceedMaxHours() throws Exception {

        RequestBuilder request = get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20")
                .param("hours", String.valueOf(MAX_HOURS_HISTORY + 1));

        testReceiveErrorMessage(request, MessageErrorDetails.MAX_HOURS_HISTORY_ERROR);
    }

    @Test
    void testForecastNegativeHours() throws Exception {

        RequestBuilder request = get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20")
                .param("hours", "-1");

        testReceiveErrorMessage(request, MessageErrorDetails.NEGATIVE_HOURS_ERROR);
    }

    @Test
    void testHistoryNegativeHours() throws Exception {

        RequestBuilder request = get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20")
                .param("hours", "-1");

        testReceiveErrorMessage(request, MessageErrorDetails.NEGATIVE_HOURS_ERROR);
    }

    @Test
    void testCurrentConditionsMinCoordinates() throws Exception {

        MockHttpServletRequestBuilder request = get("/current").contentType(MediaType.APPLICATION_JSON);

        testCoordinatesLimit(request, MIN_VALUE_COORDINATE - 1, MessageErrorDetails.MIN_COORDINATE_ERROR);
    }

    @Test
    void testForecastNegativeCoordinates() throws Exception {

        MockHttpServletRequestBuilder request = get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("hours", "2");

        testCoordinatesLimit(request, MIN_VALUE_COORDINATE - 1, MessageErrorDetails.MIN_COORDINATE_ERROR);
    }

    @Test
    void testHistoryNegativeCoordinates() throws Exception {

        MockHttpServletRequestBuilder request = get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("hours", "2");

        testCoordinatesLimit(request, MIN_VALUE_COORDINATE - 1, MessageErrorDetails.MIN_COORDINATE_ERROR);
    }

    @Test
    void testCurrentConditionsMaxCoordinates() throws Exception {

        MockHttpServletRequestBuilder request = get("/current").contentType(MediaType.APPLICATION_JSON)
                .param("hours", "2");

        testCoordinatesLimit(request, MAX_VALUE_COORDINATE + 1, MessageErrorDetails.MAX_COORDINATE_ERROR);
    }

    @Test
    void testForecastMaxCoordinates() throws Exception {

        MockHttpServletRequestBuilder request = get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("hours", "2");

        testCoordinatesLimit(request, MAX_VALUE_COORDINATE + 1, MessageErrorDetails.MAX_COORDINATE_ERROR);
    }

    @Test
    void testHistoryMaxCoordinates() throws Exception {

        MockHttpServletRequestBuilder request = get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("hours", "2");

        testCoordinatesLimit(request, MAX_VALUE_COORDINATE + 1, MessageErrorDetails.MAX_COORDINATE_ERROR);
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

    private void testHourlyAirQualityObject(RequestBuilder request) throws Exception {
        ResultActions resultActions = mvc.perform(request);

        JsonElement jsonElement = gson.toJsonTree(
                new JSONParser().parse(resultActions.andReturn().getResponse().getContentAsString()));
        Message response = gson.fromJson(jsonElement, Message.class);

        List<AirQuality> expectedAirQualityList = new ArrayList<>();

        // add two air quality objects to list with two pollutants each
        AirQuality expectedAirQuality = new AirQuality(expectedPollutant[0], expectedColor[0], expectedCategory[0],
                expectedScore[0]);
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[0], expectedFullName[0],
                expectedPollutantColor[0], expectedPollutantCategory[0], expectedPollutantScore[0],
                new PollutantConcentration(expectedValue[0], expectedUnits[0])));
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[1], expectedFullName[1],
                expectedPollutantColor[1], expectedPollutantCategory[1], expectedPollutantScore[1],
                new PollutantConcentration(expectedValue[1], expectedUnits[1])));
        expectedAirQualityList.add(expectedAirQuality);

        expectedAirQuality = new AirQuality(expectedPollutant[1], expectedColor[1], expectedCategory[1],
                expectedScore[1]);
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[0], expectedFullName[0],
                expectedPollutantColor[0], expectedPollutantCategory[0], expectedPollutantScore[0],
                new PollutantConcentration(expectedValue[0], expectedUnits[0])));
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[1], expectedFullName[1],
                expectedPollutantColor[1], expectedPollutantCategory[1], expectedPollutantScore[1],
                new PollutantConcentration(expectedValue[1], expectedUnits[1])));
        expectedAirQualityList.add(expectedAirQuality);

        // test
        MatcherAssert.assertThat(response.getMultipleAirQuality(), Matchers.is(expectedAirQualityList));
    }

    private void testCoordinatesLimit(MockHttpServletRequestBuilder request, int value, MessageErrorDetails messageErrorDetails)
            throws Exception {

        // test for latitude
        testReceiveErrorMessage(request.param("lat", String.valueOf(value)).param("lon", "20"),
                messageErrorDetails);

        // test for longitude
        testReceiveErrorMessage(request.param("lat", "10").param("lon", String.valueOf(value)),
                messageErrorDetails);
    }

    private void testReceiveErrorMessage(RequestBuilder request, MessageErrorDetails expectedMessageErrorDetails) throws Exception {

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.airQuality", nullValue()))
                .andExpect(jsonPath("$.multipleAirQuality", nullValue()))
                .andExpect(jsonPath("$.detail", is(expectedMessageErrorDetails.getDetail())))
                .andExpect(jsonPath("$.success", is(false)));
    }
}
