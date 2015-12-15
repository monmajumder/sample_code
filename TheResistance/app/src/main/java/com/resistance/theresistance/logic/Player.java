package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Player class is responsible for a single player. Keeps track of username and Player type - resistor or spy.
 */

@ParseClassName("PlayerObject")
public class Player extends ParseObject {

    private String username;
    private PlayerType playerType;

    /**
     * Describes the player type, spy or resistor
     */
    public enum PlayerType {
        SPY, RESISTOR
    }

    /**
     * Constructor
     */
    public Player() {
        super();
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    /**
     * Gets the user name
     * @return the username of the player
     */
    public String getUsername() {
        return getString("Name");
    }

    /**
     * Sets the user name
     * @param playerName username of the player
     */
    public void setUsername(String playerName) {
        put("Name", playerName);
    }

    /**
     * Gets the player type.
     * @return the type of the player
     */
    public PlayerType getPlayerType() {
        return PlayerType.valueOf(getString("PlayerType"));
    }

    /**
     * Sets the player type.
     * @param type Either resistor or spy
     */
    public void setPlayerType(PlayerType type) {
        put("PlayerType", type.toString());
    }
}
