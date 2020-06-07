package com.opsly.test.JamesTest.RestFul;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class SocialClient {
    RestTemplate restTemplate = new RestTemplate();
    static final int MAX_RETRIES = 10;

    private static RetryPolicy retryPolicy = new RetryPolicy().withMaxRetries(MAX_RETRIES);

    public JsonArray getDataFromUrl(String url) {
        // Gson gson = new Gson();

        // try {

        return Failsafe.with(retryPolicy).get(() -> restTemplate.getForObject(url, JsonArray.class));
        // } catch (Exception e) {
        //

        // }

    }
    

    public List<String> getListFromUrl(String url, String type) {
        List<String> list = new ArrayList<String>();

        JsonArray data = getDataFromUrl(url);
        for (JsonElement datapointElement: data) {
            JsonObject datapointObject = datapointElement.getAsJsonObject(); 
            String dataToAdd = datapointObject.get(type).getAsString();
            list.add(dataToAdd);

        }

        return list;
    }


}
