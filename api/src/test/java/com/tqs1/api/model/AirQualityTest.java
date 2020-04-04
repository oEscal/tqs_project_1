package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class AirQualityTest {

    private AirQuality airQuality;
    private String dominantPollutantTest = "dominant_pollutant_test",
            colorTest = "color_test",
            categoryTest = "category_test";
    private int scoreTest = 38;

    @BeforeEach
    void setUp() {
        this.airQuality = new AirQuality(dominantPollutantTest, colorTest, categoryTest, scoreTest);
    }

    @Test
    void getAir_quality_color() {
        assertThat(airQuality.getAirQualityColor(), is(colorTest));
    }

    @Test
    void getAir_quality_category() {
        assertThat(airQuality.getAirQualityCategory(), is(categoryTest));
    }

    @Test
    void getAir_quality_score() {
        assertThat(airQuality.getAirQualityScore(), is(scoreTest));
    }

    @Test
    void getDominant_pollutant() {
        assertThat(airQuality.getDominantPollutant(), is(dominantPollutantTest));
    }

    @Test
    void testNoPollutantAddedNoPollutantsOnAirQuality() {
        assertThat(airQuality.getPollutants(), empty());
    }

    @Test
    void testAddPollutantsNumberPollutantsOnAirQuality() {

        this.airQuality.addPollutant(new Pollutant("simple_name_test", "full_name_test",
                "color_test", "category_test", 13,
                new PollutantConcentration(6.9, "unit_test")));
        this.airQuality.addPollutant(new Pollutant("simple_name_test2", "full_name_test2",
                "color_test2", "category_test2", 14,
                new PollutantConcentration(5, "unit_test2")));

        assertThat(airQuality.getPollutants(), hasSize(2));
    }

    @Test
    void testAddPollutantsPollutantsOnAirQuality() {

        Pollutant pollutant1 = new Pollutant("simple_name_test", "full_name_test",
                "color_test", "category_test", 13,
                new PollutantConcentration(6.9, "unit_test"));
        Pollutant pollutant2 = new Pollutant("simple_name_test2", "full_name_test2",
                "color_test2", "category_test2", 14,
                new PollutantConcentration(5, "unit_test2"));

        this.airQuality.addPollutant(pollutant1);
        this.airQuality.addPollutant(pollutant2);

        List<Pollutant> pollutants = airQuality.getPollutants();
        assertThat(pollutants, hasItem(pollutant1));
        assertThat(pollutants, hasItem(pollutant2));
    }

    @Test
    void testEquals() {
        AirQuality expectedAirQuality = new AirQuality(dominantPollutantTest, colorTest, categoryTest,
                scoreTest);

        assertThat(airQuality, is(expectedAirQuality));
    }
}
