package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ssahu6 on 6/6/16.
 */
public class User {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJSON(JSONObject json){

        User u = new User();

        //extract and fill the values
        try {
            u.name = json.getString("name");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.uid = json.getLong("id");
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingCount = json.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    private String tagline;
    private int followersCount;
    private int followingCount;

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingCount;
    }

    public String getTagline() {
        return tagline;
    }
}
