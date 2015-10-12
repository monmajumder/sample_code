package com.oose2015.group11.resistance;

import java.util.ArrayList;

/**
 * GameController class is in charge of controlling the Game.
 * Keeps track of all the games that are active, and deals with
 * Game related functions such as
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
Creates a new Game, initializes it, adds it to list
of games in Lobby
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




}