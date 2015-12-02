package com.resistance.theresistance.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.views.MyTextView;

import java.util.ArrayList;

/**
 * Game Activity
 */
public class GameWaitingActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    public final static String ANOTHER_EXTRA_MESSAGE = "";
    private static Intent intent;
    Button startButton;
    String gameName;
    String gameRoomStr;

    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        // Get the message from the intent
        intent = getIntent();
        gameName = intent.getStringExtra(GameNameActivity.EXTRA_MESSAGE);
        gameRoomStr = this.getResources().getString(R.string.game_room_name);

        gameRoomStr = gameRoomStr + " " + gameName;

        ((com.resistance.theresistance.views.MyTextView)findViewById(R.id.game_room_name)).setText(gameRoomStr);

//        ImageView player1 = new ImageView(this);
//        player1.setVisibility(View.VISIBLE);

        //Start GameActivity on start button click
        startButton = (Button) findViewById(R.id.button);
        // Capture button clicks
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {

                startGame();
            }
        });


        //Abstract this method
        //Add some sort of timer, do this every second or so
        //Do it until the game is started (game state changes after host presses start)
        //Check if game started
        if (!GameController.checkStarted(gameName)) {
            ArrayList<String> players = GameController.updatePlayers(gameName);
            if (players == null) {
                Log.d("Players array", "IS NULL");
            }
            setPlayerNames(players);
        } else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String playerName = preferences.getString("playerName", "none");
            if (GameController.isResistance(playerName)) {
                //Go to GamePlayActivity with certain layout for Resistance?
            } else {
                //Go to GamePlayActivity with certain layout for Spies?
            }
        }
    }

    private void setPlayerNames(ArrayList<String> players){
        MyTextView player1 = (MyTextView)findViewById(R.id.pink_player_label);
        player1.setText(players.get(0));
    }

    /**
     * Checks if player is host and if player is host, player can see "Start" button.
     */
    private void handleHost() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedPlayer = preferences.getString("playerName","none");

        //If player is host, change visibilities for host
        if (GameController.checkHost(gameName, storedPlayer)) {
            //Make Start button visible
        }
    }

    private void startGame() {
        //When host clicks start Game, run Cloud code.
        //Starting the new activity should be elsewhere
        intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(ANOTHER_EXTRA_MESSAGE, gameName);
        startActivity(intent);


        /**
        //TEST IF CHECKHOST WORKS. DELETE.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedPlayer = preferences.getString("playerName","none");
        Log.d("checkPlayerName", storedPlayer);
        if (GameController.checkHost(gameName, storedPlayer)) {
            Log.d("CHECKING", "YES. THIS PLAYER IS HOST.");
        } else {
            Log.d("CHECKING", "NO. THIS PLAYER IS NOT HOST.");
        }

         //TEST IF CHECK STATE WORKS. DELETE.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedPlayer = preferences.getString("playerName","none");
        Log.d("checkPlayerName", storedPlayer);
        if (GameController.checkStarted(gameName)) {
            Log.d("CHECKING", "YES. GAME HAS STARTED.");
        } else {
            Log.d("CHECKING", "NO. GAME HAS NOT STARTED.");
        }

        //TEST IF UPDATE PLAYERS WORKS. DELETE.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedPlayer = preferences.getString("playerName","none");
        Log.d("checkPlayerName", storedPlayer);
        ArrayList<String> testPlayers = new ArrayList<>();
        testPlayers = GameController.updatePlayers(gameName);
        for (String name : testPlayers) {
            Log.d("Player name", name);
        } **/
    }

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
//        query.whereEqualTo("Name", gameName);
//        query.countInBackground(new CountCallback() {
//            @Override
//            public void done(int count, ParseException e) {
//                if (e == null) {
//                    Log.d("gameName", "The retrieval succeeded");
//                    if (count <= 0) {
//                        createNewParseGameObject(gameName);
//                        createIntent(gameName);
//                    } else {
//                        secondView.setVisibility(View.INVISIBLE);
//                        existsView.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    Log.d("gameName", "The retrieval failed");
//                }
//            }
//        });

}
