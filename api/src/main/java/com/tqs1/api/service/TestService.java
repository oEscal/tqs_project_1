package com.tqs1.api.service;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class TestService {

    @Value("${breezometer.token}")
    private String breezometer_token;

    private final HttpClient httpClient = new HttpBasic();
    private URIBuilder uriBuilder;

    public JSONObject request_api(BreezometerEndpoints endpoint, double latitude, double longitude)
            throws URISyntaxException, IOException, ParseException {

        uriBuilder = new URIBuilder("https://api.breezometer.com/air-quality/v2/" + endpoint.getEndpoint());
        uriBuilder.addParameter("lat", String.valueOf(latitude));
        uriBuilder.addParameter("lon", String.valueOf(longitude));
        uriBuilder.addParameter("key", breezometer_token);
        System.out.println(uriBuilder.build().toString());

        String response = httpClient.get(uriBuilder.build().toString());

        // get parts from response till reaching the address
        try {
            return (JSONObject) new JSONParser().parse(response);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchFieldError();
        }
    }
}
