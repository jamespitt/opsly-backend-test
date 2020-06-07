package com.opsly.test.JamesTest.RestFul;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAsync

public class RestFulController {

    @Autowired
    private SocialClient socialClient;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String GetData() {
        Future<JsonArray> twitter = socialClient.getArrayFromUrl("https://takehome.io/twitter", "tweet");
        Future<JsonArray> facebook = socialClient.getArrayFromUrl("https://takehome.io/facebook", "status");
        Future<JsonArray> instagram = socialClient.getArrayFromUrl("https://takehome.io/instagram", "picture");

        JsonObject result = new JsonObject();

        while (!twitter.isDone() && !facebook.isDone() && !instagram.isDone()) {
            Thread.sleep(300);
        }
        result.add("twitter", twitter.get());
        result.add("facebook", facebook.get());
        result.add("instagram", instagram.get());
        return result.toString();
    }

}