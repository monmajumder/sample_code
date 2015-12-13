package com.resistance.theresistance.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.resistance.theresistance.R;
import com.resistance.theresistance.activities.EndGameActivity;
import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.logic.GameTimer;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that shows the Game Play.
 */
public class PlayFragment extends android.support.v4.app.Fragment {

    private String gameName;
    private String playerName;
    private int numPlayersOnMission;
    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play, null);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        gameName = preferences.getString("gameName", "none");
        playerName = preferences.getString("playerName", "none");

        this.context = getActivity().getApplicationContext();

        //UI Components here

        String gameRoomStr = this.getResources().getString(R.string.game_room_name);

        gameRoomStr = gameRoomStr + " " + gameName;

        ((com.resistance.theresistance.views.MyTextView)view.findViewById(R.id.game_play_room_name)).setText(gameRoomStr);

        handleResistanceOrSpy();
        changeToMissionLeaderChoosing();

        return view;
    }

    /**
     * Checks if a player is Resistance or Spy and changes visibilities if so.
     */
    private void handleResistanceOrSpy() {
        if (GameController.isResistance(playerName)) {
            //Visibilities for resistance
        } else {
            //visibilities for spies
        }
    }

    /**
     * Called when the game state is Mission Leader Choosing.
     */
    public void changeToMissionLeaderChoosing() {
        handleLeader();
        GameTimer.missionLeaderDoneChoosing(this, gameName);
    }

    /**
     * Handles the leader, displays the start button if leader.
     */
    private void handleLeader() {
        Log.d("handleLeader", gameName);
        String currentLeader = GameController.getCurrentMission(gameName).getCurrentMissionLeader();
        // Do something to change position of little star

        if (GameController.checkLeader(gameName, playerName)) {
            this.numPlayersOnMission = GameController.getMissionariesRequired(gameName);
            Log.d("handleLeader numPlayers", String.valueOf(numPlayersOnMission));
            //Change visibilities for Mission Leader, with number of missionaries that need to be chosen
        } else {
            //Change visibilities for waiting for mission leader to choose
        }
    }

    /**
     * Handles when a leader is choosing missionaries.
     */
    public void leaderChoosingMissionaries() {
        //somehow take in clicks for players who the leader chooses, make sure it is the right number, numPlayersOnMission
        ArrayList<String> chosenMissionaries = new ArrayList<>();
        if (chosenMissionaries.size() != this.numPlayersOnMission) {
            Toast.makeText(context, "Please choose exactly " + String.valueOf(this.numPlayersOnMission) + " players.", Toast.LENGTH_SHORT).show();
            //somehow redo taking in the clicks for the players
        }
        GameController.addChosenMissionaries(gameName, chosenMissionaries);
        GameController.changeState(gameName, Game.State.VOTE_FOR_MISSIONARIES);
        //change visibilities to "please wait"
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
     * Called when a player clicks "Accept" when voting for a Missionary team.
     */
    public void acceptMissionaryTeam() {
        // switch to visibilities for waiting for everyone to finish voting
        boolean vote = true;
        GameController.addVoteForMissionaries(vote, gameName, playerName);
    }

    /**
     * Called when a player clicks "Reject" when voting for a Missionary team.
     */
    public void rejectMissionaryTeam() {
        // switch to visibilities for waiting for everyone to finish voting
        boolean vote = false;
        GameController.addVoteForMissionaries(vote, gameName, playerName);
    }

    /**
     * Called when the game state is Missionaries Voting.
     */
    public void changeToMissionaryVoting() {
        handleMissionary();
        GameTimer.missionariesDoneVoting(this, gameName);
    }

    /**
     * Handles the missionary, displays pass/fail buttons.
     */
    private void handleMissionary() {
        if (GameController.checkMissionary(gameName, playerName)) {
            //Change visibilities for Missionary, see pass/fail buttons
        } else {
            //change visibilities to waiting for missionaries to vote
        }
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
     * Displays a toast if the Missionary Team was approved.
     */
    public void showMissionTeamApproved() {
        Toast.makeText(context, "The Mission Team was approved.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a toast if the Missionary Team was rejected.
     */
    public void showMissionTeamRejected() {
        Toast.makeText(context, "The Mission Team was rejected.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when a mission was passed. Displays a toast and updates the UI.
     * @param missionNum Mission number that was passed.
     */
    public void showMissionPassed(int missionNum) {
        Toast.makeText(context, "The Mission passed!", Toast.LENGTH_SHORT).show();
        //DO SOMETHING IN THE LITTLE THING IN THE MIDDLE TO SHOW THAT THE MISSION PASSED AT THE CERTAIN MISSION NUMBER
    }

    /**
     * Called when a mission was failed. Displays a toast and updates the UI.
     * @param missionNum Mission number that was failed.
     */
    public void showMissionFailed(int missionNum) {
        Toast.makeText(context, "The Mission failed!", Toast.LENGTH_SHORT).show();
        //DO SOMETHING IN THE LITTLE THING IN THE MIDDLE TO SHOW THAT THE MISSION FAILED AT THE CERTAIN MISSION NUMBER
    }

    /**
     * Starts the Game Over activity.
     * @param state State of the game, either Resistance Wins or Spies Win.
     */
    public void changeToGameOver(Game.State state) {
        //DELETE EVERYTHING??
        Intent intent = new Intent(this.getContext(), EndGameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, state.toString());
        this.getContext().startActivity(intent);
    }
}