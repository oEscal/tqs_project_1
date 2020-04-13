package com.tqs1.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs1.api.model.AirQuality;
import com.tqs1.api.model.Cache;
import com.tqs1.api.model.Message;
import com.tqs1.api.model.MessageErrorDetails;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BreezometerService {

    @Value("${breezometer.token}")
    private String breezometerToken;

    @Value("${breezometer.all_features}")
    private String breezometerAllFeatures;

    @Autowired
    private HttpClient httpClient;

    public Message requestApi(BreezometerEndpoints endpoint, double latitude, double longitude, int hours, Cache cache)
            throws URISyntaxException, IOException, ParseException {

        URIBuilder uriBuilder = new URIBuilder("https://api.breezometer.com/air-quality/v2/" + endpoint.getEndpoint());
        uriBuilder.addParameter("lat", String.valueOf(latitude));
        uriBuilder.addParameter("lon", String.valueOf(longitude));
        uriBuilder.addParameter("key", breezometerToken);
        uriBuilder.addParameter("features", breezometerAllFeatures);
        uriBuilder.addParameter("hours", String.valueOf(hours));

        Cache.ParametersEncapsulation parametersEncapsulation = new Cache.ParametersEncapsulation(
                endpoint, latitude, longitude, hours);

        String response;
        try {
            response = cache.getResponse(parametersEncapsulation);
            if (response == null) {
                response = httpClient.get(uriBuilder.build().toString());

                // add to cache
                cache.addResponse(parametersEncapsulation, response);
            }
        } catch (UnknownHostException e) {
            return new Message(MessageErrorDetails.HOST_ERROR.getDetail(), false);
        } catch (RuntimeException e) {
            return new Message(MessageErrorDetails.UNEXPECTED_ERROR.getDetail(), false);
        }

        List<AirQuality> allAirQuality = new ArrayList<>();

        try {
            // get parts from response till reaching the address
            Object jsonData = ((JSONObject) new JSONParser().parse(response)).get("data");
            JSONObject jsonError = (JSONObject) ((JSONObject) new JSONParser().parse(response)).get("error");
            
            // verify if API returned some error
            if (jsonError != null)
                return new Message(jsonError.get("title").toString(), false);

            if (!(jsonData instanceof JSONArray))
                allAirQuality.add(new ObjectMapper().readValue(jsonData.toString(), AirQuality.class));
            else
                for (Object jsonSubData : (JSONArray) jsonData)
                    allAirQuality.add(new ObjectMapper().readValue(jsonSubData.toString(), AirQuality.class));
        } catch (NullPointerException e) {
            return new Message(MessageErrorDetails.REQUEST_ERROR.getDetail(), false);
        } catch (RuntimeException e) {
            return new Message(MessageErrorDetails.UNEXPECTED_ERROR.getDetail(), false);
        }

        return new Message(allAirQuality, "Success obtaining the requested information", true);
    }

    public Message requestApi(BreezometerEndpoints endpoint, double latitude, double longitude, Cache cache)
            throws ParseException, IOException, URISyntaxException {

        Message originalResponse = requestApi(endpoint, latitude, longitude, 0, cache);
        List<AirQuality> originalAirQualityList = originalResponse.getMultipleAirQuality();

        AirQuality returnAirQuality;
        if (originalAirQualityList != null)
            returnAirQuality = originalAirQualityList.get(0);
        else
            return originalResponse;

        return new Message(returnAirQuality, originalResponse.getDetail(), originalResponse.isSuccess());
    }
}
