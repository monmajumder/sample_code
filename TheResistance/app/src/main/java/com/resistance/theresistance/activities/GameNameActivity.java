package com.resistance.theresistance.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.GameNameHandler;

/**
 * Game Name Activity
 */
public class GameNameActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    private static String gameName;
    public static Context mContext;

    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getBaseContext();
        setContentView(R.layout.activity_game_name);

        // Get the message from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra(GameNameActivity.EXTRA_MESSAGE);
        String hiString = this.getResources().getString(R.string.hi_name);

        hiString = hiString + " " + name + ".";

        ((com.resistance.theresistance.views.MyTextView)findViewById(R.id.hi_name)).setText(hiString);
        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(name);

    }

    /**
     * Get context
     * @return context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * Called when a user clicks create game
     * @param view View that is called
     */
    public void createGame(View view) {
        EditText editText = (EditText) findViewById(R.id.enter_game_keyword);
        gameName = editText.getText().toString();

        if (!nameEntered(gameName)) {
            return;
        }
        GameNameHandler.createGameHandler(this,gameName, getPlayerName());
        saveInPreferences(gameName);
    }

    /**
     * Called when a user clicks join game.
     * @param view the view that is called
     */
    public void joinGame(View view) {
        EditText editText = (EditText) findViewById(R.id.enter_game_keyword);
        gameName = editText.getText().toString();

        if (!nameEntered(gameName)) {
            return;
        }
        GameNameHandler.joinGameHandler(this, gameName, getPlayerName());
        saveInPreferences(gameName);
    }

    /**
     * Checks if user entered name.
     * @param gameName name of game
     * @return true if entered, false if not
     */
    private boolean nameEntered(String gameName) {
        if (gameName.matches("")) {
            Toast.makeText(this, "You did not enter a game name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Save the game name in preferences, so name can be accessed across activities.
     * @param gameName name of the game
     */
    private void saveInPreferences(String gameName) {
        //Store name in shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("gameName", gameName);
        editor.commit();

        //Check if gameName stored
        //String storedPreference = preferences.getString("gameName","none");
        //Log.d("checkGameName", storedPreference);
    }

    /**
     * Retrieves the name of the player from the shared preferences.
     * @return Name of the player
     */
    private String getPlayerName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedName = preferences.getString("playerName","none");
        return storedName;
    }
}