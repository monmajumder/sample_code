package com.resistance.theresistance.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.resistance.theresistance.R;
import com.resistance.theresistance.fragments.PlayFragment;

public class EndGameActivity extends AppCompatActivity {

    private static Intent intent;
    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    String winner;

    /**
     * Called on create.
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        intent = getIntent();
        winner = intent.getStringExtra(PlayFragment.EXTRA_MESSAGE);

        visibility(winner);
    }

    /**
     * Sets the visibility on the activity
     * @param s String that specifies the winner
     */
    private void visibility(String s) {
        if (winner == "Resistance") {
            TextView view = (TextView)findViewById(R.id.resistance_winstextview);
            view.setVisibility(View.VISIBLE);
        }
        else {
            TextView view = (TextView)findViewById(R.id.spies_wintextview);
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Starts the Game Name Activity
     * @param view Button click
     */
    public void startNewGame(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String playerName = preferences.getString("playerName", "none");
        intent = new Intent(this, GameNameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, playerName);
        startActivity(intent);
    }

}
