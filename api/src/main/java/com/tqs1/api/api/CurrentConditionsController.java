package com.tqs1.api.api;

import com.tqs1.api.model.SimpleAirQuality;
import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.BreezometerService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;


@RestController
public class CurrentConditionsController {

    @Autowired
    private BreezometerService test;

    @GetMapping("/current/")
    public SimpleAirQuality test() throws ParseException, IOException, URISyntaxException {
        System.out.println(test.request_api(BreezometerEndpoints.CURRENT_CONDITIONS, 39.160489,-8.548239).getAir_quality_category());
        return test.request_api(BreezometerEndpoints.CURRENT_CONDITIONS, 39.160489,-8.548239);
    }
}
