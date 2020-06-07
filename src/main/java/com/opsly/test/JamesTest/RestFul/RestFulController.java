package com.opsly.test.JamesTest.RestFul;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAsync

public class RestFulController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String GetData() {
        return "{}";
    }

}