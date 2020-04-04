package com.tqs1.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.AirQuality;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@Service
public class BreezometerService {

    @Value("${breezometer.token}")
    private String breezometer_token;

    @Value("${breezometer.all_features}")
    private String breezometer_all_features;

    private final HttpClient httpClient = new HttpBasic();
    private URIBuilder uriBuilder;

    public AirQuality request_api(BreezometerEndpoints endpoint, double latitude, double longitude)
            throws URISyntaxException, IOException, ParseException {

        uriBuilder = new URIBuilder("https://api.breezometer.com/air-quality/v2/" + endpoint.getEndpoint());
        uriBuilder.addParameter("lat", String.valueOf(latitude));
        uriBuilder.addParameter("lon", String.valueOf(longitude));
        uriBuilder.addParameter("key", breezometer_token);
        uriBuilder.addParameter("features", breezometer_all_features);

        String response = httpClient.get(uriBuilder.build().toString());

        // get parts from response till reaching the address
        try {
            return new ObjectMapper().readValue(response, AirQuality.class);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchFieldError();
        }
    }

    private static class JSONObjectStrategy {

        private JSONObject jsonObject;

        public JSONObjectStrategy(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public JSONObjectStrategy get(String o) {
            return new JSONObjectStrategy((JSONObject) this.jsonObject.get(o));
        }

        public JSONObject getJsonObject() {
            return jsonObject;
        }
    }
}
