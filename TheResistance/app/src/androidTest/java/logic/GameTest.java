package logic;

import com.resistance.theresistance.logic.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;

/**
 * Tests Game.
 * */
@RunWith(Theories.class)
public class GameTest {

   /**
    * Fixture initialization (common initialization for all tests)
    */
   private interface Fixture {
      Game init();
   }

   /**
   * Creates Game
   */
   @DataPoint
   public static final Fixture playerObject = 
      new Fixture() {
         public Game init() {
            return new Game("abc");
         }
      };

   /**
    * Verifying that the Game constuctor works
    * @param fix Fixture to test
    */
   @Theory
   public void testGame(Fixture fix) {
      Game g = fix.init();
      assertEquals(g.getGameState().toString(), "WAITING_FOR_PLAYERS");
      //Games start with one player
      assertEquals(g.getNumPlayers(), 1);
   }

   /**
    * Verifying that the addPlayers method works.
    * @param fix Fixture to test
    */
   @Theory
   public void testAddPlayers(Fixture fix) {
      Game g = fix.init();
      //Games start with one player
      assertEquals(g.getNumPlayers(), 1);
      //Adding a player
      Player p = new Player("Mindy");
      g.addPlayer(p);
      assertEquals(g.getNumPlayers(), 2);
      int mindy = g.getPlayers().indexOf(p);
      assertEquals(g.getPlayers().get(mindy), p);
   }

   /**
    * Verifying an exception is thrown when trying to add
    * more than 10 players to a game.
    * @param fix Fixture to test
    */
   @Theory @Test (expected = IndexOutOfBoundsException.class)
   public void testAddPlayersMax(Fixture fix) {
      Game g = fix.init();
      //Adding 9 more players
      for(int i = 0; i < 9; i++){
         Player p = new Player("Player" + i);
         g.addPlayer(p);
      }
      assertEquals(g.getNumPlayers(), 10);
      Player n = new Player("Player11");
      g.addPlayer(n);
   }

    /**
     * Checks the starting game method.
     * @param fix Fixture to test
     */
    @Theory //@Test (expected = Game.IllegalGameException.class)
    public void testStart(Fixture fix) {
        Game g = fix.init();
        //g.start();
    }

    /**
     * Testing Restart and setGameState
     * @param fix Fixture to test
     */
    @Theory
    public void testRestartSetState(Fixture fix){
        Game g = fix.init();
        assertEquals(g.getGameState().toString(), "WAITING_FOR_PLAYERS");
        g.setGameState("START");
        //assertEquals(g.getGameState().toString(), "START");
        //g.restart();
        //assertEquals(g.getGameState().toString(), "WAITING_FOR_PLAYERS");
    }

    /**
     * Testing setNumPlayers
     * @param fix Fixture to test
     */
    @Theory
    public void testSetNumPlayers(Fixture fix){
        Game g = fix.init();
        //assertEquals(g.getGameState().toString(), "START");
    }

    /**
     * Testing setKeyword
     * @param fix Fixture to test
     */
    @Theory
    public void testSetKeyword(Fixture fix){
        Game g = fix.init();
        //assertEquals(g.getGameState().toString(), "START");
    }
}
