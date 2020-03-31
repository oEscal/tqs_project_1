package com.tqs1.api.service;

public enum BreezometerEndpoints {

    CURRENT_CONDITIONS("current-conditions"),
    FORECAST_HOURLY("forecast/hourly"),
    HISTORICAL_HOURLY("historical/hourly");

    private final String endpoint;

    BreezometerEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
