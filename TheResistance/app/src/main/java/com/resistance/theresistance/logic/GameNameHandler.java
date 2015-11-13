package com.resistance.theresistance.logic;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.R;
import com.resistance.theresistance.activities.GameWaitingActivity;
import com.resistance.theresistance.activities.GameNameActivity;

/**
 * Handles user input for name of the game. Either create or join game.
 * Created by Candace on 11/13/2015.
 */
public class GameNameHandler {

    private static String gameName;
    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    protected GameNameActivity context;
    private static GameNameActivity activity;

    /**
     * Constructor
     *
     * @param context to access the application
     */
    public GameNameHandler(GameNameActivity context) {
        this.context = context;
    }

    /**
     * Called when a user wants to create a game
     * @param thisActivity GameNameActivity
     * @param name name of the new game room
     */
    public static void createGameHandler(GameNameActivity thisActivity, String name) {
        gameName = name;
        activity = thisActivity;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("gameName", "The retrieval succeeded");
                    if (count <= 0) {
                        createNewParseGameObject(gameName);
                        startActivity();
                    } else {
                        changeGameNameExistsView(activity, "create");
                    }
                } else {
                    Log.d("gameName", "The retrieval failed");
                }
            }
        });
    }

    /**
     * Called when a user wants to create a game
     * @param thisActivity GameNameActivity
     * @param name name of the game room to be joined
     */
    public static void joinGameHandler(GameNameActivity thisActivity, String name) {
        gameName = name;
        activity = thisActivity;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("gameName", "The retrieval succeeded");
                    if (count > 0) {
                        createNewParseGameObject(gameName);
                        startActivity();
                    } else {
                        changeGameNameExistsView(activity, "join");
                    }
                } else {
                    Log.d("gameName", "The retrieval failed");
                }
            }
        });
    }

    /**
     * Starts the new activity after user enters a unique name for the game room.
     */
    public static void startActivity() {
        Intent intent = new Intent(GameNameActivity.getContext(), GameWaitingActivity.class);
        intent.putExtra(EXTRA_MESSAGE, gameName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        GameNameActivity.getContext().startActivity(intent);
    }

    /**
     * Checks if game name already exists and changes view accordingly.
     * @param activity MainActivity
     * @param type either create or join
     */
    public static void changeGameNameExistsView(GameNameActivity activity, String type) {
        View existsView= (View) activity.findViewById(R.id.sorry_use_another_name);
        View secondView = (View) activity.findViewById(R.id.name_doesnt_exist);
        if (type.equals("join")) {
            existsView.setVisibility(View.INVISIBLE);
            secondView.setVisibility(View.VISIBLE);
        } else {
            secondView.setVisibility(View.INVISIBLE);
            existsView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Creates a new Parse object for game name.
     *
     * @param gameName name of the game
     */
    public static void createNewParseGameObject(String gameName) {
        Game object = new Game();
        object.setKeyword(gameName);
        /*ADD RELATION TO PLAYER */
        object.saveInBackground();
    }

}
