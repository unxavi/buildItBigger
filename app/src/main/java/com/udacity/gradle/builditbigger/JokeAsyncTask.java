package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class JokeAsyncTask extends AsyncTask<JokeAsyncTask.JokeListener, Void, String> {
    private static final String BASE_URL = "http://10.0.2.2:8080/_ah/api/";
    private static MyApi myApiService = null;

    private WeakReference<JokeListener> weakReferenceListener;

    public interface JokeListener {
        void onGetJoke(String joke);
    }

    @Override
    protected String doInBackground(JokeAsyncTask.JokeListener... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(BASE_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            JokeListener jokeListener = params[0];
            this.weakReferenceListener = new WeakReference<>(jokeListener);
            myApiService = builder.build();
        }


        try {
            return myApiService.joke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        if (weakReferenceListener != null) {
            JokeListener jokeListener = weakReferenceListener.get();
            if (jokeListener != null) {
                jokeListener.onGetJoke(joke);
            }
        }
    }
}
