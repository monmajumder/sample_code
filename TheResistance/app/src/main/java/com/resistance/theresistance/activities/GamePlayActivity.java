package com.resistance.theresistance.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.logic.Round;
import com.resistance.theresistance.views.GamePlayFragment;

import java.util.ArrayList;

public class GamePlayActivity extends FragmentActivity {

    String gameName;
    String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        gameName = preferences.getString("gameName", "none");
        playerName = preferences.getString("playerName", "none");

        //handleResistanceOrSpy();
        //handleLeader();

        //Abstract this method
        //Add some sort of timer, do this every second or so
        //Do it until the game state changes (mission leader presses choose)
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
     * Checks if a player is Resistance or Spy and changes visibilities if so.
     */
    private void handleResistanceOrSpy() {
        if (GameController.isResistance(playerName)) {
            //Go to GamePlayActivity with certain layout for Resistance?
        } else {
            //Go to GamePlayActivity with certain layout for Spies?
        }
    }

    /**
     * Checks if player is a Mission Leader and changes visibilities if so.
     */
    private void handleLeader() {
        if (GameController.checkLeader(gameName, playerName)) {
            //Change visibilities for Mission Leader
        } else {
            //Change visibilities
        }
    }

    /**
     * Checks if a player is a Missionary and handles visibilities if so.
     */
    private void handleMissionary() {
        if (GameController.checkMissionary(gameName, playerName)) {
            //Change visibilities for Missionary
        } else {
            //change visibilities
        }
    }

    /**
     * Called when a Mission Leader is choosing missionaries.
     */
    public void leaderChoosingMissionaries() {
        //somehow take in clicks for players who the leader chooses
        ArrayList<String> chosenMissionaries = new ArrayList<>();
        GameController.addChosenMissionaries(gameName, chosenMissionaries);
        GameController.changeState(gameName, Game.State.VOTE_FOR_MISSIONARIES);
    }

    /**
     * Called when a player clicks "YES" when voting for a Missionary team.
     */
    public void yesMissionaryTeam() {
        GameController.getCurrentMission(gameName).getCurrentRound().addYesVote(playerName);
        // switch to visibilities for waiting for everyone to finish voting
        GameController.ifEveryoneDoneVoting(gameName);
    }

    /**
     * Called when a player clicks "NO" when voting for a Missionary team.
     */
    public void noMissionaryTeam() {
        GameController.getCurrentMission(gameName).getCurrentRound().addNoVote(playerName);
        // switch to visibilities for waiting for everyone to finish voting
        GameController.ifEveryoneDoneVoting(gameName);
    }

    /**
     * Called when a Missionary clicks "PASS" when going on a Mission.
     */
    public void passMission() {
        GameController.getCurrentMission(gameName).addPassVote();
        // switch to visibilities for waiting for other missionaries
        GameController.ifMissionariesDoneVoting(gameName);
    }

    /**
     * Called when a Missionary clicks "FAIL" when going on a Mission.
     */
    public void failMission() {
        GameController.getCurrentMission(gameName).addFailVote();
        // switch to visibilities for waiting for other missionaries
        GameController.ifMissionariesDoneVoting(gameName);
    }

    /**
     * DELETE!!!!!!!!!!
     * Test game.
     */
    public void testGame() {
        //TEST IF ISRESISTANCE WORKS. DELETE.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedPlayer = preferences.getString("playerName","none");
        Log.d("checkPlayerName", storedPlayer);
        if (GameController.isResistance(storedPlayer)) {
            Log.d("IsResistance", storedPlayer);
        } else {
            Log.d("IsNotResistance",storedPlayer);
        }
    }

}