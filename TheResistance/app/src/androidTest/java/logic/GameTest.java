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
     * Verifying an exception is thrown when trying to add
     * more than 10 players to a game.
     * @param g Game to test
     */
    @Theory @Test (expected = IndexOutOfBoundsException.class)
    public void testAddPlayersMax(Game g) {
        //Game g = fix.init();
        //Adding 10 players
        for(int i = 0; i < 10; i++){
            Player p = new Player();
            g.addPlayer(p);
        }
        assertEquals(g.getNumPlayers(), 10);
        //Adding 11th player
        Player n = new Player();
        g.addPlayer(n);
    }


    /**
     * Verifying and exception is thrown when trying to get
     * the current mission of a game that has just been
     * initalized and hasn't started.
     * @param g Game to test
     */
    @Theory @Test (expected = NullPointerException.class)
    public void testGetCurrMiss(Game g){
        // Game g = fix.init();
        g.getCurrMission();
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
