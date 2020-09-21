package com.prestaging.fulvila.model;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import org.springframework.stereotype.Component;

@Component
public class ClarifaiTest {
    private final String API_KEY = "{{KEY}}";
    private ClarifaiBuilder builder;
    private ClarifaiClient client;

    public ClarifaiTest(){
        builder = new ClarifaiBuilder(API_KEY);
        client = builder.buildSync();
    }

    public ClarifaiBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ClarifaiBuilder builder) {
        this.builder = builder;
    }

    public ClarifaiClient getClient() {
        return client;
    }

    public void setClient(ClarifaiClient client) {
        this.client = client;
    }
}
