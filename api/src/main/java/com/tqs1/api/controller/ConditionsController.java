package com.tqs1.api.controller;

import com.tqs1.api.model.Message;
import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.BreezometerService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;


@RestController
public class ConditionsController {

    @Autowired
    private BreezometerService service;

    @GetMapping("/current")
    public Message currentConditions(@RequestParam Double lat, @RequestParam Double lon)
            throws IOException, URISyntaxException, ParseException {
        return service.requestApi(BreezometerEndpoints.CURRENT_CONDITIONS, lat, lon);
    }

    @GetMapping("/forecast")
    public Message forecastConditions(@RequestParam Double lat, @RequestParam Double lon,
                                                        @RequestParam Integer hours)
            throws IOException, URISyntaxException, ParseException {
        return service.requestApi(BreezometerEndpoints.FORECAST_HOURLY, lat, lon, hours);
    }

    @GetMapping("/history")
    public Message historyConditions(@RequestParam Double lat, @RequestParam Double lon,
                                                       @RequestParam Integer hours)
            throws IOException, URISyntaxException, ParseException {
        return service.requestApi(BreezometerEndpoints.HISTORICAL_HOURLY, lat, lon, hours);
    }
}
