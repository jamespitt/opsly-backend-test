package com.opsly.test.JamesTest.RestFul;

import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

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
    public Object GetData() {
        Object twitter = socialClient.getDataFromUrl("https://takehome.io/twitter");
        Object facebook = socialClient.getDataFromUrl("https://takehome.io/facebook");
        Object instragram = socialClient.getDataFromUrl("https://takehome.io/instragram");
        // CompletableFuture<SocialData> =
        // socialClient.getDataFromUrl("https://takehome.io/twitter");
        // CompletableFuture<SocialData> =
        // socialClient.getDataFromUrl("https://takehome.io/twitter");
        Object result = new Object();
        try {
            result = twitter.get();

        } catch (Throwable e) {
            //
        }
        return result;
    }

}