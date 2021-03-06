package com.tqs1.api.controller;

import com.tqs1.api.model.Cache;
import com.tqs1.api.model.Message;
import com.tqs1.api.model.MessageErrorDetails;
import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.BreezometerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;


@RestController
@Api(value="Conditions endpoints")
public class ConditionsController {

    private static final int MAX_HOURS_FORECAST = 95;
    private static final int MAX_HOURS_HISTORY = 168;
    private static final int MAX_VALUE_COORDINATE = 90;
    private static final int MIN_VALUE_COORDINATE = -90;

    @Autowired
    private Cache cache;

    @Autowired
    private BreezometerService service;

    @GetMapping("/current")
    @ApiOperation(value="Get the current air quality conditions")
    public Message currentConditions(@ApiParam(value = "Latitude coordinate", required = true) @RequestParam Double lat,
                                     @ApiParam(value = "Longitude coordinate", required = true) @RequestParam Double lon)
            throws IOException, URISyntaxException, ParseException {

        // coordinates limit verification
        String limitCoordinatesRange = verifyLimitCoordinatesRange(lat, lon);
        if (limitCoordinatesRange.length() > 0)
            return new Message(limitCoordinatesRange, false);

        return service.requestApi(BreezometerEndpoints.CURRENT_CONDITIONS, lat, lon, cache);
    }

    @GetMapping("/forecast")
    @ApiOperation(value="Get the future air quality conditions for some number of hours")
    public Message forecastConditions(@ApiParam(value = "Latitude coordinate", required = true) @RequestParam Double lat,
                                      @ApiParam(value = "Longitude coordinate", required = true) @RequestParam Double lon,
                                      @ApiParam(value = "Number of hours wanted", required = true) @RequestParam Integer hours)
            throws IOException, URISyntaxException, ParseException {

        // coordinates limit verification
        String limitCoordinatesRange = verifyLimitCoordinatesRange(lat, lon);
        if (limitCoordinatesRange.length() > 0)
            return new Message(limitCoordinatesRange, false);

        // hours limit verification
        String lowLimitHoursVerification = verifyLowLimitHoursRange(hours);
        if (lowLimitHoursVerification.length() > 0)
            return new Message(lowLimitHoursVerification, false);
        if (hours > MAX_HOURS_FORECAST)
            return new Message(MessageErrorDetails.MAX_HOURS_FORECAST_ERROR.getDetail(), false);

        return service.requestApi(BreezometerEndpoints.FORECAST_HOURLY, lat, lon, hours, cache);
    }

    @GetMapping("/history")
    @ApiOperation(value="Get the past air quality conditions for some number of hours")
    public Message historyConditions(@ApiParam(value = "Latitude coordinate", required = true) @RequestParam Double lat,
                                     @ApiParam(value = "Longitude coordinate", required = true) @RequestParam Double lon,
                                     @ApiParam(value = "Number of hours wanted", required = true) @RequestParam Integer hours)
            throws IOException, URISyntaxException, ParseException {

        // coordinates limit verification
        String limitCoordinatesRange = verifyLimitCoordinatesRange(lat, lon);
        if (limitCoordinatesRange.length() > 0)
            return new Message(limitCoordinatesRange, false);

        // hours limit verification
        String lowLimitHoursVerification = verifyLowLimitHoursRange(hours);
        if (lowLimitHoursVerification.length() > 0)
            return new Message(lowLimitHoursVerification, false);
        if (hours > MAX_HOURS_HISTORY)
            return new Message(MessageErrorDetails.MAX_HOURS_HISTORY_ERROR.getDetail(), false);

        return service.requestApi(BreezometerEndpoints.HISTORICAL_HOURLY, lat, lon, hours, cache);
    }

    @GetMapping("/cache")
    @ApiOperation(value="Get cache current statistics")
    public Cache cacheStatistics() {
        return cache;
    }

    private String verifyLowLimitHoursRange(Integer hours) {

        if (hours < 0)
            return MessageErrorDetails.NEGATIVE_HOURS_ERROR.getDetail();
        if(hours == 0)
            return MessageErrorDetails.ZERO_HOURS_ERROR.getDetail();
        return "";
    }

    private String verifyLimitCoordinatesRange(Double latitude, Double longitude) {

        if (latitude < MIN_VALUE_COORDINATE || longitude < MIN_VALUE_COORDINATE)
            return MessageErrorDetails.MIN_COORDINATE_ERROR.getDetail();
        if(latitude > MAX_VALUE_COORDINATE || longitude > MAX_VALUE_COORDINATE)
            return MessageErrorDetails.MAX_COORDINATE_ERROR.getDetail();
        return "";
    }
}
