package com.resistance.theresistance.views;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.GameController;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends android.support.v4.app.Fragment {

    String gameName;
    String playerName;
    int numPlayersOnMission;
    private static Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play, null);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        gameName = preferences.getString("gameName", "none");
        playerName = preferences.getString("playerName", "none");

        //UI Components here

        String gameRoomStr = this.getResources().getString(R.string.game_room_name);

        gameRoomStr = gameRoomStr + " " + gameName;

        ((com.resistance.theresistance.views.MyTextView)view.findViewById(R.id.game_play_room_name)).setText(gameRoomStr);

        handleResistanceOrSpy();
        handleLeader();

        return view;
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


}