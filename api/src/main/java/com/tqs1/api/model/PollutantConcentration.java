package com.tqs1.api.model;

public class PollutantConcentration {

    private double value;
    private String units;

    public PollutantConcentration(double value, String units) {
        this.value = value;
        this.units = units;
    }

    public double getValue() {
        return value;
    }

    public String getUnits() {
        return units;
    }
}
