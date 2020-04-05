package com.tqs1.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.AirQuality;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BreezometerService {

    @Value("${breezometer.token}")
    private String breezometerToken;

    @Value("${breezometer.all_features}")
    private String breezometerAllFeatures;

    private final HttpClient httpClient = new HttpBasic();

    public List<AirQuality> requestApi(BreezometerEndpoints endpoint, double latitude, double longitude, int hours)
            throws URISyntaxException, IOException, ParseException {

        URIBuilder uriBuilder = new URIBuilder("https://api.breezometer.com/air-quality/v2/" + endpoint.getEndpoint());
        uriBuilder.addParameter("lat", String.valueOf(latitude));
        uriBuilder.addParameter("lon", String.valueOf(longitude));
        uriBuilder.addParameter("key", breezometerToken);
        uriBuilder.addParameter("features", breezometerAllFeatures);
        uriBuilder.addParameter("hours", String.valueOf(hours));

        String response = httpClient.get(uriBuilder.build().toString());

        // get parts from response till reaching the address
        try {
            Object jsonData = ((JSONObject) new JSONParser().parse(response)).get("data");

            List<AirQuality> allAirQuality = new ArrayList<>();

            if (!(jsonData instanceof JSONArray))
                allAirQuality.add(new ObjectMapper().readValue(jsonData.toString(), AirQuality.class));
            else
                for (Object jsonSubData : (JSONArray) jsonData)
                    allAirQuality.add(new ObjectMapper().readValue(jsonSubData.toString(), AirQuality.class));

            return allAirQuality;
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchFieldError();
        }
    }

    public AirQuality requestApi(BreezometerEndpoints endpoint, double latitude, double longitude)
            throws ParseException, IOException, URISyntaxException {
        return requestApi(endpoint, latitude, longitude, 0).get(0);
    }
}
