package com.oose2015.group11.resistance;

import java.util.ArrayList;

public class Game{

   String keyword;
   int numPlayers;
   ArrayList<Player> players;
//enum gameState;

   /**
   Creates Game object, with user-created keyword
   and a maximum numPlayers
   */
   public Game(String keyword, int numPlayers){
   
      this.keyword = keyword;
      this.numPlayers = numPlayers;
      players = new ArrayList<Player>();
   //gameState = ""; //some kind of gamesState
   
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
   Generates a randomized mission leader
   @param numPlayers the number of players in the game
   @return the number of the player who is mission leader
   */
   public int getRandomMissionLeader(int numPlayers){
      return 0;
   }
   
   /**
   Assigns all players the role of Resistor/Spy
   */
   public void chooseRoles(){
   }
   
   /**
   Displays all of the mission's vote history
   */
   public void displayHistory(){
   }
}