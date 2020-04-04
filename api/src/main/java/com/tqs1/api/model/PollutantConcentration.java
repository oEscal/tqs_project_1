package com.tqs1.api.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tqs1.api.serializers.PollutantConcentrationDeserializer;

import java.util.Objects;


@JsonDeserialize(using = PollutantConcentrationDeserializer.class)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PollutantConcentration)) return false;
        PollutantConcentration that = (PollutantConcentration) o;
        return Double.compare(that.getValue(), getValue()) == 0 &&
                Objects.equals(getUnits(), that.getUnits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getUnits());
    }

    @Override
    public String toString() {
        return "PollutantConcentration{" +
                "value=" + value +
                ", units='" + units + '\'' +
                '}';
    }
}
