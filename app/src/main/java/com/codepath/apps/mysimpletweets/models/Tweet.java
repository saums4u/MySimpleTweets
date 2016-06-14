package com.codepath.apps.mysimpletweets.models;

import android.text.format.DateUtils;

import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ssahu6 on 6/4/16.
 */
// models/Tweet.java
@Table(name = "Tweets")
public class Tweet {
    int uid;
    String userHandle;
    String createdAt;
    String timeFrom;

    public String getTimeFrom() {
        return timeFrom;
    }

    String body;
    private User user;

    public int getUid() {
        return uid;
    }

    public String getUserHandle() {
        return userHandle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    // Make sure to always define this constructor with no arguments
    public Tweet() {
    }
    // Add a constructor that creates an object from the JSON response
    public Tweet(JSONObject object){
        try {
            this.uid = object.getInt("id");
            this.userHandle = object.getString("user_username");
            this.createdAt = object.getString("created_at");

            // "created_at":"Mon Jan 23 06:24:52 +0000 2012"
            String aFormat = "EEE MMM d HH:mm:ss Z yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(aFormat);
            Date date = null;
            try {
                date = dateFormat.parse(this.createdAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // use createdAt time to set timeFrom
            this.timeFrom = DateUtils.getRelativeTimeSpanString(date.getTime(),
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
            ).toString();

            this.body = object.getString("body");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Tweet fromJSON(JSONObject json){
        Tweet tweet = new Tweet();

        try {
            tweet.body = json.getString("text");
            tweet.uid = json.getInt("id");
            tweet.createdAt = json.getString("created_at");

            // "created_at":"Mon Jan 23 06:24:52 +0000 2012"
            String aFormat = "EEE MMM d HH:mm:ss Z yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(aFormat);
            Date date = null;
            try {
                date = dateFormat.parse(tweet.createdAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // use createdAt time to set timeFrom
            tweet.timeFrom = DateUtils.getRelativeTimeSpanString(date.getTime(),
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
            ).toString();


            tweet.user = User.fromJSON(json.getJSONObject("user"));
       } catch ( JSONException e){
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                //tweet.save();
                if(tweet!=null) {
                    tweets.add(tweet);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
}