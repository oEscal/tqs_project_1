package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class PollutantTest {

    private Pollutant pollutant;

    private String simpleNameTest = "simple_name_test",
            fullNameTest = "full_name_test",
            colorTest = "color_test",
            categoryTest = "category_test";
    private int scoreTest = 38;
    private PollutantConcentration pollutantConcentration = new PollutantConcentration(10.59, "unit_test");


    @BeforeEach
    void setupPollutant() {
        this.pollutant = new Pollutant(simpleNameTest, fullNameTest, colorTest, categoryTest,
                scoreTest, pollutantConcentration);
    }

    @Test
    void testGetSimple_name() {
        assertThat(pollutant.getSimpleName(), is(simpleNameTest));
    }

    @Test
    void testGetFull_name() {
        assertThat(pollutant.getFullName(), is(fullNameTest));
    }

    @Test
    void testGetConcentration() {
        assertThat(pollutant.getConcentration(), is(pollutantConcentration));
    }

    @Test
    void testGetAir_quality_category() {
        assertThat(pollutant.getAirQualityCategory(), is(categoryTest));
    }

    @Test
    void testGetAir_quality_color() {
        assertThat(pollutant.getAirQualityColor(), is(colorTest));
    }

    @Test
    void testGetAir_quality_score() {
        assertThat(pollutant.getAirQualityScore(), is(scoreTest));
    }
}
