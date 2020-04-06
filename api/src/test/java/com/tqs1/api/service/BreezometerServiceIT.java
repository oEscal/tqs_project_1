package com.tqs1.api.service;

import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;
import com.tqs1.api.utils.BuildBreezometerLink;
import com.tqs1.api.utils.JsonSamples;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(properties = "breezometer.token=test_token")
class BreezometerServiceIT {

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
    private BreezometerService breezometerService;

    // @Mock(lenient = true)
    @MockBean
    private HttpClient httpClient;


    @BeforeEach
    void setup() throws URISyntaxException, IOException {

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
        when(httpClient.get(linkBuilder.createLinkString(BreezometerEndpoints.CURRENT_CONDITIONS, 10, 20)))
                .thenReturn(json);

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
    void testHistoricalRequest() throws ParseException, IOException, URISyntaxException {
        testRequestHourly(BreezometerEndpoints.HISTORICAL_HOURLY);
    }

    @Test
    void testForecastRequest() throws ParseException, IOException, URISyntaxException {
        testRequestHourly(BreezometerEndpoints.FORECAST_HOURLY);
    }

    @Test
    void testCurrentConditionsRequest() throws ParseException, IOException, URISyntaxException {

        // get resultant data from the message returned
        AirQuality returnedAirQuality = breezometerService.requestApi(BreezometerEndpoints.CURRENT_CONDITIONS, 10, 20)
                .getData();

        // create a air quality object with two pollutants
        AirQuality expectedAirQuality = new AirQuality(expectedPollutant[0], expectedColor[0], expectedCategory[0],
                expectedScore[0]);
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[0], expectedFullName[0],
                expectedPollutantColor[0], expectedPollutantCategory[0], expectedPollutantScore[0],
                new PollutantConcentration(expectedValue[0], expectedUnits[0])));
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[1], expectedFullName[1],
                expectedPollutantColor[1], expectedPollutantCategory[1], expectedPollutantScore[1],
                new PollutantConcentration(expectedValue[1], expectedUnits[1])));

        // test
        assertThat(returnedAirQuality, is(expectedAirQuality));
    }

    private void testRequestHourly(BreezometerEndpoints endpoint) throws URISyntaxException, IOException, ParseException {

        // get resultant data from the message returned
        List<AirQuality> returnedAirQualityList = breezometerService.requestApi(endpoint, 10, 20, 2)
                .getData();

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
        assertThat(returnedAirQualityList, is(expectedAirQualityList));
    }
}
