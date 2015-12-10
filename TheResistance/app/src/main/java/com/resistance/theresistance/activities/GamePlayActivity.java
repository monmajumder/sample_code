package com.resistance.theresistance.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.views.GamePlayFragment;
import com.resistance.theresistance.views.MyTextView;

import java.util.ArrayList;

import ru.biovamp.widget.CircleLayout;

public class GamePlayActivity extends FragmentActivity {

    String gameName;
    String playerName;
    int numPlayersOnMission;
    private static Intent intent;
    public ArrayList<String> playerNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);




        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        gameName = preferences.getString("gameName", "none");
        playerName = preferences.getString("playerName", "none");


        addPlayerIcons();

        handleResistanceOrSpy();
        handleLeader();

        //GameTimer.missionLeaderDoneChoosing(this, gameName);

        //TEST IF GET NUMBER OF MISSIONARIES REQUIRED WORKS. DELETE.
        //int numPlayers = GameController.getMissionariesRequired(gameName);
        //Log.d("Missionaries required", String.valueOf(numPlayers));

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
            this.numPlayersOnMission = GameController.getMissionariesRequired(gameName);
            Log.d("handleLeader numPlayers", String.valueOf(numPlayersOnMission));
            //Change visibilities for Mission Leader, with number of missionaries that need to be chosen
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
        //somehow take in clicks for players who the leader chooses, make sure it is the right number, numPlayersOnMission
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

    private void addPlayer(int playerNumber){
        TypedArray playerIcons = getResources().obtainTypedArray(R.array.player_imgs);
        TypedArray playerIds = getResources().obtainTypedArray(R.array.player_ids_ints);
        ArrayList<String> playerNames = GameController.updatePlayers(gameName);
        CircleLayout circleLayout = (CircleLayout) findViewById(R.id.circleview2);


        // Creating a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);

        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Creating a new ImageView
        ImageView iv = new ImageView(this);
        iv.setImageResource(playerIcons.getResourceId(playerNumber, -1));
        iv.setId(playerIds.getResourceId(playerNumber, -1));

        // Defining the layout parameters of the ImageView
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        //Creating a new TextView
        MyTextView tv = new MyTextView(this);
        tv.setText(playerNames.get(playerNumber));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                44,
                this.getResources().getDisplayMetrics()
        );

        //Defining the layout parameters of the TextView
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.setMargins(0, px, 0, 0);

        // Setting the parameters on the views
        iv.setLayoutParams(imageParams);
        tv.setLayoutParams(textParams);

        // Adding the ImageView and TextView to the RelativeLayout as children
        relativeLayout.addView(iv);
        relativeLayout.addView(tv);

        // Setting the RelativeLayout as our content view
        circleLayout.addView(relativeLayout);
    }

    public void addPlayerIcons() {

        ArrayList<String> playerNames = GameController.updatePlayers(gameName);
        Log.d("Player names size", String.valueOf(playerNames.size()));

        for (int i = 0; i < playerNames.size(); i++) {
            addPlayer(i);
        }
    }

}