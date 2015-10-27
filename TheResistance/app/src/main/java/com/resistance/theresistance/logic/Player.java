package com.resistance.theresistance.logic;

/**
 * Player class is responsible for creating the Player object,
 * and letting Players vote
 * @author Group 11
 *
 */
public class Player {

    private String username;
    private PlayerType playerType;

    /**
     * Describes the player type, spy or resistor
     */
    public enum PlayerType {
        SPY, RESISTOR
    }

    /**
     * Creates a Player object
     *
     * @param username, the player's username
     */
    public Player(String username) {
        this.username = username;
        //playerType is based on randomizer, is decided later
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
     * Player votes for missionaries
     * @param vote yes/no (true or false respectively)
     * @return vote the casted vote
     */
    public boolean voteForMissionaries(boolean vote) {
        return vote;
    }


    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    /**
     * Returns the user name
     * @return the username of the player
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the player type.
     * @return the type of the player
     */
    public PlayerType getPlayerType() {
        return this.playerType;
    }
}
