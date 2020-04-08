package com.tqs1.api.model;

import com.tqs1.api.service.BreezometerEndpoints;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


@Component
public class Cache {

    private static final int DEFAULT_MAX_SIZE = 5;

    private Map<ParametersEncapsulation, String> savedResponses = new LinkedHashMap<ParametersEncapsulation, String>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<ParametersEncapsulation, String> eldest)
        {
            return size() > maxSize;
        }
    };
    private int maxSize = DEFAULT_MAX_SIZE;
    private int requests = 0;
    private int hits = 0;
    private int misses = 0;


    public Cache() { }

    public Cache(int maxSize) {
        this.maxSize = maxSize;
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

    public int getSize() {
        return this.savedResponses.size();
    }

    @Override
    public String toString() {
        return "Cache{" +
                "savedResponses=" + savedResponses +
                ", maxSize=" + maxSize +
                ", requests=" + requests +
                ", hits=" + hits +
                ", misses=" + misses +
                '}';
    }


    public static class ParametersEncapsulation {

        private BreezometerEndpoints endpoint;
        private double latitude;
        private double longitude;
        private int hours;

        public ParametersEncapsulation(BreezometerEndpoints endpoint, double latitude, double longitude) {
            this.endpoint = endpoint;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public ParametersEncapsulation(BreezometerEndpoints endpoint, double latitude, double longitude, int hours) {
            this.endpoint = endpoint;
            this.latitude = latitude;
            this.longitude = longitude;
            this.hours = hours;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ParametersEncapsulation)) return false;
            ParametersEncapsulation that = (ParametersEncapsulation) o;
            return Double.compare(that.latitude, latitude) == 0 &&
                    Double.compare(that.longitude, longitude) == 0 &&
                    hours == that.hours &&
                    endpoint == that.endpoint;
        }

        @Override
        public int hashCode() {
            return Objects.hash(endpoint, latitude, longitude, hours);
        }

        @Override
        public String toString() {
            return "ParametersEncapsulation{" +
                    "endpoint=" + endpoint +
                    ", latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", hours=" + hours +
                    '}';
        }
    }
}
