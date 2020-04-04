package com.tqs1.api.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tqs1.api.serializers.AirQualityDeserializer;

public abstract class SimpleAirQuality {

    private String air_quality_color,
            air_quality_category;
    private int air_quality_score;


    public SimpleAirQuality(String air_quality_color, String air_quality_category,
                              int air_quality_score) {
        this.air_quality_color = air_quality_color;
        this.air_quality_category = air_quality_category;
        this.air_quality_score = air_quality_score;
    }


    public String getAir_quality_color() {
        return air_quality_color;
    }

    public String getAir_quality_category() {
        return air_quality_category;
    }

    public int getAir_quality_score() {
        return air_quality_score;
    }
}
