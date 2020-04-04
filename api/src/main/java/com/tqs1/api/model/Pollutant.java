package com.tqs1.api.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tqs1.api.serializers.PollutantDeserializer;


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
}
