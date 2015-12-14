package logic;

import com.resistance.theresistance.logic.Game;
import com.resistance.theresistance.logic.Player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests Game.
 * */
public class GameTest {


    /**
     * Verifying that the addPlayers method works.
     * @param g game to test
     */
    @Test
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
    @Test
    public void testSetHost(Game g){
        //Game g = fix.init();
        g.setHost("Mindy");
        assertEquals(g.getHost(), "Mindy");
    }

}
