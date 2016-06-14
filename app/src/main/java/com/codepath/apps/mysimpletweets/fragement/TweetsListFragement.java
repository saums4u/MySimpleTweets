package com.codepath.apps.mysimpletweets.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ssahu6 on 6/10/16.
 */
public abstract class TweetsListFragement extends Fragment {

    private ArrayList<Tweet> tweets = new ArrayList<>();
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private int maxId = -1;
    protected TwitterClient client;


    public void setLastMaxId() {
        Tweet t = aTweets.getItem(aTweets.getCount() - 1);
        Log.d("DEBUG:", "Adapter size = "+aTweets.getCount());
        this.maxId = t.getUid();
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public int getMaxId() {
        return maxId;
    }

    private static int pageScollCount = 0;

    protected abstract void populateTimeline(int page);

    // inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragement_tweets_list, parent, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        // Attach the listener to the AdapterView onCreate
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if(page > pageScollCount) {

                    //get another 10 pages.
                    pageScollCount = pageScollCount+10;
                    populateTimeline(pageScollCount);

                }
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }

            @Override
            public int getFooterViewType() {
                return 0;
            }

        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView adapterView, View view, int position, long val)
            {
                Tweet t = aTweets.getItem(position);
                Bundle b = new Bundle();
                b.putString("screen_name", t.getUser().getScreenName());
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtras(b);
                startActivity(i);


              //  Toast.makeText(getActivity(), "Stop Clicking me", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    // creating of lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public TweetsArrayAdapter getAdapter(){
        return aTweets;
    }

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }

    public void clearAndAddAll(List<Tweet> tweets){
        aTweets.clear();
        aTweets.addAll(tweets);
    }


    public void postTweet(String tweetText) {
        client.postTweet(tweetText, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG:",response.toString());

                // add to the list at the begining
                Tweet tweet = Tweet.fromJSON(response);
                aTweets.insert(tweet,0);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
