package com.tqs1.api.model;

import java.util.Objects;


public abstract class SimpleAirQuality {

    private String airQualityColor;
    private String airQualityCategory;
    private int airQualityScore;


    public SimpleAirQuality(String airQualityColor, String airQualityCategory,
                            int airQualityScore) {

        this.airQualityColor = airQualityColor;
        this.airQualityCategory = airQualityCategory;
        this.airQualityScore = airQualityScore;
    }


    public String getAirQualityColor() {
        return airQualityColor;
    }

    public String getAirQualityCategory() {
        return airQualityCategory;
    }

    public int getAirQualityScore() {
        return airQualityScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleAirQuality that = (SimpleAirQuality) o;
        return getAirQualityScore() == that.getAirQualityScore() &&
                Objects.equals(getAirQualityColor(), that.getAirQualityColor()) &&
                Objects.equals(getAirQualityCategory(), that.getAirQualityCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAirQualityColor(), getAirQualityCategory(), getAirQualityScore());
    }

    @Override
    public String toString() {
        return "SimpleAirQuality{" +
                "air_quality_color='" + airQualityColor + '\'' +
                ", air_quality_category='" + airQualityCategory + '\'' +
                ", air_quality_score=" + airQualityScore +
                '}';
    }
}
