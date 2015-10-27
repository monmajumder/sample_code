package com.resistance.theresistance.logic;

/**
 * Player class is responsible for creating the Player object,
 * and letting Players vote
 * @author Group 11
 *
 */
public class Player {

    String username;
    PlayerType playerType;
    boolean isHost;

    public enum PlayerType{
        UNDECIDED, SPY, RESISTOR //etc
    }

    /**
     * Creates a Player object
     *
     * @param username,  the player's username.
     * @param playerType - Spy, Resistor, or Undecided
     */
    public Player(String username, PlayerType playerType) {
        this.username = username;
        this.playerType = playerType;
    }

    /**
     * Changes the player's status to host
     */
    public void changeToHost() {
        this.isHost = true;
    }

    /**
     * Player votes yes or no for the missionaries chosen by the leader
     */
    public void voteForMissionaries() {
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    public String getUsername() {return this.username;}

    public PlayerType getPlayerType() {return this.playerType;}

    public boolean isHost() {return isHost;}

}