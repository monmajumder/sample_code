package com.resistance.theresistance.logic;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class is responsible for letting the Leader choose Missionaries. Has a Player object
 * Since Leader is a Player with additional functionality, but since the Leader is constantly
 * changing, we can easily change the Player who is the leader.
 * @author Group 11
 *
 */

public class Leader{

    private Player leader;

    /**
     * Creates a Leader object using a player's fields
     * @param leader the player that is the leader
     */
    public Leader(Player leader){
        this.leader = leader;
    }

    /**
     * Chooses the missionaries to go on the mission.
     * @param allPlayers ArrayList of all players in the game
     * @param numMiss the number of missionaries to choose
     * @return ArrayList of the missionaries that will go on the mission
     */
    public ArrayList<Missionary> chooseMissionaries(ArrayList<Player> allPlayers, int numMiss) {
        ArrayList<Missionary> missionariesChosen = new ArrayList<Missionary>();
        //Takes in clicks from front end to decide which missionaries are chosen
        return missionariesChosen;
    }

    /**
     * Changes the mission leader to a new player
     * @param newLeader the new leader
     */
    public void changeLeader(Player newLeader){
      if (leader.equals(newLeader)){
         //throw exception if leader is already here
      }
      leader = newLeader;
   }

    //---------------------------------
    // Getter and setter methods
    //---------------------------------

    /**
     * Gets the leader
     * @return leader the mission leader
     */
    public Player getLeader() {
        return leader;
    }
}