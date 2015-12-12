package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.resistance.theresistance.R;
import com.resistance.theresistance.fragments.HistoryFragment;
import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.logic.GameTimer;
import com.resistance.theresistance.fragments.PlayFragment;

import java.util.ArrayList;
import java.util.List;

public class GamePlayActivity extends FragmentActivity {

    String gameName;
    String playerName;
    int numPlayersOnMission;
    private static Intent intent;
    public ArrayList<String> playerNames;

    /**
     * Identifier for the first fragment.
     */
    public static final int FRAGMENT_ONE = 0;

    /**
     * Identifier for the second fragment.
     */
    public static final int FRAGMENT_TWO = 1;
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
     * Number of total fragments.
     */
    public static final int FRAGMENTS = 2;
    private void handleMissionary() {
        if (GameController.checkMissionary(gameName, playerName)) {
            //Change visibilities for Missionary, see pass/fail buttons
        } else {
            //change visibilities to waiting for missionaries to vote
        }
    }

    /**
     * The adapter definition of the fragments.
     * Called when a Mission Leader clicks "Done" after done choosing missionaries.
     */
    private FragmentPagerAdapter _fragmentPagerAdapter;
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
     * The ViewPager that hosts the section contents.
     */
    private ViewPager _viewPager;

    /**
     * List of fragments.
     */
    private List<android.support.v4.app.Fragment> _fragments = new ArrayList<android.support.v4.app.Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // Create fragments.
        _fragments.add(FRAGMENT_ONE, new PlayFragment());
        _fragments.add(FRAGMENT_TWO, new HistoryFragment());

        // Setup the fragments, defining the number of fragments, the screens and titles.
        _fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return FRAGMENTS;
            }

            @Override
            public Fragment getItem(final int position) {
                return _fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(final int position) {
                switch (position) {
                    case FRAGMENT_ONE:
                        return "Title One";
                    case FRAGMENT_TWO:
                        return "Title Two";
                    default:
                        return null;
                }
            }
        };
        _viewPager = (ViewPager) findViewById(R.id.pager);
        _viewPager.setAdapter(_fragmentPagerAdapter);
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



}