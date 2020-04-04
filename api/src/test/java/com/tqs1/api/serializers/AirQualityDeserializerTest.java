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

        String expected_color = "#96D62B",
                expected_category = "Good air quality",
                expected_pollutant = "03";
        int expected_score = 67;

        String json = "{" +
                "\"data\": {\n" +
                "        \"datetime\": \"2020-04-04T16:00:00Z\",\n" +
                "        \"data_available\": true,\n" +
                "        \"indexes\": {\n" +
                "            \"baqi\": {\n" +
                "                \"display_name\": \"BreezoMeter AQI\",\n" +
                "                \"aqi\": " + expected_score + ",\n" +
                "                \"aqi_display\": \"" + expected_score + "\",\n" +
                "                \"color\": \"" + expected_color + "\",\n" +
                "                \"category\": \"" + expected_category + "\",\n" +
                "                \"dominant_pollutant\": \"" + expected_pollutant + "\"\n" +
                "            }" +
                "        }" +
                "       }" +
                "}";

        AirQuality expected_air_quality = new AirQuality(expected_pollutant, expected_color, expected_category,
                expected_score);
        AirQuality obtained_air_quality = new ObjectMapper().readValue(json, AirQuality.class);

        assertThat(obtained_air_quality, is(expected_air_quality));
    }
}