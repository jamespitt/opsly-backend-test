package com.opsly.test.JamesTest.RestFul;

import java.util.List;

import com.google.gson.JsonObject;

import lombok.Data;

@Data
public class SocialData {
    private List<JsonObject> data;

}