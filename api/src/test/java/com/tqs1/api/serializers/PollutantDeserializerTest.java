package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class PollutantDeserializerTest {

    @Test
    void testDeserialize() throws JsonProcessingException {

        String expectedSimpleName = "O3",
                expectedFullName = "Ozone",
                expectedColor = "#84CF33",
                expectedCategory = "Good air quality";
        int expectedScore = 67;
        String expectedUnits = "ppb";
        double expectedValue = 41.46;

        String json = "{\n" +
                "                \"display_name\": \"" + expectedSimpleName + "\",\n" +
                "                \"full_name\": \"" + expectedFullName + "\",\n" +
                "                \"aqi_information\": {\n" +
                "                    \"baqi\": {\n" +
                "                        \"display_name\": \"BreezoMeter AQI\",\n" +
                "                        \"aqi\": " + expectedScore + ",\n" +
                "                        \"aqi_display\": \"" + expectedScore + "\",\n" +
                "                        \"color\": \"" + expectedColor + "\",\n" +
                "                        \"category\": \"" + expectedCategory + "\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"concentration\": {\n" +
                "                    \"value\": " + expectedValue + ",\n" +
                "                    \"units\": \"" + expectedUnits + "\"\n" +
                "                }\n" +
                "            }";

        Pollutant expectedPollutant = new Pollutant(expectedSimpleName, expectedFullName, expectedColor,
                expectedCategory, expectedScore, new PollutantConcentration(expectedValue, expectedUnits));
        Pollutant obtainedPollutant = new ObjectMapper().readValue(json, Pollutant.class);

        assertThat(obtainedPollutant, is(expectedPollutant));
    }
}