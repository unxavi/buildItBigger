package com.udacity.gradle.builditbigger;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import unxavi.com.github.jokedisplayandroidlib.JokeDisplayActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements JokeAsyncTask.JokeListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Unbinder unbinder;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tellJokeBtn)
    public void onViewClicked() {
        loadJoke();
    }

    private void loadJoke() {
        showLoading();
        new JokeAsyncTask().execute(this);
    }

    @Override
    public void onGetJoke(String joke) {
        hideLoading();
        Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE_KEY, joke);
        startActivity(intent);
    }


    private void showLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
