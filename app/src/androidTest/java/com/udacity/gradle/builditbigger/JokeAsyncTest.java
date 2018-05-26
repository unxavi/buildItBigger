package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JokeAsyncTest extends AndroidTestCase implements JokeAsyncTask.JokeListener {

    // create  a signal to let us know when our task is done.
    final CountDownLatch signal = new CountDownLatch(1);
    private String jokeToTest = null;


    public void testJokeAsyncTaks() throws InterruptedException {
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask();
        jokeAsyncTask.execute(this);

        /* The testing thread will wait here until the JokeListener releases it
         * above with the countDown() or 15 seconds passes and it times out.
         */
        signal.await(15, TimeUnit.SECONDS);
        assertNotNull(jokeToTest);
    }

    @Override
    public void onGetJoke(String joke) {
        this.jokeToTest = joke;
        signal.countDown();
    }
}
