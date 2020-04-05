package com.tqs1.api.api;

import com.tqs1.api.model.AirQuality;
import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.BreezometerService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@RestController
public class ConditionsController {

    @Autowired
    private BreezometerService test;

    @GetMapping("/current")
    public AirQuality currentConditions(@RequestParam Double lat, @RequestParam Double lon)
            throws IOException, URISyntaxException, ParseException {
        return test.requestApi(BreezometerEndpoints.CURRENT_CONDITIONS, lat, lon);
    }

    @GetMapping("/forecast")
    public List<AirQuality> forecastConditions(@RequestParam Double lat, @RequestParam Double lon, @RequestParam Integer hours)
            throws IOException, URISyntaxException, ParseException {
        return test.requestApi(BreezometerEndpoints.FORECAST_HOURLY, lat, lon, hours);
    }

    @GetMapping("/history")
    public List<AirQuality> historyConditions(@RequestParam Double lat, @RequestParam Double lon, @RequestParam Integer hours)
            throws IOException, URISyntaxException, ParseException {
        return test.requestApi(BreezometerEndpoints.HISTORICAL_HOURLY, lat, lon, hours);
    }
}