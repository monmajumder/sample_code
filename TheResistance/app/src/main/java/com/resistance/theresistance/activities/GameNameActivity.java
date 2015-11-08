package com.resistance.theresistance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.R;

/**
 * Game Name Activity
 */
public class GameNameActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    private static String gameName;
    private static Intent intent;
    private static View existsView;
    private static View secondView;

    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
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

    /**
     * Called when a user clicks create game
     * @param view View that is called
     */
    public void createGame(View view) {
        existsView= (View) findViewById(R.id.sorry_use_another_name);
        secondView = (View) findViewById(R.id.name_doesnt_exist);
        intent = new Intent(this, GameActivity.class);
        EditText editText = (EditText) findViewById(R.id.enter_game_keyword);
        gameName = editText.getText().toString();

        if (!nameEntered(gameName)) {
            return;
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("gameName", "The retrieval succeeded");
                    if (count <= 0) {
                        createNewParseGameObject(gameName);
                        createIntent(gameName);
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

    /**
     * Called when a user clicks join game.
     * @param view the view that is called
     */
    public void joinGame(View view) {
        existsView= (View) findViewById(R.id.name_doesnt_exist);
        secondView= (View) findViewById(R.id.sorry_use_another_name);
        EditText editText = (EditText) findViewById(R.id.enter_game_keyword);
        gameName = editText.getText().toString();

        if (!nameEntered(gameName)) {
            return;
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    Log.d("gameName", "The retrieval succeeded");
                    if (count > 0) {
                        createIntent(gameName);
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

    /**
     * Checks if user entered name.
     * @param gameName name of game
     * @return true if entered, false if not
     */
    public boolean nameEntered(String gameName) {
        if (gameName.matches("")) {
            Toast.makeText(this, "You did not enter a game name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * Creates a new Parse object for game name.
     * @param gameName name of the game
     */
    public void createNewParseGameObject(String gameName) {
        ParseObject nameObject = new ParseObject("GameObject");
        nameObject.put("Name", gameName);
        nameObject.saveInBackground();
    }

    /**
     * Create an intent
     * @param gameName name of the game
     */
    public void createIntent(String gameName) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, gameName);
        startActivity(intent);
    }
}
