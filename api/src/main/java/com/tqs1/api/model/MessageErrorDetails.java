package com.tqs1.api.model;

public enum MessageErrorDetails {

    ZERO_HOURS_ERROR("Error! You should request more than 0 hours!"),
    NEGATIVE_HOURS_ERROR("Error! You cant request a negative number of hours!"),
    MAX_HOURS_FORECAST_ERROR("Error! You must request less than 96 hours for forecast!"),
    MAX_HOURS_HISTORY_ERROR("Error! You must request less than 169 hours for history!"),
    MIN_COORDINATE_ERROR("Error! You must request more or equal than 90 per coordinate!"),
    MAX_COORDINATE_ERROR("Error! You must request less or equal than 90 per coordinate!"),
    HOST_ERROR("Error! Cant connect to conditions API!"),
    REQUEST_ERROR("Error! There was an error trying to contact the conditions API!"),
    UNEXPECTED_ERROR("Error! There was an unexpected error!");

    private final String detail;

    MessageErrorDetails(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
