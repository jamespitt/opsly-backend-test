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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class SocialClient {
    RestTemplate restTemplate = new RestTemplate();
    static final int MAX_RETRIES = 5;

    private static RetryPolicy retryPolicy = new RetryPolicy().withMaxRetries(MAX_RETRIES);

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public JsonArray getDataFromUrl(String url) {

        JsonParser parser = new JsonParser();
        String socialData = restTemplate.getForObject(url, String.class);
        JsonElement socialdataElement = parser.parse(socialData);
        JsonArray returnArray = socialdataElement.getAsJsonArray();

        return returnArray;

    }

    public Future<JsonArray> getArrayFromUrl(String url, String type) {
        return executor.submit(() -> {
            final JsonArray jsonArray = new JsonArray();

            JsonArray data = Failsafe.with(retryPolicy).get(() -> getDataFromUrl(url));
            for (JsonElement datapointElement : data) {
                JsonObject datapointObject = datapointElement.getAsJsonObject();
                String dataToAdd = datapointObject.get(type).getAsString();
                jsonArray.add(dataToAdd);
            }

            return jsonArray;
        });
    }

}
