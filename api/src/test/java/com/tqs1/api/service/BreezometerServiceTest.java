package com.tqs1.api.service;

import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;
import com.tqs1.api.utils.JsonSamples;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "application-tests.properties")
class BreezometerServiceTest {

    @Value("${breezometer.token}")
    private String tokenTest;

    @Value("${breezometer.all_features}")
    private String breezometerAllFeaturesTest;


    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private BreezometerService breezometerService;


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

        // for air quality
        String expectedColor = "#96D62B",
                expectedCategory = "Good air quality",
                expectedPollutant = "o3";
        int expectedScore = 67;

        // for pollutants
        String[] expectedSimpleName = {"O3", "CO"},
                expectedFullName = {"Ozone", "Carbon monoxide"},
                expectedPollutantColor = {"#84CF33", "#009E3A"},
                expectedPollutantCategory = {"Good air quality", "Excellent air quality"};
        int[] expectedPollutantScore = {67, 99};
        String[] expectedUnits = {"ppb", "ppb"};
        double[] expectedValue = {41.46, 171.91};

        String json = JsonSamples.jsonAirQualityOnePollutantData(expectedScore, expectedColor, expectedCategory,
                expectedPollutant, expectedSimpleName, expectedFullName, expectedPollutantScore, expectedPollutantColor,
                expectedPollutantCategory, expectedValue, expectedUnits);
        json = "{\n" +
                "    \"metadata\": null,\n" +
                "    \"data\": " + json + ",\n" +
                "    \"error\": null\n" +
                "}";

        when(httpClient.get(createLinkString(BreezometerEndpoints.CURRENT_CONDITIONS, 10, 20)))
                .thenReturn(json);

        AirQuality returnedAirQuality = breezometerService.requestApi(BreezometerEndpoints.CURRENT_CONDITIONS, 10, 20);

        AirQuality expectedAirQuality = new AirQuality(expectedPollutant, expectedColor, expectedCategory,
                expectedScore);

        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[0], expectedFullName[0],
                expectedPollutantColor[0], expectedPollutantCategory[0], expectedPollutantScore[0],
                new PollutantConcentration(expectedValue[0], expectedUnits[0])));
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[1], expectedFullName[1],
                expectedPollutantColor[1], expectedPollutantCategory[1], expectedPollutantScore[1],
                new PollutantConcentration(expectedValue[1], expectedUnits[1])));

        assertThat(returnedAirQuality, is(expectedAirQuality));
    }


    private void testRequestHourly(BreezometerEndpoints endpoint) throws URISyntaxException, IOException, ParseException {

        // for air quality
        String[] expectedColor = {"#96D62B", "#8AD130"},
                expectedCategory = {"Good air quality", "Excellent air quality"},
                expectedPollutant = {"o3", "pm25"};
        int[] expectedScore = {67, 90};

        // for pollutants
        String[] expectedSimpleName = {"O3", "CO"},
                expectedFullName = {"Ozone", "Carbon monoxide"},
                expectedPollutantColor = {"#84CF33", "#009E3A"},
                expectedPollutantCategory = {"Good air quality", "Excellent air quality"};
        int[] expectedPollutantScore = {67, 99};
        String[] expectedUnits = {"ppb", "ppb"};
        double[] expectedValue = {41.46, 171.91};

        String json = JsonSamples.jsonAirQualitySeveralPollutantData(expectedScore, expectedColor, expectedCategory,
                expectedPollutant, expectedSimpleName, expectedFullName, expectedPollutantScore, expectedPollutantColor,
                expectedPollutantCategory, expectedValue, expectedUnits);

        json = "{\n" +
                "    \"metadata\": null,\n" +
                "    \"data\": " + json  + ",\n" +
                "    \"error\": null\n" +
                "}";

        when(httpClient.get(createLinkString(endpoint, 10, 20, 3)))
                .thenReturn(json);

        List<AirQuality> returnedAirQualityList = breezometerService.requestApi(endpoint, 10, 20, 3);

        List<AirQuality> expectedAirQualityList = new ArrayList<>();

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

        assertThat(returnedAirQualityList, is(expectedAirQualityList));
    }

    private String createLinkString(BreezometerEndpoints endpoint, double latitude, double longitude, int hours)
            throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder("https://api.breezometer.com/air-quality/v2/" + endpoint.getEndpoint());
        uriBuilder.addParameter("lat", String.valueOf(latitude));
        uriBuilder.addParameter("lon", String.valueOf(longitude));
        uriBuilder.addParameter("key", tokenTest);
        uriBuilder.addParameter("features", breezometerAllFeaturesTest);
        uriBuilder.addParameter("hours", String.valueOf(hours));

        return uriBuilder.build().toString();
    }

    private String createLinkString(BreezometerEndpoints endpoint, double latitude, double longitude)
            throws URISyntaxException {
        return createLinkString(endpoint, latitude, longitude, 0);
    }
}
