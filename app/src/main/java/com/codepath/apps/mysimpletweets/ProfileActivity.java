package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragement.UserTimelineFragement;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
    String screenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        screenName = getIntent().getStringExtra("screen_name");

        client = TwitterApplication.getTwitterClient();
        client.getUserInfo(screenName, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                getSupportActionBar().setTitle("@"+user.getScreenName());
                populateProfileHeader(user);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


        if(savedInstanceState == null) {
            UserTimelineFragement fragementUserTimeline = UserTimelineFragement.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragementUserTimeline);

            ft.commit();
        }

    }

    private void populateProfileHeader(User user) {
        TextView tvName = (TextView)findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowers.setText(user.getFriendsCount() + " Following");

        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if( id == R.id.miProfile ){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
