package com.tqs1.api.service;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpBasic implements HttpClient {

    private static final Logger LOGGER = Logger.getLogger( HttpBasic.class.getName() );

    @Override
    public String get(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        CloseableHttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
            throw new ConnectionClosedException();
        }

        try {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage());
            throw new ConnectionShutdownException();
        } finally {
            response.close();
            client.close();
        }
    }
}
