package com.tqs1.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tqs1.api.serializers.AirQualityDeserializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


@JsonDeserialize(using = AirQualityDeserializer.class)
public class AirQuality extends SimpleAirQuality {

    private String dominantPollutant;
    private List<Pollutant> pollutants;

    public AirQuality(String dominantPollutant, String airQualityColor, String airQualityCategory,
                      int airQualityScore) {
        super(airQualityColor, airQualityCategory, airQualityScore);

        this.dominantPollutant = dominantPollutant;
        this.pollutants = new ArrayList<>();
    }

    public void addPollutant(Pollutant pollutant) {
        this.pollutants.add(pollutant);
    }

    public List<Pollutant> getPollutants() {
        return pollutants;
    }

    public String getDominantPollutant() {
        return dominantPollutant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AirQuality)) return false;
        if (!super.equals(o)) return false;
        AirQuality that = (AirQuality) o;
        return Objects.equals(getDominantPollutant(), that.getDominantPollutant()) &&
                Objects.equals(new HashSet<>(getPollutants()), new HashSet<>(that.getPollutants()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDominantPollutant(), getPollutants());
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "dominant_pollutant='" + dominantPollutant + '\'' +
                ", pollutants=" + pollutants +
                "} " + super.toString();
    }
}
