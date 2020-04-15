package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


class PollutantConcentrationTest {

    private PollutantConcentration pollutantConcentration;

    private double testValue = 0.6;
    private String testUnit = "test_unit";

    @BeforeEach
    void setupNewConcentration() {
        pollutantConcentration = new PollutantConcentration(testValue, testUnit);
    }

    @Test
    void testEqualsDifferentObjects() {
        PollutantConcentration expectedPollutantConcentration = new PollutantConcentration(testValue, testUnit);

        assertThat(pollutantConcentration, is(expectedPollutantConcentration));
    }

    @Test
    void testEqualsSameObjects() {
        assertThat(pollutantConcentration, is(pollutantConcentration));
    }

    @Test
    void testNotEquals() {
        PollutantConcentration notExpectedPollutantConcentration = new PollutantConcentration(90.123, "unit2");

        assertThat(pollutantConcentration, not(notExpectedPollutantConcentration));
    }

    @Test
    void testNotEqualsDifferentClass() {
        Object notExpected = new Object();

        assertThat(pollutantConcentration, not(notExpected));
    }
}
