package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.R;

public class GameNameActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    private static String gameName;
    private static Intent intent;
    private static View existsView;
    private static View secondView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(name);
        setContentView(R.layout.activity_game_name);
    }

    /** Called when user presses create button */
    public void createGame(View view) {
        existsView= (View) findViewById(R.id.sorry_use_another_name);
        secondView = (View) findViewById(R.id.name_doesnt_exist);
        intent = new Intent(this, GameActivity.class);
        EditText editText = (EditText) findViewById(R.id.enter_game_keyword);
        gameName = editText.getText().toString();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameNameObject");
        query.whereEqualTo("Name", gameName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("gameName", "The retrieval succeeded");
                    if (count <= 0) {
                        ParseObject nameObject = new ParseObject("GameNameObject");
                        nameObject.put("Name", gameName);
                        nameObject.saveInBackground();
                        intent.putExtra(EXTRA_MESSAGE, gameName);
                        startActivity(intent);
                    } else {
                        secondView.setVisibility(View.INVISIBLE);
                        existsView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("gameName", "The retrieval failed");
                }
            }
        });
    }

    /** Called when user presses join button */
    public void joinGame(View view) {
        existsView= (View) findViewById(R.id.name_doesnt_exist);
        secondView= (View) findViewById(R.id.sorry_use_another_name);
        intent = new Intent(this, GameActivity.class);
        EditText editText = (EditText) findViewById(R.id.enter_game_keyword);
        gameName = editText.getText().toString();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameNameObject");
        query.whereEqualTo("Name", gameName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("gameName", "The retrieval succeeded");
                    if (count > 0) {
                        intent.putExtra(EXTRA_MESSAGE, gameName);
                        startActivity(intent);
                    } else {
                        secondView.setVisibility(View.INVISIBLE);
                        existsView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("gameName", "The retrieval failed");
                }
            }
        });
    }
}
