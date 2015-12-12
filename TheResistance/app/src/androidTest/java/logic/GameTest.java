package logic;

import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.Player;

import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Tests Game.
 * */
@RunWith(Theories.class)
public class GameTest {

    /**
     * Creates Game
     */
    @DataPoint
    public static Game data(){
        return new Game();
    }

    /**
     * Verifying that the Game constuctor works
     * @param g Game to test
     */
    @Theory
    public void testGame(Game g) {
        //Games start with no players
        assertEquals(g.getNumPlayers(), 0);
        //MAYBE ADD AN ASSERTION FOR GAME STATE
    }

    /**
     * Verifying that the addPlayers method works.
     * @param g game to test
     */
    @Theory
    public void testAddPlayers(Game g) {
        //Game g = fix.init();
        //Games start with zero players
        assertEquals(g.getNumPlayers(), 0);
        //Adding a player
        Player p = new Player();
        g.addPlayer(p);
        assertEquals(g.getNumPlayers(), 1);
        int mindy = g.getPlayers().indexOf(p);
        assertEquals(g.getPlayers().get(mindy), p);
    }

    /**
     * Testing setHost and getHost
     * @param g Game to test
     */
    @Theory
    public void testSetHost(Game g){
        //Game g = fix.init();
        g.setHost("Mindy");
        assertEquals(g.getHost(), "Mindy");
    }

}
