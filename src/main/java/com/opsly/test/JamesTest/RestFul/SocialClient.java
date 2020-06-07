package com.opsly.test.JamesTest.RestFul;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class SocialClient {
    RestTemplate restTemplate = new RestTemplate();


    @Async
    public CompletableFuture<SocialData> getCountriesByLanguage(String url) {
        SocialData response = restTemplate.getForObject(url, SocialData.class);

        return CompletableFuture.completedFuture(response);
    }

}
