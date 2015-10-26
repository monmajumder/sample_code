package com.resistance.theresistance.logic;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;


/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions such as starting/playing a Game
 * @author Group 11
 *
 */
public class GameController {

    /** Constructor */
    public GameController(){

    }

   /**
   Creates a new Game, initializes it
   */
   public void createGame (){
       //NEED PARSE STUFF TO GET ACCESS TO KEYWORD and player username - halp me candace
       String username = "temp";
       String keyword = "temp";
       Host host = new Host(new Player (username, Game.PlayerType.UNDECIDED));

       Game newGame = new Game (keyword);
   }

   /**
   Adds a player to an existing game.
   @param keyword, the keyword that is associated with
      an existing game
   */
   public void joinGame(String keyword){
       //ACCESS GAME WITH KEYWORD via PARSE

       Game game;

       //this.game = parse.getgamewith(keyword);
       game = new Game ("temp"); //get rid of this line

       //Create a player object for new game joiner
       //Get player's username from parse
       String username = "temp"; // get rid of this - for compilation purposes only
       Player newPlayer = new Player (username, Game.PlayerType.UNDECIDED);
       game.addPlayer(newPlayer);
       game.incrementNumPlayers();
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

   }

}