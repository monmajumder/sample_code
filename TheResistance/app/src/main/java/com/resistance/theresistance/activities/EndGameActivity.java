package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.resistance.theresistance.R;
import com.resistance.theresistance.fragments.PlayFragment;

public class EndGameActivity extends AppCompatActivity {

    private static Intent intent;
    String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        intent = getIntent();
        winner = intent.getStringExtra(PlayFragment.EXTRA_MESSAGE);

        visibility(winner);
    }

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

    //TO do: FIX THIS.
    public void startNewGame(View view) {
        intent = new Intent(this, GameNameActivity.class);
        startActivity(intent);
    }

    //To do: FIX THIS.
    public void endGame(View view) {
        //intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        finish();
        System.exit(0);
    }
}
