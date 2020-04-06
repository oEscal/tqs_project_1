package com.tqs1.api.api;

import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.HttpClient;
import com.tqs1.api.utils.BuildBreezometerLink;
import com.tqs1.api.utils.JsonSamples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(properties = "breezometer.token=test_token")
class ConditionsControllerTestIT {

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

    @Test
    void testCurrentConditionsJsonObject() throws Exception {

        RequestBuilder request =get("/current").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20");

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.dominantPollutant", is(expectedPollutant[0])))
                .andExpect(jsonPath("$.data.pollutants", hasSize(2)))
                .andExpect(jsonPath("$.data.pollutants[*].simpleName", containsInAnyOrder(expectedSimpleName)))
                .andExpect(jsonPath("$.data.pollutants[*].concentration.value", containsInAnyOrder(expectedValue[0], expectedValue[1])))
                .andExpect(jsonPath("$", hasKey("detail")))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void testForecastConditionsJsonObject() throws Exception {

        RequestBuilder request =get("/forecast").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "2");

        testHourlyConditionsJsonObject(request);
    }

    @Test
    void testHistoricalConditionsJsonObject() throws Exception {

        RequestBuilder request =get("/history").contentType(MediaType.APPLICATION_JSON)
                .param("lat", "10").param("lon", "20").param("hours", "2");

        testHourlyConditionsJsonObject(request);
    }

    private void testHourlyConditionsJsonObject(RequestBuilder request) throws Exception {

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[*].dominantPollutant", containsInAnyOrder(expectedPollutant)))
                .andExpect(jsonPath("$.data[*].pollutants", hasSize(2)))
                .andExpect(jsonPath("$.data[0].pollutants[*].simpleName", containsInAnyOrder(expectedSimpleName)))
                .andExpect(jsonPath("$.data[0].pollutants[*].concentration.value", containsInAnyOrder(expectedValue[0], expectedValue[1])))
                .andExpect(jsonPath("$", hasKey("detail")))
                .andExpect(jsonPath("$.success", is(true)));
    }
}
