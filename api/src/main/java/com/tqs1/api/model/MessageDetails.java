package com.tqs1.api.model;

public enum MessageDetails {

    ZERO_HOURS_ERROR("Error! You should request more than 0 hours!"),
    NEGATIVE_HOURS_ERROR("Error! You cant request a negative number of hours!"),
    MAX_HOURS_FORECAST_ERROR("Error! You must request less than 96 hours for forecast!"),
    MAX_HOURS_HISTORY_ERROR("Error! You must request less than 169 hours for history!");

    private final String detail;

    MessageDetails(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }
}
