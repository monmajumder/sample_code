package com.resistance.theresistance.logic;

//import com.parse.GetCallback;
//import com.parse.Parse;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
//import java.text.ParseException;


/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions such as starting/playing a Game
 * @author Group 11
 *
 */
public class GameController {

    //temp games storage
    ArrayList<Game> games = new ArrayList<Game>();

    /** Constructor */
    public GameController(){
    }

   /**
    * Creates a new game and initializes it
    */
   public void createGame (String user, String key){
       //NEED PARSE STUFF TO GET ACCESS TO KEYWORD and player username - halp me candace

       String username, keyword;

       username = user;
       keyword = key;

       Player creator = new Player (username);
       Host host = new Host(creator);

       Game newGame = new Game(keyword);
       newGame.addPlayer(creator);

       //temp add game to the arraylist of games to store temporarily before parse is included in code
       games.add(newGame);
   }

    /**
     * Returns games arraylist for testing purposes
     * @return games
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
       //ACCESS GAME WITH KEYWORD via PARSE

       Game game;

       //this.game = parse.getgamewith(keyword);
       game = new Game ("temp"); //get rid of this line

       //Create a player object for new game joiner
       //Get player's username from parse
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