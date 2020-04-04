package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class PollutantTest {

    private Pollutant pollutant;

    private String simple_name = "simple_name_test",
            full_name = "full_name_test",
            air_quality_color = "color_test",
            air_quality_category = "category_test";
    private int air_quality_score = 38;
    private PollutantConcentration pollutantConcentration = new PollutantConcentration(10.59, "unit_test");


    @BeforeEach
    void setupPollutant() {
        this.pollutant = new Pollutant(simple_name, full_name, air_quality_color, air_quality_category,
                air_quality_score, pollutantConcentration);
    }

    @Test
    void testGetSimple_name() {
        assertThat(pollutant.getSimple_name(), is(simple_name));
    }

    @Test
    void testGetFull_name() {
        assertThat(pollutant.getFull_name(), is(full_name));
    }

    @Test
    void testGetConcentration() {
        assertThat(pollutant.getConcentration(), is(pollutantConcentration));
    }

    @Test
    void testGetAir_quality_category() {
        assertThat(pollutant.getAir_quality_category(), is(air_quality_category));
    }

    @Test
    void testGetAir_quality_color() {
        assertThat(pollutant.getAir_quality_color(), is(air_quality_color));
    }

    @Test
    void testGetAir_quality_score() {
        assertThat(pollutant.getAir_quality_score(), is(air_quality_score));
    }
}
