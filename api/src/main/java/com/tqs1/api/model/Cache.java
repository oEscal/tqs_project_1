package com.tqs1.api.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


@Component
public class Cache {

    private static final int DEFAULT_MAX_SIZE = 5;

    private Map<ParametersEncapsulation, String> savedResponses;
    private int requests = 0;
    private int hits = 0;
    private int misses = 0;


    public Cache() {
        this.savedResponses = new LinkedHashMap<>(DEFAULT_MAX_SIZE);
    }

    public Cache(int maxSize) {
        this.savedResponses = new LinkedHashMap<>(maxSize);
    }

    public void addResponse(ParametersEncapsulation parameters, String response) {
        this.savedResponses.put(parameters, response);
    }

    public String getResponse(ParametersEncapsulation parameters) {
        String requestedResponse = this.savedResponses.get(parameters);

        this.requests++;
        if (requestedResponse == null)
            this.misses++;
        else
            this.hits++;

        return requestedResponse;
    }

    public int getRequests() {
        return requests;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }


    public static class ParametersEncapsulation {

        private String latitude;
        private String longitude;
        private int hours;

        public ParametersEncapsulation(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public ParametersEncapsulation(String latitude, String longitude, int hours) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.hours = hours;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ParametersEncapsulation)) return false;
            ParametersEncapsulation that = (ParametersEncapsulation) o;
            return hours == that.hours &&
                    Objects.equals(latitude, that.latitude) &&
                    Objects.equals(longitude, that.longitude);
        }

        @Override
        public int hashCode() {
            return Objects.hash(latitude, longitude, hours);
        }
    }
}
