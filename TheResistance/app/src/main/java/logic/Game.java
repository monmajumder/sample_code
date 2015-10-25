package logic;

import java.util.ArrayList;

/**
 * Game class where players will be able to join a game
 * by entering the "keyword" associated with game object.
 * GameController is in charge of adding players to Game.
 * @author Group 11
 *
 */
public class Game{

   String keyword;
   int numPlayers;
   Host gameHost;
   //assume arraylist is storing players in order of them joining the game
   //so oldest player will be the player in slot 0
   ArrayList<Player> players;
   public enum State{
      WAITING, START, PLAYING //etc
   }
   State gameState;
   //enum gameState; Will add game states later

   /**
    Creates Game object, with user-created keyword
    and a maximum numPlayers
    @param keyword, the keyword players will use to join the game
    @param numPlayers the maximum number of players allowed
    in the game
    */
   public Game(String keyword, int numPlayers){

      this.keyword = keyword;
      this.numPlayers = numPlayers;
      players = new ArrayList<Player>();
      gameState = State.WAITING; //some kind of gameState

   }

   /**
    Gets the number of players in the game
    @return numPlayers the number of players
    in the game
    */
   public int getNumPlayers(){
      return numPlayers;
   }

   /**
    Assigns all players the role of Resistor/Spy
    */
   public void chooseRoles(){
      int res, spy;//number of resistors and spies for the game
      //setting resistance
      //setting spy


   }

   /**
    Gets all of the mission's vote history
    */
   public void getHistory(){
   }

   /**
    * Adds a Player to the game
    * @param player, the Player to add to the game
    */
   public void addPlayer(Player player){
      players.add(player);
   }

   /**
    * Checks if the Game is ready to start
    * @return true if game is ready, false otherwise
    */
   public boolean start(){
      return gameState == State.START;
   }

   /**
    * Restarts/resets the game.
    */
   public void restart(){
      //some code of some sort
      gameState = State.WAITING;//which game state?
   }

   /**
    * Changes the Game's host
    */
   public void changeHost(){
      gameHost.host = players.get(0);

   }
}