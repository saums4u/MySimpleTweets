package com.codepath.apps.mysimpletweets.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ssahu6 on 6/10/16.
 */
public class HomeTimelineFragement extends TweetsListFragement{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getTwitterClient();
        populateTimeline(1);
    }


    protected void populateTimeline(int page){

        client.getHomeTimeline(page, getMaxId(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG:",response.toString());

                ArrayList<Tweet> tweets = Tweet.fromJSONArray(response);
                addAll(tweets);

                // setMaxId(tweets.get(tweets.size() - 1).getUid());
                setLastMaxId();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

        });
    }

}
