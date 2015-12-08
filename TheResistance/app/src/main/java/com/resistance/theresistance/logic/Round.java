package com.resistance.theresistance.logic;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Round class is in charge of storing each Round of
 * Mission. Purpose is to have an object that contains
 * all the information for each Mission round, that can be
 * stored in Mission more easily.
 * @author Group 11
 *
 */
@ParseClassName("RoundObject")
public class Round extends ParseObject {

	private String missionLeader;
	private ArrayList<String> missionaries;//The current proposed Mission team of Missionaries
	private int numberVoted; //Total number of players who have voted so far
    private ArrayList<String> yes; //Players who voted yes
    private ArrayList<String> no; //Players who voted no
    private boolean passed;

    /**
     * Creates Round object. A Round contains all of the player voting on whether to approve the
     * missionaries. If the team is not approved, a new Round object is created, but the Mission
     * remains the same.
     */
    public Round(){
        super();
    }

    public void addYesVote(String playerName) {
        yes = new ArrayList<>(getYes());
        yes.add(playerName);
        setYes(yes);
    }

    public void addNoVote(String playerName) {
        no = new ArrayList<>(getNo());
        no.add(playerName);
        setNo(no);
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
    public List<String> getYes() {
        return getList("Yes");
    }

    /**
     * Saves the players' names who voted yes
     * @param yesVotes player's names who voted yes
     */
    public void setYes(ArrayList<String> yesVotes){
       put("Yes", yesVotes);
    }

    /**
     * Gets the players' names who voted no
     * @return List of names
     */
    public List<String> getNo() {
        return getList("No");
    }

    /**
     * Saves the players' names who voted no
     * @param noVotes player's names who voted no
     */
    public void setNo(ArrayList<String> noVotes){
        put("No", noVotes);
    }

}
