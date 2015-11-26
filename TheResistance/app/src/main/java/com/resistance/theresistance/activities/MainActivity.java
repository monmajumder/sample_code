package com.resistance.theresistance.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.MyApplication;
import com.resistance.theresistance.logic.PlayerNameHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Activity. First screen that asks for username.
 */
public class MainActivity extends AppCompatActivity {

    private static String playerName;
    public static Context mContext;

    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getBaseContext();
        //This comment is here to fix issue.
        setContentView(R.layout.activity_main);
    }

    /**
     * Get context
     * @return context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * Inflate the menu
     * @param menu the menu
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles action bar item.
     * @param item item that is called
     * @return if no inspection, true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when user clicks the Enter button after typing name
     */
    public void enterName(View view) {
        EditText editText = (EditText) findViewById(R.id.enter_name);
        playerName = editText.getText().toString();

        if (!nameEntered(playerName)) {
            return;
        }

        PlayerNameHandler.handleUsername(this, playerName);

        //Store name in shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("playerName", playerName);
        editor.commit();

        //Check if playerName stored
        //String storedPreference = preferences.getString("playerName","none");
        //Log.d("check",storedPreference);
    }

    /**
     * Checks if user entered name.
     * @param playerName name of player
     * @return true if entered, false if not
     */
    private boolean nameEntered(String playerName) {
        if (playerName.matches("")) {
            Toast.makeText(this, "You did not enter a name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}