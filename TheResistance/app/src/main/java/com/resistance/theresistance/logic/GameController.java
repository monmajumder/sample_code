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


    public void chooseMissionaries() {

    }

    public void waitForMissionLeader() {

    }











    /**DELETE?**/
    /**
     * DON'T NEED THIS.
     * Creates a game.
     * @param hostName Name of the host
     * @param keyword Keyword of the game
     */
    public void createGame (String hostName, String keyword){
       //Will eventually add Parse stuff to access and store GameObjects

        Player creator = new Player(hostName);
        Host host = new Host(creator);

        Game newGame = new Game(keyword);
        newGame.addPlayer(creator);

        //Temporarily add game to the arraylist of games before Parse is included in code
        games.add(newGame);
    }

    /**
     * Returns games arraylist for testing purposes before Parse is added
     * @return games Arraylist of all games
     */
    public ArrayList<Game> getGames () {
        return this.games;
    }

   /**
    * Adds a player to an existing game.
    * @param keyword, the keyword that is associated with
    * an existing game
    */
   public void joinGame(String keyword){
       Game game = new Game("temp");
       //Query Parse to retrieve GameObject using keyword

       //Temporary method that finds the game in Arraylist of games (replace with Parse query)
       for(Game oneGame: games) {
           if (oneGame.getKeyword().equals(keyword)) {
               game = oneGame;
               break;
           }
       }

       //Create a player object for new game joiner
       //Get player's username from Parse
       String username = "temp"; // get rid of this - for compilation purposes only
       Player newPlayer = new Player (username);
       game.addPlayer(newPlayer);
   }

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