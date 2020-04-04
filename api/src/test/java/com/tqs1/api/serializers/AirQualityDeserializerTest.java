package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.AirQuality;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class AirQualityDeserializerTest {

    @Test
    void testDeserialize() throws JsonProcessingException {

        String expectedColor = "#96D62B",
                expectedCategory = "Good air quality",
                expectedPollutant = "03";
        int expectedScore = 67;

        String json = "{" +
                "\"data\": {\n" +
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
                "        }" +
                "       }" +
                "}";

        AirQuality expectedAirQuality = new AirQuality(expectedPollutant, expectedColor, expectedCategory,
                expectedScore);
        AirQuality obtainedAirQuality = new ObjectMapper().readValue(json, AirQuality.class);

        assertThat(obtainedAirQuality, is(expectedAirQuality));
    }
}