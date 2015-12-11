package com.resistance.theresistance.logic;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.GetCallback;
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
    private static String playerName;
    public final static String EXTRA_MESSAGE = "com.resistance.theresistance.MESSAGE";
    private static GameNameActivity activity;

    /**
     * Constructor
     * @param context to access the application
     */
    public GameNameHandler(GameNameActivity context) {
        this.activity = context;
    }

    /**
     * Called when a user wants to create a game
     * @param thisActivity GameNameActivity
     * @param gName name of the new game room
     */
    public static void createGameHandler(GameNameActivity thisActivity, String gName, String pName) {
        gameName = gName;
        playerName = pName;
        activity = thisActivity;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            int count = query.count();
            Log.d("createGameHandler game", "The retrieval succeeded");
            if (count <= 0) {
                createNewGame(gameName, playerName);
                startActivity();
            } else {
                changeGameNameExistsView(activity, "create");
            }
        } catch (ParseException e) {
            Log.d("createGameHandler game", "The retrieval failed");
        }
    }

    /**
     * Called when a user wants to join a game.
     * @param thisActivity GameNameActivity
     * @param gName name of the game room to be joined
     */
    public static void joinGameHandler(GameNameActivity thisActivity, String gName, String pName) {
        gameName = gName;
        playerName = pName;
        activity = thisActivity;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Game gameObject = (Game) object;
            Log.d("joinGameHandler game", "The retrieval succeeded");
            if (GameController.checkStarted(gameName)) {
                gameHasAlreadyStarted(activity);
            } else if (gameObject.getNumPlayers() >= 10){
                tooManyPlayers(activity);
            } else {
                addPlayerToGame(object, playerName);
                object.saveInBackground();
                startActivity();
            }
        } catch (ParseException e) {
            Log.d("joinGameHandler game", "The retrieval failed");
            changeGameNameExistsView(activity, "join");
        }
    }

    /**
     * Creates a new game.
     * @param gameName Name of the game
     * @param playerName Name of player creating the game
     */
    private static void createNewGame(String gameName, String playerName) {
        final String keyword = gameName;
        final String player = playerName;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayerObject");
        query.whereEqualTo("Name", playerName);
        try {
            ParseObject object = query.getFirst();
            Log.d("createNewGame player", "The retrieval succeeded");
            Game newGame = new Game();
            newGame.setNumPlayers(0);
            newGame.setGameState(Game.State.WAITING_FOR_PLAYERS);
            newGame.setKeyword(keyword);
            newGame.setHost(player);
            newGame.addPlayer((Player) object);
            newGame.saveInBackground();

        } catch (ParseException e) {
            Log.d("createNewGame player", "The retrieval failed");
        }
    }

    /**
     * Adds a player to the game.
     * @param parseObject The game object
     * @param playerName The name of the player
     */
    private static void addPlayerToGame(ParseObject parseObject, String playerName) {
        final Game gameObject = (Game) parseObject;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayerObject");
        query.whereEqualTo("Name", playerName);
        try {
            ParseObject object = query.getFirst();
            Log.d("addPlayerToGame player", "The retrieval succeeded");
            gameObject.addPlayer((Player) object);
            gameObject.saveInBackground();
        } catch (ParseException e) {
            Log.d("addPlayerToGame player", "The retrieval failed");
        }
    }

    /**
     * Starts the new activity after user enters a unique name for the game room.
     */
    private static void startActivity() {
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
    private static void changeGameNameExistsView(GameNameActivity activity, String type) {
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
     * Displays a message to the user if they try to join a game that has already started.
     * @param thisActivity GameName Activity
     */
    private static void gameHasAlreadyStarted(GameNameActivity thisActivity) {
        Toast.makeText(thisActivity, "That game has already started.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a message to the user if they try to join a game that already has 10 players.
     * @param thisActivity GameName Activity
     */
    private static void tooManyPlayers(GameNameActivity thisActivity) {
        Toast.makeText(thisActivity, "This game is at maximum capacity.", Toast.LENGTH_SHORT).show();
    }
}