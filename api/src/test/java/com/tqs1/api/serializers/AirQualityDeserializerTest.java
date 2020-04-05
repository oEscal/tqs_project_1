package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class AirQualityDeserializerTest {

    @Test
    void testDeserialize() throws JsonProcessingException {

        // for air quality
        String expectedColor = "#96D62B",
                expectedCategory = "Good air quality",
                expectedPollutant = "03";
        int expectedScore = 67;

        // for pollutants
        String[] expectedSimpleName = {"O3", "CO"},
                expectedFullName = {"Ozone", "Carbon monoxide"},
                expectedPollutantColor = {"#84CF33", "#009E3A"},
                expectedPollutantCategory = {"Good air quality", "Excellent air quality"};
        int[] expectedPollutantScore = {67, 99};
        String[] expectedUnits = {"ppb", "ppb"};
        double[] expectedValue = {41.46, 171.91};


        String json = "{\n" +
                "        \"datetime\": \"2020-04-04T16:00:00Z\",\n" +
                "        \"data_available\": true,\n" +
                "        \"indexes\": {\n" +
                "            \"baqi\": {\n" +
                "                \"display_name\": \"BreezoMeter AQI\",\n" +
                "                \"aqi\": " + expectedScore + ",\n" +
                "                \"aqi_display\": \"" + expectedScore + "\",\n" +
                "                \"color\": \"" + expectedColor + "\",\n" +
                "                \"category\": \"" + expectedCategory + "\",\n" +
                "                \"dominant_pollutant\": \"" + expectedPollutant + "\"\n" +
                "            }" +
                "        },\n" +
                "        \"pollutants\": {\n" +
                "            \"co\": {\n" +
                "                \"display_name\": \"" + expectedSimpleName[0] + "\",\n" +
                "                \"full_name\": \"" + expectedFullName[0] + "\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": " + expectedPollutantScore[0] + ",\n" +
                "                        \"aqi_display\": \"" + expectedPollutantScore[0] + "\",\n" +
                "                        \"color\": \"" + expectedPollutantColor[0] + "\",\n" +
                "                        \"category\": \"" + expectedPollutantCategory[0] + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": " + expectedValue[0] + ",\n" +
                "                    \"units\": \"" + expectedUnits[0] + "\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"so2\": {\n" +
                "                \"display_name\": \"" + expectedSimpleName[1] + "\",\n" +
                "                \"full_name\": \"" + expectedFullName[1] + "\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": " + expectedPollutantScore[1] + ",\n" +
                "                        \"aqi_display\": \"" + expectedPollutantScore[1] + "\",\n" +
                "                        \"color\": \"" + expectedPollutantColor[1] + "\",\n" +
                "                        \"category\": \"" + expectedPollutantCategory[1] + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": " + expectedValue[1] + ",\n" +
                "                    \"units\": \"" + expectedUnits[1] + "\"\n" +
                "                }\n" +
                "            }\n" +
                "        }" +
                "}";

        AirQuality expectedAirQuality = new AirQuality(expectedPollutant, expectedColor, expectedCategory,
                expectedScore);

        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[0], expectedFullName[0],
                expectedPollutantColor[0], expectedPollutantCategory[0], expectedPollutantScore[0],
                new PollutantConcentration(expectedValue[0], expectedUnits[0])));
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[1], expectedFullName[1],
                expectedPollutantColor[1], expectedPollutantCategory[1], expectedPollutantScore[1],
                new PollutantConcentration(expectedValue[1], expectedUnits[1])));

        AirQuality obtained_air_quality = new ObjectMapper().readValue(json, AirQuality.class);

        assertThat(obtained_air_quality, is(expectedAirQuality));
    }
}
