package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


class PollutantTest {

    private Pollutant pollutant;

    private String simpleNameTest = "simple_name_test",
            fullNameTest = "full_name_test",
            colorTest = "color_test",
            categoryTest = "category_test",
            date = "date_test";
    private int scoreTest = 38;
    private PollutantConcentration pollutantConcentration = new PollutantConcentration(10.59, "unit_test");


    @BeforeEach
    void setupPollutant() {
        this.pollutant = new Pollutant(simpleNameTest, fullNameTest, colorTest, categoryTest,
                scoreTest, pollutantConcentration);
    }

    @Test
    void testEqualsDifferentObjects() {
        Pollutant expectedPollutant = new Pollutant(simpleNameTest, fullNameTest, colorTest, categoryTest,
                scoreTest, pollutantConcentration);

        assertThat(pollutant, is(expectedPollutant));
    }

    @Test
    void testEqualsSameObjects() {
        assertThat(pollutant, is(pollutant));
    }

    @Test
    void testNotEqualsSameClassDifferentPollutantDifferentConcentration() {
        Pollutant notExpectedPollutant = new Pollutant("simple_name2", "full_name2", "colo2",
                "category2", 100, new PollutantConcentration(10.3, "unit2"));

        assertThat(pollutant, not(notExpectedPollutant));
    }

    @Test
    void testNotEqualsSameClassDifferentPollutantSameConcentration() {
        Pollutant notExpectedPollutant = new Pollutant("simple_name2", "full_name2", "colo2",
                "category2", 100, pollutantConcentration);

        assertThat(pollutant, not(notExpectedPollutant));
    }

    @Test
    void testNotEqualsSameClassSamePollutantDifferentConcentration() {
        Pollutant notExpectedPollutant = new Pollutant(simpleNameTest, fullNameTest, colorTest, categoryTest,
                scoreTest, new PollutantConcentration(10.3, "unit2"));

        assertThat(pollutant, not(notExpectedPollutant));
    }

    @Test
    void testNotEqualsDifferentClass() {
        AirQuality notExpected = new AirQuality(simpleNameTest, date, colorTest, categoryTest, scoreTest);

        assertThat(pollutant, not(notExpected));
    }
}
