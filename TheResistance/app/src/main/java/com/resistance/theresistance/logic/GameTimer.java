package com.resistance.theresistance.logic;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.resistance.theresistance.R;
import com.resistance.theresistance.activities.GamePlayActivity;
import com.resistance.theresistance.activities.GameWaitingActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles all tasks that are run on a timer. i.e. tasks that periodically pull from Parse until there is a change in state.
 */
public class GameTimer {

    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    private static GameWaitingActivity activity;

    /**
     * Constructor
     * @param context to access the application
     */
    public GameTimer(GameWaitingActivity context) {
        this.activity = context;
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
//                            thisActivity.addPlayerIcons();
                            Log.d("TIMER CHECK", "GAME HAS NOT STARTED");
                        } else {
                            timer.cancel();
                            startGamePlayActivity(game);
                            Log.d("TIMER CHECK", "GAME HAS STARTED");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * Starts the new activity after the game has started.
     */
    private static void startGamePlayActivity(String gameName) {
        Intent intent = new Intent(GameWaitingActivity.getContext(), GamePlayActivity.class);
        intent.putExtra(EXTRA_MESSAGE, gameName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        GameWaitingActivity.getContext().startActivity(intent);
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
                            changeView(thisActivity);
                            Log.d("TIMER CHECK", "I AM DONE");
                        } else {
                            Log.d("TIMER CHECK", "ONE TIME");
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * Change View. CHANGE TO REFLECT VISIBILITY CHANGE WHEN LEADER DONE CHOOSING.
     * @param activity GameWaitingActivity
     */
    private static void changeView(GamePlayActivity activity) {
        View existsView= (View) activity.findViewById(R.id.waiting_for_host);
        existsView.setVisibility(View.INVISIBLE);
    }


}
