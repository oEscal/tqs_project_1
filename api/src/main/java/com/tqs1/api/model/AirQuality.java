package com.tqs1.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tqs1.api.serializers.AirQualityDeserializer;

import java.util.ArrayList;
import java.util.List;


@JsonDeserialize(using = AirQualityDeserializer.class)
public class AirQuality extends SimpleAirQuality {

    private String dominant_pollutant;
    private List<Pollutant> pollutants;

    public AirQuality(String dominant_pollutant, String air_quality_color, String air_quality_category,
                      int air_quality_score) {
        super(air_quality_color, air_quality_category, air_quality_score);

        this.dominant_pollutant = dominant_pollutant;
        this.pollutants = new ArrayList<>();
    }

    public void addPollutant(Pollutant pollutant) {
        this.pollutants.add(pollutant);
    }

    public List<Pollutant> getPollutants() {
        return pollutants;
    }

    public String getDominant_pollutant() {
        return dominant_pollutant;
    }
}
