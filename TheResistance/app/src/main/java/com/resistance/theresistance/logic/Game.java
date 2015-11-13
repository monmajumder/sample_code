package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Game class where players will be able to join a game
 * by entering the "keyword" associated with game object.
 * GameController is in charge of adding players to Game.
 * @author Group 11
 *
 */

@ParseClassName("GameObject")
public class Game extends ParseObject {

    private String keyword;
    private int numPlayers;
    private Host gameHost;

    //RELATION?
    private Leader leader;
    private ArrayList<Player> players;
    private ArrayList<Mission> missions;

    /**
     * Defines the states that a game can be in
     */
    public enum State {
        WAITING_FOR_PLAYERS, START, MISSION_LEADER_CHOOSING, MISSIONARIES_VOTING, RESISTANCE_WINS, SPIES_WIN
    }
    State gameState;

    /**
     * Constructor
     */
    public Game() {
        setNumPlayers(1);
        setGameState(State.WAITING_FOR_PLAYERS.toString());
    }





    public ArrayList<String> getPlayerNames() {
        ArrayList<String> allNames = new ArrayList<String>();
        //for each player in the game, extract the game name and add to array
        return allNames;
    }


    /**
     * DON'T REALLY NEED THIS I THINK. DELETE LATER?
     * Creates Game object, with user-inputted keyword
     * @param keyword, the unique identifier for a game
     */
    public Game(String keyword) {
        this.keyword = keyword;
        this.numPlayers = 1;
        this.players = new ArrayList<Player>();
        this.gameState = State.WAITING_FOR_PLAYERS;
    }

    /**
     * Adds a Player to the game
     * @param player, the Player to add to the game
     */
    public void addPlayer(Player player){
        players.add(player);
        numPlayers++;
    }

    /**
    * Assigns all players the role of Resistor or Spy
    */
    public void setPlayerType(){

        int res, spy;
        //number of resistors and spies for the game
        // setting resistance
        // setting spy
    }

    /**
     * Returns number of missionaries that need to be chosen in the current mission
     * @return numMissionaries
     */
    public int getNumMissionaries() {
        int temp = 1;
        return temp;
    }

   /**
    * Gets all of the mission's vote history
    */
   public void getHistory(){
   }

    /**
     * Checks if the Game is ready to start
     * @return true if game is ready, false otherwise
     * @throws IllegalGameException if number of players is wrong
     */
   public boolean start() throws IllegalGameException {
       if (numPlayers < 5 || numPlayers > 10) {
           throw new IllegalGameException("The number of players is insufficient for gameplay.");
       }
       return gameState == State.START;
   }

   /**
    * Restarts the game.
    */
   public void restart(){
      gameState = State.WAITING_FOR_PLAYERS;
   }

   /**
    * Changes the Game's host
    */
   public void changeHost(){
       gameHost.setHost(players.get(0)); //If host leaves, the longest tenured player becomes host.
   }

    //-----------------------------------------------
    // Helper Methods
    //-----------------------------------------------

    /**
     * Illegal Game Exception - throws an exception when the preconditions
     */
    public static class IllegalGameException extends Exception {
        public IllegalGameException(String message) {
            super (message);
        }
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    public String getKeyword() {return this.keyword;}

    public void setKeyword(String keyword) {
        put("Name", keyword);
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public void setNumPlayers(int num) {
        put("NumPlayers", num);
    }

    public State getGameState() {
        return this.gameState;
    }

    public void setGameState(String state) {
        put("State", state);
    }

    public Player getLeader() {
        return this.leader.getLeader();
    }

    public void setLeader() {
        put("Leader",leader.getLeader().getUsername());
    }

    /**Think about these**/
    public Host getGameHost(){return this.gameHost;}

    public ArrayList<Player> getPlayers() {return this.players;}
}