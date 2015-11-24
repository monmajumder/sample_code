package com.resistance.theresistance.logic;

import android.util.Log;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;

/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions including starting and playing a Game.
 * @author Group 11
 *
 */
public class GameController {

    private String thisPlayerName;
    private String thisGameName;

    //DON'T NEED THIS!!!! DELETE.
    //Temporarily stores the games, will eventually be replaced by Parse
    ArrayList<Game> games = new ArrayList<Game>();

    /**
     * Constructor for GameController.
     */
    public GameController(){
    }

    /**
    public void checkStartedGame(String gameName) {
        //EVERY FEW SECONDS, QUERY FOR STATE OF GAME. HAS THE GAME STARTED?
        //ABSTRACT THIS METHOD TO DIFFERENT CLASS?
        //NEED TO ADD TIME INTERVAL COMPONENT
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo("Name", gameName);
        query.getFirstInBackground(new GetCallback<Game>() {
            @Override
            public void done(Game game, ParseException e) {
                if (e == null) {
                    Log.d("game", "The retrieval succeeded");
                    if (game.getGameState().equals("MISSION_LEADER_CHOOSING")) {
                        if (game.getLeader().getUsername().equals(thisPlayerName)) {
                            chooseMissionaries();
                        } else {
                            waitForMissionLeader();
                        }
                    } else if (game.getGameState().equals("WAITING_FOR_PLAYERS")) {
                        ArrayList<String> playersInGame = game.getPlayerNames();
                        //DO SOMETHING HERE TO CHANGE THE VIEW ON THE ACTIVITY
                    }
                } else {
                    Log.d("game", "The retrieval failed");
                }
            }
        });
    }
    **/

    public void getMyRole(String playerName) {
        ParseQuery<Player> query = ParseQuery.getQuery(Player.class);
        query.whereEqualTo("Name", playerName);
        query.getFirstInBackground(new GetCallback<Player>() {
            @Override
            public void done(Player player, ParseException e) {
                if (e == null) {
                    Log.d("game", "The retrieval succeeded");
                    Player.PlayerType type = player.getPlayerType();
                } else {
                    Log.d("game", "The retrieval failed");
                }
            }
        });
    }

    public void chooseMissionaries() {

    }

    public void waitForMissionLeader() {

    }



    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    public void setThisPlayerName(String name) {
        this.thisPlayerName = name;
    }

    public String getThisPlayerName() {
        return this.thisPlayerName;
    }

    public void setThisGameName(String name) {
        this.thisGameName = name;
    }

    public String getThisGameName() {
        return this.thisGameName;
    }







    /**NOT MINE!!!! **/
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