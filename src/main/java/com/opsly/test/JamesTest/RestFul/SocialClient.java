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

    // We will try to get data a max of 10 times...
    static final int MAX_RETRIES = 10;

    private static RetryPolicy retryPolicy = new RetryPolicy().withMaxRetries(MAX_RETRIES);

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    //
    // This function is called from Failsafe - so we don't have to worry about
    // errors here.
    // If we do have issues, let's just call this function again
    //
    private JsonArray getDataFromUrl(String url) {

        JsonParser parser = new JsonParser();

        // First - let's call the url
        String socialData = restTemplate.getForObject(url, String.class);

        // Now let's parse the data into a JsonElement
        JsonElement socialdataElement = parser.parse(socialData);

        // And put it into a JsonArray
        JsonArray returnArray = socialdataElement.getAsJsonArray();

        return returnArray;

    }

    //
    // We have url to call & a type inside the objects inside the Json array
    // returned
    //
    public Future<JsonArray> getArrayFromUrl(String url, String type) {
        return executor.submit(() -> {
            final JsonArray jsonArray = new JsonArray();

            //
            // Try to make call MAX_RETRIES times
            //
            JsonArray data = Failsafe.with(retryPolicy).get(() -> getDataFromUrl(url));

            for (JsonElement datapointElement : data) {
                JsonObject datapointObject = datapointElement.getAsJsonObject();

                // Get the field required - for example 'tweet' for the Twitter feed
                String dataToAdd = datapointObject.get(type).getAsString();
                jsonArray.add(dataToAdd);
            }

            return jsonArray;
        });
    }

}
