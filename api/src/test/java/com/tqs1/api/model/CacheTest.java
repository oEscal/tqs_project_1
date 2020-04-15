package com.tqs1.api.model;

import com.tqs1.api.service.BreezometerEndpoints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;


class CacheTest {

    private Cache cache;


    @BeforeEach
    void setup() {
        // set max size to 3 to facilitate testing
        cache = new Cache(3);

        // add two values to the cache
        cache.addResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 1, 1, 1),
                "response1");
        cache.addResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 2, 2, 2),
                "response2");
    }

    @Test
    void testInitialConditions() {

        cache = new Cache(3);

        assertThat(cache.getSize(), is(0));
        assertThat(cache.getHits(), is(0));
        assertThat(cache.getRequests(), is(0));
        assertThat(cache.getMisses(), is(0));
    }

    @Test
    void testMaxSizeRemoveOldest() {

        Cache.ParametersEncapsulation expectedRemove =
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 1, 1, 1);
        Cache.ParametersEncapsulation expectedStay =
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 2, 2, 2);

        cache.addResponse(new Cache.ParametersEncapsulation(
                BreezometerEndpoints.HISTORICAL_HOURLY, 3, 3, 3), "response3");
        cache.addResponse(new Cache.ParametersEncapsulation(
                BreezometerEndpoints.HISTORICAL_HOURLY, 4, 4, 4), "response4");

        assertThat(cache.getResponse(expectedRemove), nullValue());
        assertThat(cache.getResponse(expectedStay), is("response2"));
    }


    @Test
    void testNumberOfMisses() {

        // misses
        cache.getResponse(new Cache.ParametersEncapsulation(BreezometerEndpoints.CURRENT_CONDITIONS, 1, 2));
        cache.getResponse(new Cache.ParametersEncapsulation(BreezometerEndpoints.CURRENT_CONDITIONS, 1, 3));
        cache.getResponse(new Cache.ParametersEncapsulation(BreezometerEndpoints.CURRENT_CONDITIONS, 1, 4));

        // hits
        cache.getResponse(new Cache.ParametersEncapsulation(
                BreezometerEndpoints.HISTORICAL_HOURLY, 1, 1, 1));
        cache.getResponse(new Cache.ParametersEncapsulation(
                BreezometerEndpoints.HISTORICAL_HOURLY, 2, 2, 2));

        assertThat(cache.getMisses(), is(3));
    }

    @Test
    void testNumberOfHits() {

        // misses
        cache.getResponse(new Cache.ParametersEncapsulation(BreezometerEndpoints.CURRENT_CONDITIONS, 1, 2));

        // hits
        cache.getResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 1, 1, 1));
        cache.getResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 2, 2, 2));

        assertThat(cache.getHits(), is(2));
    }

    @Test
    void testNumberOfRequests() {

        // misses
        cache.getResponse(new Cache.ParametersEncapsulation(BreezometerEndpoints.CURRENT_CONDITIONS, 1, 2));
        cache.getResponse(new Cache.ParametersEncapsulation(BreezometerEndpoints.CURRENT_CONDITIONS, 1, 3));
        cache.getResponse(new Cache.ParametersEncapsulation(BreezometerEndpoints.CURRENT_CONDITIONS, 1, 4));

        // hits
        cache.getResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 1, 1, 1));
        cache.getResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 2, 2, 2));

        assertThat(cache.getRequests(), is(5));
    }

    @Test
    void testSizeMinorThanMax() {
        assertThat(cache.getSize(), is(2));
    }

    @Test
    void testSizeMoreThanMax() {
        cache.addResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 3, 3, 3),
                "response3");
        cache.addResponse(
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 4, 4, 4),
                "response4");

        assertThat(cache.getSize(), is(3));
    }

    @Test
    void testAddSameParameters() {

        Cache.ParametersEncapsulation parameters =
                new Cache.ParametersEncapsulation(BreezometerEndpoints.HISTORICAL_HOURLY, 1, 1, 1);
        cache.addResponse(parameters, "something");

        assertThat(cache.getSize(), is(2));
    }

    @Test
    void testAddSameParametersExceptEndpoint() {

        Cache.ParametersEncapsulation parameters =
                new Cache.ParametersEncapsulation(BreezometerEndpoints.FORECAST_HOURLY, 1, 1, 1);
        cache.addResponse(parameters, "something");

        assertThat(cache.getSize(), is(3));
    }
}
