package com.resistance.theresistance.logic;

//Will import these when Parse is used
//import com.parse.GetCallback;
//import com.parse.Parse;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;
//import java.text.ParseException;
import java.util.ArrayList;

/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions including starting and playing a Game.
 * @author Group 11
 *
 */
public class GameController {

    //Temporarily stores the games, will eventually be replaced by Parse
    ArrayList<Game> games = new ArrayList<Game>();

    /**
     * Constructor for GameController.
     */
    public GameController(){
    }

    /**
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