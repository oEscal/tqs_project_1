package com.tqs1.api.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tqs1.api.serializers.PollutantDeserializer;

import java.util.Objects;


@JsonDeserialize(using = PollutantDeserializer.class)
public class Pollutant extends SimpleAirQuality {

    private String simpleName;
    private String fullName;
    private PollutantConcentration concentration;

    public Pollutant(String simpleName, String fullName, String airQualityColor, String airQualityCategory,
                     int airQualityScore, PollutantConcentration concentration) {
        super(airQualityColor, airQualityCategory, airQualityScore);

        this.simpleName = simpleName;
        this.fullName = fullName;
        this.concentration = concentration;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public String getFullName() {
        return fullName;
    }

    public PollutantConcentration getConcentration() {
        return concentration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pollutant)) return false;
        if (!super.equals(o)) return false;
        Pollutant pollutant = (Pollutant) o;
        return Objects.equals(getSimpleName(), pollutant.getSimpleName()) &&
                Objects.equals(getFullName(), pollutant.getFullName()) &&
                Objects.equals(getConcentration(), pollutant.getConcentration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSimpleName(), getFullName(), getConcentration());
    }

    @Override
    public String toString() {
        return "Pollutant{" +
                "simpleName='" + simpleName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", concentration=" + concentration +
                "} " + super.toString();
    }
}
