package com.tqs1.api.api;

import com.tqs1.api.service.BreezometerEndpoints;
import com.tqs1.api.service.TestService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;


@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private TestService test;

    @GetMapping
    public JSONObject test() throws ParseException, IOException, URISyntaxException {

        return test.request_api(BreezometerEndpoints.CURRENT_CONDITIONS, 40.740421, -7.736100);
    }
}
