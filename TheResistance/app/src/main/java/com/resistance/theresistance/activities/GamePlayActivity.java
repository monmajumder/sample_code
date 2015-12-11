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
import android.widget.Toast;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.views.GamePlayFragment;

import java.util.ArrayList;
import java.util.List;

public class GamePlayActivity extends FragmentActivity {

    String gameName;
    String playerName;
    int numPlayersOnMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        gameName = preferences.getString("gameName", "none");
        playerName = preferences.getString("playerName", "none");

        handleResistanceOrSpy();
        changeToMissionLeaderChoosing();

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
            //Change visibilities for waiting for mission leader to choose
        }
    }

    /**
     * Checks if a player is a Missionary and handles visibilities if so.
     */
    private void handleMissionary() {
        if (GameController.checkMissionary(gameName, playerName)) {
            //Change visibilities for Missionary, see pass/fail buttons
        } else {
            //change visibilities to waiting for missionaries to vote
        }
    }

    /**
     * Called when a Mission Leader clicks "Done" after done choosing missionaries.
     */
    public void leaderChoosingMissionaries() {
        //somehow take in clicks for players who the leader chooses, make sure it is the right number, numPlayersOnMission
        ArrayList<String> chosenMissionaries = new ArrayList<>();
        if (chosenMissionaries.size() != this.numPlayersOnMission) {
            Toast.makeText(this, "Please choose exactly " + String.valueOf(this.numPlayersOnMission) + " players.", Toast.LENGTH_SHORT).show();
            //somehow redo taking in the clicks for the players
        }
        GameController.addChosenMissionaries(gameName, chosenMissionaries);
        GameController.changeState(gameName, Game.State.VOTE_FOR_MISSIONARIES);
        //change visibilities to "please wait"
    }

    /**
     * Called when a player clicks "YES" when voting for a Missionary team.
     */
    public void yesMissionaryTeam() {
        // switch to visibilities for waiting for everyone to finish voting
        boolean vote = true;
        GameController.addVoteForMissionaries(vote, gameName, playerName);
    }

    /**
     * Called when a player clicks "NO" when voting for a Missionary team.
     */
    public void noMissionaryTeam() {
        // switch to visibilities for waiting for everyone to finish voting
        boolean vote = false;
        GameController.addVoteForMissionaries(vote, gameName, playerName);
    }

    /**
     * Called when a Missionary clicks "PASS" when going on a Mission.
     */
    public void passMission() {
        // switch to visibilities for waiting for other missionaries
        boolean vote = true;
        GameController.addPassFailForMission(true, gameName);
    }

    /**
     * Called when a Missionary clicks "FAIL" when going on a Mission.
     */
    public void failMission() {
        // switch to visibilities for waiting for other missionaries
        boolean vote = false;
        GameController.addPassFailForMission(false, gameName);
    }

    /**
     * Called when the game state is Mission Leader Choosing.
     */
    public void changeToMissionLeaderChoosing() {
        handleLeader();
        GameTimer.missionLeaderDoneChoosing(this, gameName);
    }

    /**
     * Called when the game state is Vote For Missionaries.
     * @param missionaryTeam the chosen missionaries
     */
    public void changeToVoteForMissionaries(List<String> missionaryTeam) {
        //VISIBILITIES FOR DISPLAYING BUTTONS FOR VOTING FOR YES OR NO
        //VISIBILITIES FOR DISPLAYING CHOSEN MISSIONARY TEAM
        GameTimer.everyoneDoneVoting(this, gameName);
    }

    /**
     * Displays a toast if the Missionary Team was approved.
     */
    public void showMissionTeamApproved() {
        Toast.makeText(this, "The Mission Team was approved.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a toast if the Missionary Team was rejected.
     */
    public void showMissionTeamRejected() {
        Toast.makeText(this, "The Mission Team was rejected.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the game state is Missionaries Voting.
     */
    public void changeToMissionaryVoting() {
        handleMissionary();
        GameTimer.missionariesDoneVoting(this, gameName);
    }

    /**
     * Called when a mission was passed. Displays a toast and updates the UI.
     * @param missionNum Mission number that was passed.
     */
    public void showMissionPassed(int missionNum) {
        Toast.makeText(this, "The Mission passed!", Toast.LENGTH_SHORT).show();
        //DO SOMETHING IN THE LITTLE THING IN THE MIDDLE TO SHOW THAT THE MISSION PASSED AT THE CERTAIN MISSION NUMBER
    }

    /**
     * Called when a mission was failed. Displays a toast and updates the UI.
     * @param missionNum Mission number that was failed.
     */
    public void showMissionFailed(int missionNum) {
        Toast.makeText(this, "The Mission failed!", Toast.LENGTH_SHORT).show();
        //DO SOMETHING IN THE LITTLE THING IN THE MIDDLE TO SHOW THAT THE MISSION FAILED AT THE CERTAIN MISSION NUMBER
    }

    /**
     * Starts the Game Over activity.
     * @param state State of the game, either Resistance Wins or Spies Win.
     */
    public void changeToGameOver(Game.State state) {
        //SWITCH TO GAME OVER ACTIVITY
        //DO SOMETHING TO DELETE EVERYTHING. HOLY SHIT.
    }

    /**
     * DELETE!!!!!!!!!!
     * Test game.
     */
    public void testGame() {
        //TEST IF ISRESISTANCE WORKS. DELETE.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedPlayer = preferences.getString("playerName", "none");
        Log.d("checkPlayerName", storedPlayer);
        if (GameController.isResistance(storedPlayer)) {
            Log.d("IsResistance", storedPlayer);
        } else {
            Log.d("IsNotResistance", storedPlayer);
        }
    }
}