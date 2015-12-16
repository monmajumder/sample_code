package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Round class is in charge of storing each Round of a mission. A new round is started every time there is a new mission leader.
 */
@ParseClassName("RoundObject")
public class Round extends ParseObject {

	private String missionLeader;
	private ArrayList<String> missionaries;//The current proposed Mission team of Missionaries
    private ArrayList<String> assentors; //Players who voted yes
    private ArrayList<String> dissentors; //Players who voted no
    private boolean missionariesAccepted;

    /**
     * Creates Round object. A Round contains all of the player voting on whether to approve the
     * missionaries. If the team is not approved, a new Round object is created, but the Mission
     * remains the same.
     */
    public Round(){
        super();
    }

    //-----------------------------------------------
    // Getter and Setter Methods
    //-----------------------------------------------

    /**
     * Gets the name of the leader of the round.
     * @return Name of the player who is the leader
     */
    public String getLeader() {
        return getString("Leader");
    }

    /**
     * Sets the new leader for the mission
     * @param newLeader the name of the new leader
     */
    public void setLeader(String newLeader){
        put("Leader", newLeader);
    }

    /**
     * Sets new list of chosen missionaries
     * @param missionaries the new chosen missionaries to be set
     */
    public void setMissionaries(ArrayList<String> missionaries){
        put("Missionaries", missionaries);
    }

    /**
     * Gets the list of missionaries for a round
     * @return List of missionaries for a round
     */
    public List<String> getMissionaries() {
        return getList("Missionaries");
    }

    /**
     * Gets the players' names who voted yes
     * @return List of names
     */
    public List<String> getAssentors() {
        return getList("Assentors");
    }

    /**
     * Saves the players' names who voted yes
     * @param yesVotes player's names who voted yes
     */
    public void setAssentors(ArrayList<String> yesVotes){
       put("Assentors", yesVotes);
    }

    /**
     * Gets the players' names who voted no
     * @return List of names
     */
    public List<String> getDissentors() {
        return getList("Dissentors");
    }

    /**
     * Saves the players' names who voted no
     * @param noVotes player's names who voted no
     */
    public void setDissentors(ArrayList<String> noVotes){
        put("Dissentors", noVotes);
    }

    /**
     * Gets the missionaries accepted boolean
     * @return True if accepted, false otherwise.
     */
    public boolean getMissionariesAccepted() {
        return getBoolean("MissionariesAccepted");
    }

    /**
     * Sets the missionaries accepted boolean
     * @param accepted True if accepted, false otherwise.
     */
    public void setMissionariesAccepted(boolean accepted) {
        put("MissionariesAccepted", accepted);
    }

}
