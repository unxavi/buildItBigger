package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import unxavi.com.github.jokejavalib.Joke;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that return a random joke
     */
    @ApiMethod(
            name = "joke",
            path = "joke")
    public MyBean getRandomJoke() {
        MyBean response = new MyBean();
        response.setData(Joke.getRandomJoke());
        return response;
    }


}
