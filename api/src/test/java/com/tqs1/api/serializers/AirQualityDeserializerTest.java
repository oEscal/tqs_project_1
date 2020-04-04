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
                "        },\n" +
                "        \"pollutants\": {\n" +
                "            \"co\": {\n" +
                "                \"display_name\": \"CO\",\n" +
                "                \"full_name\": \"Carbon monoxide\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": 99,\n" +
                "                        \"aqi_display\": \"99\",\n" +
                "                        \"color\": \"#009E3A\",\n" +
                "                        \"category\": \"Excellent air quality\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": 171.91,\n" +
                "                    \"units\": \"ppb\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"no2\": {\n" +
                "                \"display_name\": \"NO2\",\n" +
                "                \"full_name\": \"Nitrogen dioxide\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": 98,\n" +
                "                        \"aqi_display\": \"98\",\n" +
                "                        \"color\": \"#009E3A\",\n" +
                "                        \"category\": \"Excellent air quality\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": 2.6,\n" +
                "                    \"units\": \"ppb\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"o3\": {\n" +
                "                \"display_name\": \"O3\",\n" +
                "                \"full_name\": \"Ozone\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": 67,\n" +
                "                        \"aqi_display\": \"67\",\n" +
                "                        \"color\": \"#84CF33\",\n" +
                "                        \"category\": \"Good air quality\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": 41.68,\n" +
                "                    \"units\": \"ppb\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"pm10\": {\n" +
                "                \"display_name\": \"PM10\",\n" +
                "                \"full_name\": \"Inhalable particulate matter (<10µm)\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": 90,\n" +
                "                        \"aqi_display\": \"90\",\n" +
                "                        \"color\": \"#009E3A\",\n" +
                "                        \"category\": \"Excellent air quality\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": 11.12,\n" +
                "                    \"units\": \"ug/m3\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"pm25\": {\n" +
                "                \"display_name\": \"PM2.5\",\n" +
                "                \"full_name\": \"Fine particulate matter (<2.5µm)\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": 91,\n" +
                "                        \"aqi_display\": \"91\",\n" +
                "                        \"color\": \"#009E3A\",\n" +
                "                        \"category\": \"Excellent air quality\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": 5.73,\n" +
                "                    \"units\": \"ug/m3\"\n" +
                "                }\n" +
                "            },\n" +
                "            \"so2\": {\n" +
                "                \"display_name\": \"SO2\",\n" +
                "                \"full_name\": \"Sulfur dioxide\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": 99,\n" +
                "                        \"aqi_display\": \"99\",\n" +
                "                        \"color\": \"#009E3A\",\n" +
                "                        \"category\": \"Excellent air quality\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": 1.84,\n" +
                "                    \"units\": \"ppb\"\n" +
                "                }\n" +
                "            }\n" +
                "        }" +
                "       }" +
                "}";

        AirQuality expectedAirQuality = new AirQuality(expectedPollutant, expectedColor, expectedCategory,
                expectedScore);
        AirQuality obtained_air_quality = new ObjectMapper().readValue(json, AirQuality.class);

        assertThat(obtained_air_quality, is(expectedAirQuality));
    }
}