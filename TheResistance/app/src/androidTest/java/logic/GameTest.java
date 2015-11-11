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
      assertEquals(g.gameState, State.WAITING_FOR_PLAYERS);
      //Games start with one player
      assertEquals(g.numPlayers, 1);
   }
   
   @Theory
   public void testAddPlayers(Fixture fix) {
      Game g = fix.init();
      //Games start with one player
      assertEquals(g.numPlayers, 1);
      Player p = new Player("Mindy");
      g.addPlayer(p);
      
   }



}
