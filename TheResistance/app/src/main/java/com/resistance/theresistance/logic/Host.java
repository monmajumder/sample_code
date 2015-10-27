package com.resistance.theresistance.logic;

/**
 * Host class in charge of the Game's players before the game has actually started.
 * Can remove unwanted Players from the game before the game hast started.
 * @author Group 11
 *
 */

public class Host{

	private Player host;

    /**
     * Constructor for the Host object
     * @param host the player that is the host
     */
    public Host(Player host){
        this.host = host;
    }

    /**
     * Removes player from the game
     * @param player the player to be removed
     * @return the player to be evicted
     */
    public Player evict(Player player){
        return player;
    }

    //---------------------------------
    // Getter and setter methods
    //---------------------------------

    /**
     * Returns the host
     * @return the Player object who is the host
     */
    public Player getHost() {
        return this.host;
    }

    /**
     * Changes the host
     * @param player the new host
     */
    public void setHost(Player player) {
        this.host = player;
    }
}