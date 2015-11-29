package com.resistance.theresistance.logic;

import android.util.Log;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions including starting and playing a Game.
 * @author Group 11
 *
 */
public class GameController {

    //DON'T NEED THIS!!!! DELETE.
    //Temporarily stores the games, will eventually be replaced by Parse
    ArrayList<Game> games = new ArrayList<Game>();

    /**
     * Constructor for GameController.
     */
    public GameController(){
        super();
    }

    /**
     * Checks whether or not a player is the host.
     * @param gameName Name of the game
     * @param playerName Name of the player
     * @return True if player is the host, false otherwise
     */
    public static boolean checkHost(String gameName, String playerName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkHost game", "The retrieval succeeded");
            Game gameObject = (Game)object;
            if (gameObject.getHost().equals(playerName)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            Log.d("checkHost game", "The retrieval failed");
            return false;
        }
    }

    /**
     * Extracts the name of the players currently in a game.
     * @param gameName Name of the game
     * @return ArrayList containing the names of the Players in a game
     */
    public static ArrayList<String> updatePlayers(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("updatePlayers game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            ArrayList<String> allPlayers = new ArrayList<>();

            List<Player> listOfPlayers = gameObject.fetchIfNeeded().getList("Players");
            for (Player player : listOfPlayers) {
                allPlayers.add(player.fetchIfNeeded().getString("Name"));
            }
            return allPlayers;
        } catch (ParseException e) {
            Log.d("updatePlayers game", "The retrieval failed");
            return null;
        }
    }

    /**
     * Checks whether a game is started.
     * @param gameName Name of the game
     * @return True if game has started, false otherwise
     */
    public static boolean checkStarted(String gameName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        try {
            ParseObject object = query.getFirst();
            Log.d("checkStarted game", "The retrieval succeeded");
            Game gameObject = (Game) object;
            if (gameObject.getGameState() == Game.State.WAITING_FOR_PLAYERS) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            Log.d("checkStarted game", "The retrieval failed");
            return false;
        }
    }

    /**
     * Checks whether a certain player is Resistance.
     * @param playerName Name of player
     * @return True if player is Resistor, false otherwise
     */
    public static boolean isResistance(String playerName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PlayerObject");
        query.whereEqualTo("Name", playerName);
        try {
            ParseObject object = query.getFirst();
            Log.d("isResistance player", "The retrieval succeeded");
            Player playerObject = (Player) object;
            if (playerObject.getPlayerType() == Player.PlayerType.RESISTOR) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            Log.d("isResistance player", "The retrieval failed");
            return false;
        }
    }

    /**
     * Checks whether a certain player is the Mission Leader.
     * @param gameName Name of the game
     * @param playerName Name of the player
     * @return True if yes, false otherwise
     */
    public static boolean checkLeader(String gameName, String playerName) {
        return false;
    }

    /**
     * Checks whether a certain player is a Missionary.
     * @param gameName Name of the game
     * @param playerName Name of the player
     * @return True if yes, false otherwise
     */
    public static boolean checkMissionary(String gameName, String playerName) {
        return false;
    }

    /**
     * Checks whether a Mission Leader has finished choosing Missionary Team.
     * @param gameName Name of the game
     * @return True if Mission Leader is finished choosing, false otherwise
     */
    public static boolean checkMissionLeaderDoneChoosing(String gameName) {
        return false;
    }






    /**DELETE?**/
   /**
    * Plays the game
    * @param game the game to be played
    */
   public void playGame(Game game){
       Game curr_game;
       int numPlayers, numSpies;
       Player curr_player;

       curr_game = game;
       numPlayers = game.getNumPlayers();
       switch(numPlayers) {
           case 5:
       }
   }

   /**
    * Deletes a game
    * @param game the Game to be deleted
    */
   public void removeGame(Game game){
       games.remove(game);
   }

}