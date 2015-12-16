package com.resistance.theresistance.logic;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.R;
import com.resistance.theresistance.activities.GameNameActivity;
import com.resistance.theresistance.activities.MainActivity;

/**
 * Handles user input for username.
 */
public class PlayerNameHandler {

    private static String playerName;
    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    protected MainActivity context;
    private static MainActivity activity;

    /**
     * Constructor
     * @param context to access the application
     */
    public PlayerNameHandler(MainActivity context) {
        this.context = context;
    }

    /**
     * Handles the user input for username. Checks if it already exists in Parse
     * @param thisActivity MainActivity
     * @param name player entered username
     */
    public static void handleUsername(MainActivity thisActivity, String name) {
        playerName = name;
        activity = thisActivity;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayerObject");
        query.whereEqualTo("Name", playerName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("handleUsername name", "The retrieval succeeded");
                    if (count <= 0) {
                        createNewParsePlayerObject(playerName);
                        startActivity();
                    } else {
                        changeNameExistsView(activity);
                    }
                } else {
                    Log.d("handleUsername name", "The retrieval failed");
                }
            }
        });
    }

    /**
     * Creates a new Parse object for player name.
     * @param playerName name of the player
     */
    private static void createNewParsePlayerObject(String playerName) {
        Player object = new Player();
        object.setUsername(playerName);
        try {
            object.save();
        } catch (ParseException e) {
            Log.d("Parse", "Object not saved");
        }
    }

    /**
     * Starts the new activity after user enters a unique name.
     */
    private static void startActivity() {
        Intent intent = new Intent(MainActivity.getContext(), GameNameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, playerName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.getContext().startActivity(intent);
    }

    /**
     * Called when a username already exists. Displays already exists message.
     * @param activity MainActivity
     */
    private static void changeNameExistsView(MainActivity activity) {
        View alreadyExistsView= (View) activity.findViewById(R.id.sorry_use_another_name);
        alreadyExistsView.setVisibility(View.VISIBLE);
    }
}