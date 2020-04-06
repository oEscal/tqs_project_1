package com.tqs1.api.utils;

import com.tqs1.api.service.BreezometerEndpoints;
import org.apache.http.client.utils.URIBuilder;


import java.net.URISyntaxException;


public class BuildBreezometerLink {

    private String tokenTest;
    private String breezometerAllFeaturesTest;

    public BuildBreezometerLink(String tokenTest, String breezometerAllFeaturesTest) {

        this.tokenTest = tokenTest;
        this.breezometerAllFeaturesTest = breezometerAllFeaturesTest;
    }

    public String createLinkString(BreezometerEndpoints endpoint, double latitude, double longitude, int hours)
            throws URISyntaxException {

        URIBuilder uriBuilder = new URIBuilder("https://api.breezometer.com/air-quality/v2/" + endpoint.getEndpoint());
        uriBuilder.addParameter("lat", String.valueOf(latitude));
        uriBuilder.addParameter("lon", String.valueOf(longitude));
        uriBuilder.addParameter("key", tokenTest);
        uriBuilder.addParameter("features", breezometerAllFeaturesTest);
        uriBuilder.addParameter("hours", String.valueOf(hours));

        return uriBuilder.build().toString();
    }

    public String createLinkString(BreezometerEndpoints endpoint, double latitude, double longitude)
            throws URISyntaxException {
        return createLinkString(endpoint, latitude, longitude, 0);
    }
}
