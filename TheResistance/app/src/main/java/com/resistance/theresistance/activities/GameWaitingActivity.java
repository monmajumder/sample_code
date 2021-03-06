package com.resistance.theresistance.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseCloud;
import com.parse.ParseException;
import com.resistance.theresistance.R;
import com.resistance.theresistance.logic.GameController;
import com.resistance.theresistance.logic.GameTimer;
import com.resistance.theresistance.views.MyTextView;

import java.util.ArrayList;
import java.util.HashMap;

import ru.biovamp.widget.CircleLayout;

/**
 * Game Waiting Activity
 */
public class GameWaitingActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    public final static String ANOTHER_EXTRA_MESSAGE = "";
    public final static String PLAYER_NAMES = "";
    public static ArrayList<String> playerNames;
    private static Intent intent;
    Button startButton;
    String gameName;
    String gameRoomStr;
    public static Context mContext;
    private int numPlayersInRoom;

    /**
     * Get context
     * @return context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * Called on create
     * @param savedInstanceState state of instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.numPlayersInRoom = 0;
        mContext = getBaseContext();

        setContentView(R.layout.activity_game);
        // Get the message from the intent
        intent = getIntent();
        gameName = intent.getStringExtra(GameNameActivity.EXTRA_MESSAGE);
        gameRoomStr = this.getResources().getString(R.string.game_room_name);

        gameRoomStr = gameRoomStr + " " + gameName;

        ((com.resistance.theresistance.views.MyTextView)findViewById(R.id.game_room_name)).setText(gameRoomStr);

        handleHost();
        addPlayerIcons();
        GameTimer.gameStarted(this, gameName);
    }

    /**
     * Checks if player is host and if player is host, player can see "Start" button.
     * If player is not host, displays "Waiting for host to begin"
     */
    private void handleHost() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String storedPlayer = preferences.getString("playerName", "none");
        Button startButton = (Button) findViewById(R.id.start_button);

        //If player is host, change visibilities for host
        if (GameController.checkHost(gameName, storedPlayer)) {
            //Make Start button visible
            startButton.setVisibility(View.VISIBLE);
        } else {
            //Display "Waiting for host to begin" message
            MyTextView waitingText = (MyTextView) findViewById(R.id.waiting_for_host);
            waitingText.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Called when a Host presses the "Start" button. Calls Cloud function to take care of starting a game.
     * @param view Button view
     */
    public void startGame(View view) {

        //Check if enough players.
        if (!GameController.checkEnoughPlayers(gameName)) {
            tooFewPlayers();
            return;
        }

        //Call Cloud function
        HashMap<String,Object> arguments = new HashMap<>();
        arguments.put("Name", gameName);
        try {
            ParseCloud.callFunction("startGame", arguments);
        } catch (ParseException e) {
            Log.d("startGame", "Cloud function did not call successfully");
        }

        intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(ANOTHER_EXTRA_MESSAGE, gameName);
        intent.putStringArrayListExtra(PLAYER_NAMES, playerNames);
        startActivity(intent);

    }

    /**
     * Displays a toast if there are not enough players to start a game.
     */
    private void tooFewPlayers() {
            Toast.makeText(this,"Too few players. Need at least 5 players to start.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Adds a player.
     * @param playerNumber
     */
    private void addPlayer(int playerNumber){
        TypedArray playerIcons = getResources().obtainTypedArray(R.array.player_imgs);
        TypedArray playerIds = getResources().obtainTypedArray(R.array.player_ids_ints);
        ArrayList<String> playerNames = GameController.updatePlayers(gameName);
        CircleLayout circleLayout = (CircleLayout) findViewById(R.id.circleview);

        // Creating a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);

        // Defining the RelativeLayout layout parameters.
        // In this case I want to fill its parent
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Creating a new ImageView
        ImageView iv = new ImageView(this);
        iv.setImageResource(playerIcons.getResourceId(playerNumber, -1));
        iv.setId(playerIds.getResourceId(playerNumber, -1));

        // Defining the layout parameters of the ImageView
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //Creating a new TextView
        MyTextView tv = new MyTextView(this);
        tv.setText(playerNames.get(playerNumber));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                44,
                this.getResources().getDisplayMetrics()
        );

        //Defining the layout parameters of the TextView
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.setMargins(0, px, 0, 0);

        // Setting the parameters on the views
        iv.setLayoutParams(imageParams);
        tv.setLayoutParams(textParams);

        // Adding the ImageView and TextView to the RelativeLayout as children
        relativeLayout.addView(iv);
        relativeLayout.addView(tv);

        // Setting the RelativeLayout as our content view
        circleLayout.addView(relativeLayout);
    }

    /**
     * Adds the player icons to the game play screen.
     */
    public void addPlayerIcons() {
        playerNames = GameController.updatePlayers(gameName);
        int numPlayers = playerNames.size();
        int changed = numPlayers - numPlayersInRoom;

        if (changed == 0) {
            return;
        }

        String a;
        for (int i = numPlayersInRoom; i < playerNames.size(); i++) {
            a = playerNames.size() + "";
            //Log.d("player Names size", a);
            addPlayer(i);
        }
        numPlayersInRoom = numPlayers;
    }

    /**
     * Starts the Game Play Activity.
     */
    public void startGamePlayActivity() {
        intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(ANOTHER_EXTRA_MESSAGE, gameName);
        startActivity(intent);
    }
}