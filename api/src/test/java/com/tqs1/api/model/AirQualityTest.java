package com.tqs1.api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class AirQualityTest {

    private AirQuality airQuality;
    private String dominant_pollutant = "dominant_pollutant_test",
            air_quality_color = "color_test",
            air_quality_category = "category_test";
    private int air_quality_score = 38;

    @BeforeEach
    void setUp() {
        this.airQuality = new AirQuality(dominant_pollutant, air_quality_color, air_quality_category, air_quality_score);
    }

    @Test
    void getAir_quality_color() {
        assertThat(airQuality.getAir_quality_color(), is(air_quality_color));
    }

    @Test
    void getAir_quality_category() {
        assertThat(airQuality.getAir_quality_category(), is(air_quality_category));
    }

    @Test
    void getAir_quality_score() {
        assertThat(airQuality.getAir_quality_score(), is(air_quality_score));
    }

    @Test
    void getDominant_pollutant() {
        assertThat(airQuality.getDominant_pollutant(), is(dominant_pollutant));
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

        List<Pollutant> pollutants_added = airQuality.getPollutants();
        assertThat(pollutants_added, hasItem(pollutant1));
        assertThat(pollutants_added, hasItem(pollutant2));
    }

    @Test
    void testEquals() {
        AirQuality expected_air_quality = new AirQuality(dominant_pollutant, air_quality_color, air_quality_category,
                air_quality_score);

        assertThat(airQuality, is(expected_air_quality));
    }
}