package com.resistance.theresistance.logic;

/**
 * Host class in charge of the Game's players before the
 * game has actually started. Can remove unwanted Players
 * from the game before the game hast started. 
 * @author Group 11
 *
 */
public class Host{

	Player host;

    /**
    * Creates Host object
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
}