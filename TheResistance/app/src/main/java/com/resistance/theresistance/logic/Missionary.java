package com.resistance.theresistance.logic;

/**
 * Class is responsible for giving Missionaries the ability to vote on Missions.
 * Missionaries are Players with additional functionality, but are constantly
 * changing. Can change the Missionary to another Player easily.
 * @author Group 11
 *
 */

public class Missionary {

    private Player player;

    /**
     * Creates Missionary object
     * @param player the player that is a missionary
     */
    public Missionary (Player player){
        this.player = player;
    }

    /**
     * Missionary votes to pass/fail (true/false) the mission
     * @param vote pass/fail (true/false respectively)
     * @return the vote
     */
    public boolean voteForMission(boolean vote){
        return vote;
    }

    /**
     * Changes the Missionary to a different player
     * @param player the player to become a Missionary
     */
    public void changeMissionary(Player player){
        this.player = player;
    }

    /**
     * Returns the player who is the missionary.
     * @return player
     */
    public Player getMissionary() {
        return this.player;
    }
}

