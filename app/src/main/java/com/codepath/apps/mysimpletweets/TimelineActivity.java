package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragement.HomeTimelineFragement;
import com.codepath.apps.mysimpletweets.fragement.MentionsTimelineFragement;
import com.codepath.apps.mysimpletweets.fragement.TweetsListFragement;

public class TimelineActivity extends AppCompatActivity {

    private TweetsListFragement fragementTweetsList;
    private HomeTimelineFragement homeTimelineFragement;
    private MentionsTimelineFragement mentionsTimelineFragement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // access the com.codepath.apps.mysimpletweets.fragement and load.

        // get the view pager.
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_timeline, menu);
//        Log.d("DEBUG",menu.toString());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle when settings is selected

        switch (item.getItemId()){
            case R.id.miCompose:
                showComposeDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    protected ComposeDialog tweetDialog = ComposeDialog.newInstance("Compose Tweet");

    public void onTweetNow(View view) {
        EditText etTweet = tweetDialog.getEtTweet();

        String tweetText = etTweet.getText().toString();
        homeTimelineFragement.postTweet(tweetText);
        tweetDialog.dismiss();
//        populateTimeline(0);
    }
    private void showComposeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        tweetDialog.show(fm, "compose");

    }


    public void onCancel(View view) {
        // dismiss the dialog.
        tweetDialog.dismiss();
    }

    public void onProfileView(MenuItem mi) {

        Bundle b = new Bundle();
        b.putString("screen_name", TwitterClient.getLoggedInUser());

        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtras(b);
        startActivity(i);

    }

    public void onProfileClick(View view) {
        Bundle b = new Bundle();
        b.putString("user_name", "saumya");
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter{
        final int PAGE_COUNT = 2;
        private String tabTitles[] = { "Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                if( homeTimelineFragement == null ) {
                    homeTimelineFragement = new HomeTimelineFragement();
                }
                return homeTimelineFragement;
            }else {
                if( mentionsTimelineFragement == null ){
                    mentionsTimelineFragement = new MentionsTimelineFragement();
                }
                return mentionsTimelineFragement;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}