package com.opsly.test.JamesTest.RestFul;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public JsonObject GetData() {
        JsonArray twitter = socialClient.getArrayFromUrl("https://takehome.io/twitter", "tweet");
        JsonArray facebook = socialClient.getArrayFromUrl("https://takehome.io/facebook", "status");
        JsonArray instragram = socialClient.getArrayFromUrl("https://takehome.io/instragram", "picture");
        JsonObject result = new JsonObject();
        result.add("twitter", twitter);
        result.add("facebook", facebook);
        result.add("instragram", instragram);
        return result;
    }

}