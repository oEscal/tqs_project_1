package com.tqs1.api.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.PollutantConcentration;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class PollutantConcentrationDeserializerTest {

    @Test
    void testDeserialize() throws JsonProcessingException {

        String expectedUnits = "ppb";
        double expectedValue = 41.46;

        String json = "{\n" +
                "                    \"value\": " + expectedValue + ",\n" +
                "                    \"units\": \"" + expectedUnits + "\"\n" +
                "                }";

        PollutantConcentration expectedPollutantConcentration = new PollutantConcentration(expectedValue, expectedUnits);
        PollutantConcentration obtainedPollutantConcentration = new ObjectMapper().readValue(json,
                PollutantConcentration.class);

        assertThat(obtainedPollutantConcentration, is(expectedPollutantConcentration));
    }
}