package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Pollutant;
import com.tqs1.api.model.PollutantConcentration;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.tqs1.api.utils.JsonSamples;


class AirQualityDeserializerTest {

    @Test
    void testDeserialize() throws JsonProcessingException {

        // for air quality
        String expectedColor = "#96D62B",
                expectedCategory = "Good air quality",
                expectedPollutant = "03",
                expectedDate = "2020-04-13T04:00:00Z";
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
                expectedPollutant, expectedDate, expectedSimpleName, expectedFullName, expectedPollutantScore,
                expectedPollutantColor, expectedPollutantCategory, expectedValue, expectedUnits);

        AirQuality expectedAirQuality = new AirQuality(expectedPollutant, expectedDate, expectedColor, expectedCategory,
                expectedScore);

        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[0], expectedFullName[0],
                expectedPollutantColor[0], expectedPollutantCategory[0], expectedPollutantScore[0],
                new PollutantConcentration(expectedValue[0], expectedUnits[0])));
        expectedAirQuality.addPollutant(new Pollutant(expectedSimpleName[1], expectedFullName[1],
                expectedPollutantColor[1], expectedPollutantCategory[1], expectedPollutantScore[1],
                new PollutantConcentration(expectedValue[1], expectedUnits[1])));

        AirQuality obtainedAirQuality = new ObjectMapper().readValue(json, AirQuality.class);

        assertThat(obtainedAirQuality, is(expectedAirQuality));
    }
}
