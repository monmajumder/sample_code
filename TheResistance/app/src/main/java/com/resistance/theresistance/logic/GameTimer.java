package com.resistance.theresistance.logic;

import android.util.Log;
import android.view.View;

import com.resistance.theresistance.R;
import com.resistance.theresistance.activities.GameNameActivity;
import com.resistance.theresistance.activities.GamePlayActivity;
import com.resistance.theresistance.activities.GameWaitingActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Candace on 12/7/2015.
 */
public class GameTimer {

    /**
     * Runs the Timer task for checking when a Mission Leader has finished choosing the team.
     * @param gameName name of the game
     */
    public static void missionLeaderDoneChoosing(GamePlayActivity activity, String gameName) {
        final GamePlayActivity thisActivity = activity;
        final GameController timerController = new GameController();
        final String game = gameName;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                thisActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (timerController.checkMissionLeaderDoneChoosing(game)) {
                            timer.cancel();
                            changeView(thisActivity);
                            // Log.d("TIMER CHECK", "I AM DONE");
                        } else {
                            // Log.d("TIMER CHECK", "ONE TIME");
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
