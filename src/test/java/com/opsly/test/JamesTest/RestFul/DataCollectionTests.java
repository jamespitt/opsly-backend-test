package com.opsly.test.JamesTest.RestFul;

import java.util.concurrent.Future;

import com.google.gson.JsonArray;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;

public class DataCollectionTests {

    // A simple unit test. Ideally at this point it would be good to put much more
    // tests to check things work
    // in terms of the data returned
    //
    @Test
    void twitterDataCollectionWorks() {
        SocialClient socialClient = new SocialClient();
        Future<JsonArray> twitter = socialClient.getArrayFromUrl("https://takehome.io/twitter", "tweet");

        try {
            assertTrue(twitter.get().size() > 0);
        } catch (Exception e) {

        }

    }

}