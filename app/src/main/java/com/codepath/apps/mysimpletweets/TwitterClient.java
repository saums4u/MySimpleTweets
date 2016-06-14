package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import cz.msebera.android.httpclient.Header;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "Buznfv6EqNNeN93sZvSDcQtRX";       // Change this
	public static final String REST_CONSUMER_SECRET = "VvJAs6Nq3dSNqGznjbNebOuri2i41Oow6IAh0f4j6esU2xkcuB"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://saumyatwitterapp"; // Change this (here and in manifest)
    public static final int PAGE_SIZE = 20000;

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	// TwitterClient.java
	public void getHomeTimeline(int page, int maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
        params.put("count", PAGE_SIZE);

        if(maxId != -1) {
            params.put("max_id", 1);
        }

//		params.put("page", String.valueOf(page));
        getClient().get(apiUrl, params, handler);

        if(loggedInUser == null){
            setLoggedInUser( new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = User.fromJSON(response);
                    loggedInUser = user.getScreenName();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }
	}

    public void postTweet(String body, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", body);
        getClient().post(apiUrl, params, handler);
    }

	public void getMentionsTimeline(int page, int maxId, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
        params.put("count", PAGE_SIZE);

        if(maxId != -1) {
            params.put("max_id", 1);
        }
        getClient().get(apiUrl, params, handler);
	}

    public void getUserTimeline(int page, int maxId, String screen_name, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screen_name);
        params.put("count", PAGE_SIZE);

        if(maxId != -1) {
            params.put("max_id", 1);
        }

        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler){

        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
            params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }

    private static String loggedInUser = null;

    public void setLoggedInUser(AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null,handler);
    }

    public static String getLoggedInUser(){
        return loggedInUser;
    }

}