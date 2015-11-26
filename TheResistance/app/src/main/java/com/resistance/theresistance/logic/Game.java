package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private int numResistanceWins;
    private int numSpiesWins;
    private String host;
    private ArrayList<Player> players;
    private ArrayList<Mission> missions;

    /**
     * Defines the states that a game can be in
     */
    public enum State {
        WAITING_FOR_PLAYERS, MISSION_LEADER_CHOOSING, VOTE_FOR_MISSIONARIES, MISSIONARIES_VOTING, RESISTANCE_WINS, SPIES_WIN
    }
    private State gameState;

    /**
     * Constructor
     */
    public Game() {
        super();
    }

    /**
     * Adds a Player to a Game's Player array and increments number counter
     * @param player Player object to be added
     */
    public void addPlayer(Player player) {
        int newNumPlayers = getNumPlayers() + 1;
        put("NumPlayers", newNumPlayers);
        List<Player> playerList = getPlayers();
        if (playerList == null) {
            players = new ArrayList<Player>();
        } else {
            players = new ArrayList<Player>(getPlayers());
        }
        players.add(player);
        put("Players",this.players);
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------
    public String getKeyword() {
        return getString("Name");
    }

    public void setKeyword(String keyword) {
        put("Name", keyword);
    }

    public int getNumPlayers() {
        return getInt("NumPlayers");
    }

    public void setNumPlayers(int num) {
        put("NumPlayers", num);
    }

    public State getGameState() {
        return State.valueOf(getString("State"));
    }

    public void setGameState(State state) {
        put("State", state.toString());
    }

    public void setHost(String hostName) {
        put("Host",hostName);
    }

    public String getHost() {
        return getString("Host");
    }

    public List<Player> getPlayers() {
        return getList("Players");
    }

    public List<Mission> getMissions() {
        return getList("Missions");
    }









    /** DON'T NEED THESE. I THINK. **/
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
    public void addPlayer(Player player){
        if (numPlayers < 10){
        players.add(player);
        numPlayers++;
        }
        else{
         throw new IndexOutOfBoundsException("Cannot have more than 10 players.");
         }
    }**/

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
       return gameState == State.MISSION_LEADER_CHOOSING;
   }

   /**
    * Restarts the game.
    */
   public void restart(){
      gameState = State.WAITING_FOR_PLAYERS;
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


    //Fix this
    /**
    public Player getLeader() {
        return this.leader.getLeader();
    }

    public void setLeader() {
        put("Leader",leader.getLeader().getUsername());
    }**/
}