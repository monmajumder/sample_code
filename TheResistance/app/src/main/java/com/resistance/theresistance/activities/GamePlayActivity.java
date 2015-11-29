package com.resistance.theresistance.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.views.GamePlayFragment;

public class GamePlayActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        handleLeader();

        //Abstract this method
        //Add some sort of timer, do this every second or so
        //Do it until the game state changes (mission leader presses choose)
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String gameName = preferences.getString("gameName", "none");

        if (GameController.checkMissionLeaderDoneChoosing(gameName)) {
            //change visibilities for VOTING FOR MISSIONARIES
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return GamePlayFragment.newInstance("FirstFragment, Instance 1");
                //case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");
                default: return GamePlayFragment.newInstance("ThirdFragment, Default");
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    /**
     * Checks if player is a Mission Leader and changes visibilities if so.
     */
    private void handleLeader() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String playerName = preferences.getString("playerName", "none");
        String gameName = preferences.getString("gameName","none");

        if (GameController.checkLeader(gameName, playerName)) {
            //Change visibilities for Mission Leader
        }
    }

    /**
     * Checks if a player is a Missionary and handles visibilities if so.
     */
    private void handleMissionary() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String playerName = preferences.getString("playerName", "none");
        String gameName = preferences.getString("gameName","none");

        if (GameController.checkLeader(gameName, playerName)) {
            //Change visibilities for Mission Leader
        }
    }

    /**
     * Called when a player clicks "YES" when voting for a Missionary team.
     */
    public void yesMissionaryTeam() {

    }

    /**
     * Called when a player clicks "NO" when voting for a Missionary team.
     */
    public void noMissionaryTeam() {

    }

    /**
     * Called when a Missionary clicks "PASS" when going on a Mission.
     */
    public void passMission() {

    }

    /**
     * Called when a Missionary clicks "FAIL" when going on a Mission.
     */
    public void failMission() {

    }

}