package com.resistance.theresistance.logic;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game class where players will be able to join a game
 * by entering the "keyword" associated with game object.
 * GameController is in charge of adding players to Game.
 * @author Group 11
 *
 */
public class Game{

   String keyword;
   int numPlayers;
   Host gameHost;
   //assume arraylist is storing players in order of them joining the game
   //so oldest player will be the player in slot 0
   ArrayList<Player> players;
   public enum State{
      WAITING, START, PLAYING //etc
   }
   State gameState;

    public enum PlayerType{
        UNDECIDED, SPY, RESISTOR //etc
    }
   //enum gameState; Will add game states later

   /**
    Creates Game object, with user-created keyword
    and a maximum numPlayers
    @param keyword, the keyword players will use to join the game
    in the game
    */
   public Game (String keyword){ //Game now only takes a keyword - players are counted as they join the game

      this.keyword = keyword;
      this.numPlayers = 1; // this is the host
      players = new ArrayList<Player>();
      gameState = State.WAITING; //some kind of gameState

      //Create a player object for the host right at the beginning of the game - game controller?
   }

   /**
    Gets the number of players in the game
    @return numPlayers the number of players
    in the game
    */
   public int getNumPlayers(){
      return numPlayers;
   }

   /**
    Assigns all players the role of Resistor/Spy
    */
   public void chooseRoles(){
      int res, spy;

      //number of resistors and spies for the game
      //setting resistance
      //setting spy



   }

   /**
    Gets all of the mission's vote history
    */
   public void getHistory(){
   }

   /**
    * Adds a Player to the game
    * @param player, the Player to add to the game
    */
   public void addPlayer(Player player){
      players.add(player);
   }

   /**
    * Checks if the Game is ready to start
    * @return true if game is ready, false otherwise
    */
   public boolean start() throws IllegalGameException {
       if (numPlayers < 5 || numPlayers > 10) {
           throw new IllegalGameException("The number of players is insufficient for gameplay.");
       }
       return gameState == State.START;
   }

   /**
    * Restarts/resets the game.
    */
   public void restart(){
      //some code of some sort
      gameState = State.WAITING;//which game state?
   }

   /**
    * Changes the Game's host
    */
   public void changeHost(){
      gameHost.host = players.get(0);

   }

    public void incrementNumPlayers() { numPlayers++; }

    //Helper Classes and Methods

    public static class IllegalGameException extends Exception {
        public IllegalGameException(String message) {
            super (message);
        }
    }
}