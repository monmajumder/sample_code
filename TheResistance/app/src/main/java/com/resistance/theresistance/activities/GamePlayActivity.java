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
import com.resistance.theresistance.logic.GameTimer;
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

        //GameTimer.missionLeaderDoneChoosing(this, gameName);
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
        // switch to visibilities for waiting for everyone to finish voting
        boolean vote = true;
        GameController.addVoteForMissionaries(vote, gameName, playerName);
        afterVotingForMissionaryTeam();
    }

    /**
     * Called when a player clicks "NO" when voting for a Missionary team.
     */
    public void noMissionaryTeam() {
        // switch to visibilities for waiting for everyone to finish voting
        boolean vote = false;
        GameController.addVoteForMissionaries(vote, gameName, playerName);
        afterVotingForMissionaryTeam();
    }

    /**
     * Runs after a player votes "Yes" or "No" for a missionary team.
     */
    private void afterVotingForMissionaryTeam() {
        Game.State result = GameController.ifEveryoneDoneVoting(gameName);
        if (result == Game.State.MISSION_LEADER_CHOOSING) {
            if (GameController.checkLeader(gameName, playerName)) {
                //mission leader voting visibility
            } else {
                //waiting for mission leader voting visibility
            }
        } else if (result == Game.State.MISSIONARIES_VOTING) {
            if (GameController.checkMissionary(gameName, playerName)) {
                //missionary voting visibility
            } else {
                //voting for missionary voting visibility
            }
        }
    }

    /**
     * Called when a Missionary clicks "PASS" when going on a Mission.
     */
    public void passMission() {
        // switch to visibilities for waiting for other missionaries
        boolean vote = true;
        GameController.addPassFailForMission(true, gameName);
        waitingForVotingForMission();
    }

    /**
     * Called when a Missionary clicks "FAIL" when going on a Mission.
     */
    public void failMission() {
        // switch to visibilities for waiting for other missionaries
        boolean vote = false;
        GameController.addPassFailForMission(false, gameName);
        waitingForVotingForMission();
    }

    private void waitingForVotingForMission() {
        Game.State result = GameController.ifMissionariesDoneVoting(gameName);
        if (result == Game.State.SPIES_WIN) {
            //change visibilities for spies win
        } else if (result == Game.State.RESISTANCE_WINS) {
            //change visibilities for resistance wins
        } else if (result == Game.State.MISSION_PASSED) {
            // change visibilities for mission passed
            //Cloud code
        } else if (result == Game.State.MISSION_FAILED) {
            // change visibilities for mission failed
            //Cloud code
        }
    }

}