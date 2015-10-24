package com.resistance.theresistance.logic;

import java.util.ArrayList;

/**
 * GameController class is in charge of controlling the Game.
 * Deals with Game related functions such as starting/playing a Game
 * @author Group 11
 *
 */
public class GameController{

ArrayList<Game> allGames;

/**
Creates a GameController object of all games
that are currently active (inactive means there are
no players in the Game)
*/
public GameController(){
   allGames = new ArrayList<Game>();
}

/**
Creates a new Game, initializes it
*/
public void createGame(){

}

/**
Adds a player to an existing game.
@param keyword, the keyword that is associated with
   an existing game
*/
public void joinGame(String keyword){

}

/**
 * Plays the game
 * @param game the game to be played
 */
public void playGame(Game game){
	
}

/**
 * Deletes a game
 * @param game the Game to be deleted
 */
public void removeGame(Game game){
	
}




}