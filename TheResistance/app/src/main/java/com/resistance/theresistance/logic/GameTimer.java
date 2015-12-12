package com.resistance.theresistance.logic;

import android.util.Log;

import com.resistance.theresistance.activities.GamePlayActivity;
import com.resistance.theresistance.activities.GameWaitingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles all tasks that are run on a timer. i.e. tasks that periodically pull from Parse until there is a change in state.
 */
public class GameTimer {

    /**
     * Constructor
     */
    public GameTimer() {
        super();
    }

    /**
     * Runs the Timer task for checking if a game has started.
     * @param activity GameWaitingActivity
     * @param gameName name of the game
     */
    public static void gameStarted(GameWaitingActivity activity, String gameName) {
        final GameWaitingActivity thisActivity = activity;
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!GameController.checkStarted(game)) {
                            ArrayList<String> players = GameController.updatePlayers(game);
                            if (players == null) {
                                Log.d("Players array", "IS NULL");
                            }
                            //thisActivity.addPlayerIcons();
                            Log.d("TIMER CHECK", "GAME HAS NOT STARTED");
                        } else {
                            timer.cancel();
                            thisActivity.startGamePlayActivity();
                            Log.d("TIMER CHECK", "GAME HAS STARTED");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * Runs the Timer task for checking when a Mission Leader has finished choosing the team.
     * @param gameName name of the game
     */
    public static void missionLeaderDoneChoosing(GamePlayActivity activity, String gameName) {
        final GamePlayActivity thisActivity = activity;
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (GameController.checkMissionLeaderDoneChoosing(game)) {
                            timer.cancel();
                            List<String> missionaryTeam = GameController.getChosenMissionaries(game);
                            thisActivity.changeToVoteForMissionaries(missionaryTeam);
                            Log.d("TIMER CHECK", "I AM DONE");
                        } else {
                            Log.d("TIMER CHECK", "ONE TIME");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    public static void everyoneDoneVoting(GamePlayActivity activity, String gameName) {
        final GamePlayActivity thisActivity = activity;
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (GameController.ifEveryoneDoneVoting(game) == Game.State.MISSION_LEADER_CHOOSING) {
                            timer.cancel();
                            thisActivity.showMissionTeamRejected();
                            thisActivity.changeToMissionLeaderChoosing();
                            Log.d("TIMER CHECK", "I AM DONE");
                        } else if (GameController.ifEveryoneDoneVoting(game) == Game.State.MISSIONARIES_VOTING) {
                            timer.cancel();
                            thisActivity.showMissionTeamApproved();
                            thisActivity.changeToMissionaryVoting();
                            Log.d("TIMER CHECK", "I AM DONE");
                        } else {
                            Log.d("TIMER CHECK", "ONE TIME");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    public static void missionariesDoneVoting(GamePlayActivity activity, String gameName) {
        final GamePlayActivity thisActivity = activity;
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Game.State state = GameController.ifMissionariesDoneVoting(thisActivity, game);
                        if (state == null) {
                            Log.d("TIMER CHECK", "ONE TIME");
                        } else if (state == Game.State.MISSION_LEADER_CHOOSING) {
                            timer.cancel();
                            thisActivity.changeToMissionLeaderChoosing();
                            Log.d("TIMER CHECK", "I AM DONE");
                        } else if (state == Game.State.RESISTANCE_WINS) {
                            timer.cancel();
                            thisActivity.changeToGameOver(state);
                            Log.d("TIMER CHECK", "I AM DONE");
                        } else if (state == Game.State.SPIES_WIN){
                            timer.cancel();
                            thisActivity.changeToGameOver(state);
                            Log.d("TIMER CHECK", "I AM DONE");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

}