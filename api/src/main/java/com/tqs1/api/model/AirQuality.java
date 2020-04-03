package com.tqs1.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tqs1.api.serializers.AirQualityDeserializer;

import java.util.ArrayList;


@JsonDeserialize(using = AirQualityDeserializer.class)
public class AirQuality extends SimpleAirQuality {

    private ArrayList<Pollutant> pollutants;

    public AirQuality(String dominant_pollutant, String air_quality_color, String air_quality_category,
                      int air_quality_score) {
        super(dominant_pollutant, air_quality_color, air_quality_category, air_quality_score);

        this.pollutants = new ArrayList<>();
    }

    public void addPollutant(Pollutant pollutant) {
        this.pollutants.add(pollutant);
    }

    public ArrayList<Pollutant> getPollutants() {
        return pollutants;
    }
}
