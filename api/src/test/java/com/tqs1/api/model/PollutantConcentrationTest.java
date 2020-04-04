package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class PollutantConcentrationTest {

    private PollutantConcentration pollutantConcentration;

    private double testValue = 0.6;
    private String testUnit = "test_unit";

    @BeforeEach
    void setupNewConcentration() {
        pollutantConcentration = new PollutantConcentration(testValue, testUnit);
    }

    @Test
    void testGetValue() {
        assertThat(pollutantConcentration.getValue(), is(testValue));
    }

    @Test
    void testGetUnits() {
        assertThat(pollutantConcentration.getUnits(), is(testUnit));
    }

    @Test
    void testEquals() {
        PollutantConcentration expectedPollutantConcentration = new PollutantConcentration(testValue, testUnit);

        assertThat(pollutantConcentration, is(expectedPollutantConcentration));
    }
}
