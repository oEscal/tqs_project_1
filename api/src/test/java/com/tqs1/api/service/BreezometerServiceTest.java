package com.tqs1.api.service;

import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;
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
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
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
    void testFindAddressForLocation() throws URISyntaxException, IOException, ParseException {

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

        when(httpClient.get(any(String.class)))
                .thenReturn("{\n" +
                        "    \"metadata\": null,\n" +
                        "    \"data\": [\n" +
                        "        {\n" +
                        "            \"datetime\": \"2020-04-05T03:00:00Z\",\n" +
                        "            \"data_available\": true,\n" +
                        "            \"indexes\": {\n" +
                        "                \"baqi\": {\n" +
                        "                    \"display_name\": \"BreezoMeter AQI\",\n" +
                        "                    \"aqi\": " + expectedScore[0] + ",\n" +
                        "                    \"aqi_display\": \"" + expectedScore[0] + "\",\n" +
                        "                    \"color\": \"" + expectedColor[0] + "\",\n" +
                        "                    \"category\": \"" + expectedCategory[0] + "\",\n" +
                        "                    \"dominant_pollutant\": \"" + expectedPollutant[0] + "\"\n" +
                        "                },\n" +
                        "                \"fra_atmo\": {\n" +
                        "                    \"display_name\": \"AQI (FR)\",\n" +
                        "                    \"aqi\": 5,\n" +
                        "                    \"aqi_display\": \"5\",\n" +
                        "                    \"color\": \"#FFA500\",\n" +
                        "                    \"category\": \"Average air quality\",\n" +
                        "                    \"dominant_pollutant\": \"pm10\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            \"pollutants\": {\n" +
                        "                \"co\": {\n" +
                        "                    \"display_name\": \"" + expectedSimpleName[0] + "\",\n" +
                        "                    \"full_name\": \"" + expectedFullName[0] + "\",\n" +
                        "                    \"aqi_information\": {\n" +
                        "                        \"baqi\": {\n" +
                        "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                        "                            \"aqi\": " + expectedPollutantScore[0] + ",\n" +
                        "                            \"aqi_display\": \"" + expectedPollutantScore[0] + "\",\n" +
                        "                            \"color\": \"" + expectedPollutantColor[0] + "\",\n" +
                        "                            \"category\": \"" + expectedPollutantCategory[0] + "\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    \"concentration\": {\n" +
                        "                        \"value\": " + expectedValue[0] + ",\n" +
                        "                        \"units\": \"" + expectedUnits[0] + "\"\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                \"o3\": {\n" +
                        "                    \"display_name\": \"" + expectedSimpleName[1] + "\",\n" +
                        "                    \"full_name\": \"" + expectedFullName[1] + "\",\n" +
                        "                    \"aqi_information\": {\n" +
                        "                        \"baqi\": {\n" +
                        "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                        "                            \"aqi\": " + expectedPollutantScore[1] + ",\n" +
                        "                            \"aqi_display\": \"" + expectedPollutantScore[1] + "\",\n" +
                        "                            \"color\": \"" + expectedPollutantColor[1] + "\",\n" +
                        "                            \"category\": \"" + expectedPollutantCategory[1] + "\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    \"concentration\": {\n" +
                        "                        \"value\": " + expectedValue[1] + ",\n" +
                        "                        \"units\": \"" + expectedUnits[1] + "\"\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            }\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"datetime\": \"2020-04-05T04:00:00Z\",\n" +
                        "            \"data_available\": true,\n" +
                        "            \"indexes\": {\n" +
                        "                \"baqi\": {\n" +
                        "                    \"display_name\": \"BreezoMeter AQI\",\n" +
                        "                    \"aqi\": " + expectedScore[1] + ",\n" +
                        "                    \"aqi_display\": \"" + expectedScore[1] + "\",\n" +
                        "                    \"color\": \"" + expectedColor[1] + "\",\n" +
                        "                    \"category\": \"" + expectedCategory[1] + "\",\n" +
                        "                    \"dominant_pollutant\": \"" + expectedPollutant[1] + "\"\n" +
                        "                },\n" +
                        "                \"fra_atmo\": {\n" +
                        "                    \"display_name\": \"AQI (FR)\",\n" +
                        "                    \"aqi\": 5,\n" +
                        "                    \"aqi_display\": \"5\",\n" +
                        "                    \"color\": \"#FFA500\",\n" +
                        "                    \"category\": \"Average air quality\",\n" +
                        "                    \"dominant_pollutant\": \"pm10\"\n" +
                        "                }\n" +
                        "            },\n" +
                        "            \"pollutants\": {\n" +
                        "                \"co\": {\n" +
                        "                    \"display_name\": \"" + expectedSimpleName[0] + "\",\n" +
                        "                    \"full_name\": \"" + expectedFullName[0] + "\",\n" +
                        "                    \"aqi_information\": {\n" +
                        "                        \"baqi\": {\n" +
                        "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                        "                            \"aqi\": " + expectedPollutantScore[0] + ",\n" +
                        "                            \"aqi_display\": \"" + expectedPollutantScore[0] + "\",\n" +
                        "                            \"color\": \"" + expectedPollutantColor[0] + "\",\n" +
                        "                            \"category\": \"" + expectedPollutantCategory[0] + "\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    \"concentration\": {\n" +
                        "                        \"value\": " + expectedValue[0] + ",\n" +
                        "                        \"units\": \"" + expectedUnits[0] + "\"\n" +
                        "                    }\n" +
                        "                },\n" +
                        "                \"o3\": {\n" +
                        "                    \"display_name\": \"" + expectedSimpleName[1] + "\",\n" +
                        "                    \"full_name\": \"" + expectedFullName[1] + "\",\n" +
                        "                    \"aqi_information\": {\n" +
                        "                        \"baqi\": {\n" +
                        "                            \"display_name\": \"BreezoMeter AQI\",\n" +
                        "                            \"aqi\": " + expectedPollutantScore[1] + ",\n" +
                        "                            \"aqi_display\": \"" + expectedPollutantScore[1] + "\",\n" +
                        "                            \"color\": \"" + expectedPollutantColor[1] + "\",\n" +
                        "                            \"category\": \"" + expectedPollutantCategory[1] + "\"\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    \"concentration\": {\n" +
                        "                        \"value\": " + expectedValue[1] + ",\n" +
                        "                        \"units\": \"" + expectedUnits[1] + "\"\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"error\": null\n" +
                        "}");

        List<AirQuality> returnedAirQualityList = breezometerService.requestApi(BreezometerEndpoints.HISTORICAL_HOURLY,
                10, 20, 3);

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
}
