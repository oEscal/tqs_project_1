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


@RestController
public class CurrentConditionsController {

    @Autowired
    private BreezometerService test;

    @GetMapping("/current")
    public AirQuality test(@RequestParam Double lat, @RequestParam Double lon)
            throws ParseException, IOException, URISyntaxException {
        return test.request_api(BreezometerEndpoints.CURRENT_CONDITIONS, lat, lon);
    }

    // 39.160489
    // -8.548239
}
