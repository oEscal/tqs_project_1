package com.tqs1.api.model;

public class Pollutant extends SimpleAirQuality {

    private String simple_name;
    private String full_name;
    private PollutantConcentration concentration;

    public Pollutant(String dominant_pollutant, String air_quality_color, String air_quality_category,
                     int air_quality_score) {
        super(dominant_pollutant, air_quality_color, air_quality_category, air_quality_score);
    }

    public String getSimple_name() {
        return simple_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public PollutantConcentration getConcentration() {
        return concentration;
    }
}
