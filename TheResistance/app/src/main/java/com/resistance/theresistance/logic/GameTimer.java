package com.resistance.theresistance.logic;

import android.util.Log;

import com.resistance.theresistance.activities.GamePlayActivity;
import com.resistance.theresistance.activities.GameWaitingActivity;
import com.resistance.theresistance.fragments.HistoryFragment;
import com.resistance.theresistance.fragments.PlayFragment;

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
                            thisActivity.addPlayerIcons();
                            Log.d("game started TIMER", "GAME HAS NOT STARTED");
                        } else {
                            timer.cancel();
                            thisActivity.startGamePlayActivity();
                            Log.d("game started TIMER", "GAME HAS STARTED");
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
    public static void missionLeaderDoneChoosing(PlayFragment fragment, String gameName) {
        final PlayFragment thisFragment = fragment;
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (GameController.checkMissionLeaderDoneChoosing(game)) {
                            timer.cancel();
                            List<String> missionaryTeam = GameController.getChosenMissionaries(game);
                            thisFragment.changeToVoteForMissionaries(missionaryTeam);
                            Log.d("missionleader TIMER", "I AM DONE");
                        } else {
                            Log.d("missionleader TIMER", "ONE TIME");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * Runs the Timer task for checking when everybody has finished voting.
     * @param fragment PlayFragment
     * @param gameName Name of the game
     */
    public static void everyoneDoneVoting(PlayFragment fragment, String gameName) {
        final PlayFragment thisFragment = fragment;
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Game.State returnedState = GameController.ifEveryoneDoneVoting(game);
                        if (returnedState == null) {
                            //DO NOTHING
                        } else if (returnedState == Game.State.MISSION_LEADER_CHOOSING) {
                            timer.cancel();
                            GamePlayActivity activity = (GamePlayActivity) thisFragment.getActivity();
                            HistoryFragment history = activity.getHistoryFragment();
                            Round round;

                            //Check if a new mission was created (check if mission team was rejected 5 times in a row)
                            if (GameController.getCurrentMission(game).getRounds().size() == 1) {
                                round = GameController.getGame(game).getPreviousMission().getCurrentRound();
                                history.addRound(round);
                                thisFragment.showMissionFailedFiveRejects(GameController.getGame(game).getMissions().size() - 1);
                            } else {
                                round = GameController.getCurrentMission(game).getPreviousRound();
                                if (GameController.getCurrentMission(game).getRounds().size() == 2) {
                                    history.addRoundInNewMission(round);
                                } else {
                                    history.addRound(round);
                                }
                                thisFragment.showMissionTeamRejected();
                            }

                            thisFragment.changeToMissionLeaderChoosing();
                            Log.d("TIMER CHECK", "I AM DONE");
                        } else if (returnedState == Game.State.MISSIONARIES_VOTING) {
                            timer.cancel();
                            thisFragment.showMissionTeamApproved();
                            thisFragment.changeToMissionaryVoting();
                            Log.d("everyone done TIMER", "I AM DONE");
                        } else if (returnedState == Game.State.SPIES_WIN) {
                            timer.cancel();
                            thisFragment.changeToGameOver(returnedState);
                        } else {
                            Log.d("everyone done TIMER", "ONE TIME");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * Runs the Timer task for checking when everybody has finished voting.
     * @param fragment PlayFragment
     * @param gameName Name of the game
     */
    public static void missionariesDoneVoting(PlayFragment fragment, String gameName) {
        final PlayFragment thisFragment = fragment;
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Game.State state = GameController.ifMissionariesDoneVoting(thisFragment, game);
                        if (state == null) {
                            Log.d("missionaries done TIMER", "ONE TIME");
                        } else if (state == Game.State.MISSION_LEADER_CHOOSING) {
                            timer.cancel();
                            GamePlayActivity activity = (GamePlayActivity) thisFragment.getActivity();
                            HistoryFragment history = activity.getHistoryFragment();

                            Mission previousMission = GameController.getGame(game).getPreviousMission();
                            Round round = previousMission.getCurrentRound();
                            if (previousMission.getRounds().size() == 1) {
                                history.addRoundInNewMission(round);
                            } else {
                                history.addRound(round);
                            }
                            thisFragment.changeToMissionLeaderChoosing();
                            Log.d("missionaries done TIMER", "I AM DONE");
                        } else if (state == Game.State.RESISTANCE_WINS) {
                            timer.cancel();
                            thisFragment.changeToGameOver(state);
                            Log.d("missionaries done TIMER", "I AM DONE");
                        } else if (state == Game.State.SPIES_WIN) {
                            timer.cancel();
                            thisFragment.changeToGameOver(state);
                            Log.d("missionaries done TIMER", "I AM DONE");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

}