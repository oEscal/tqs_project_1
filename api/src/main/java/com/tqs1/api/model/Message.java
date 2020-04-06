package com.tqs1.api.model;


import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Message {

    private AirQuality airQuality;
    private List<AirQuality> multipleAirQuality;

    private String detail;
    private boolean success;

    public Message() {}

    public Message(AirQuality data, String detail, boolean success) {

        this.airQuality = data;
        this.detail = detail;
        this.success = success;
    }

    public Message(List<AirQuality> data, String detail, boolean success) {

        this.multipleAirQuality = data;
        this.detail = detail;
        this.success = success;
    }

    public AirQuality getAirQuality() {
        return airQuality;
    }

    public List<AirQuality> getMultipleAirQuality() {
        return multipleAirQuality;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Message{" +
                "airQuality=" + airQuality +
                ", multipleAirQuality=" + multipleAirQuality +
                ", detail='" + detail + '\'' +
                ", success=" + success +
                '}';
    }
}
