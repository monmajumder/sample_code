package logic;

/**
 * Class is responsible for giving Missionaries (Missionaries
 * are Players with additional functionality, but are constantly
 * changing) the ability to vote on Missions. Can change the
 * Missionary to another Player easily.
 * @author Group 11
 *
 */
public class Missionary{

Player player;	

/**
 * Creates Missionary object	
 * @param player the player that is a missionary
 */
public Missionary(Player player){
	this.player = player;
}

/**
 * Missionary votes to pass/fail the mission
 */
public void voteForMission(){
	
}

/**
 * Changes the Missionary to a different player
 * @param player the player to become a Missionary
 */
public void changeMissionary(Player player){
	
}

}