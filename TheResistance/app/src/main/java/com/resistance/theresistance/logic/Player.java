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

    public enum PlayerType {
        SPY, RESISTOR //etc
    }

    /**
     * Creates a Player object
     *
     * @param username, the player's username
     */
    public Player(String username) {
        this.username = username;
        //this.playerType = playerType; //spy or Resistor depending on randomizer
    }

    /**
     * Sets the player's type
     *
     * @param type, "Spy" or "Resistor" for type
     */
    public void setType(PlayerType type) {

        if (type != PlayerType.SPY && type != PlayerType.RESISTOR) {
            //throw some illegal exception here
        }
        this.playerType = type;
    }

    /**
     * Player votes for missionaries - votes yes/no (true/false) for the
     * leader-chosen mission team
     */
    public boolean voteForMissionaries(boolean vote) {
        return vote;
    }

    /**
     * Player votes yes or no for the missionaries chosen by the leader
     */
    public void voteForMissionaries() {
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    public String getUsername() {
        return this.username;
    }

    public PlayerType getPlayerType() {
        return this.playerType;
    }
}
