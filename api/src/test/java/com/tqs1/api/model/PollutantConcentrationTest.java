package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class PollutantConcentrationTest {

    private PollutantConcentration pollutantConcentration;

    private double test_value = 0.6;
    private String test_unit = "test_unit";

    @BeforeEach
    void setupNewConcentration() {
        pollutantConcentration = new PollutantConcentration(test_value, test_unit);
    }

    @Test
    void testGetValue() {
        assertThat(pollutantConcentration.getValue(), is(test_value));
    }

    @Test
    void testGetUnits() {
        assertThat(pollutantConcentration.getUnits(), is(test_unit));
    }
}
