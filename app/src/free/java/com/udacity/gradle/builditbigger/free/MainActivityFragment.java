package com.udacity.gradle.builditbigger.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.JokeAsyncTask;
import com.udacity.gradle.builditbigger.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import unxavi.com.github.jokedisplayandroidlib.JokeDisplayActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements JokeAsyncTask.JokeListener {

    Unbinder unbinder;

    private InterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        createInterstitial();
    }

    private void createInterstitial() {
        if (getContext() != null) {
            mInterstitialAd = new InterstitialAd(getContext());
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    new JokeAsyncTask().execute(MainActivityFragment.this);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tellJokeBtn)
    public void onViewClicked() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            loadJoke();
        }


    }

    private void loadJoke() {
        new JokeAsyncTask().execute(this);
    }

    @Override
    public void onGetJoke(String joke) {
        Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE_KEY, joke);
        startActivity(intent);
    }
}

