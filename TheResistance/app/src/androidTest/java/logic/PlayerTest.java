package logic;

import com.parse.ParseException;
import com.resistance.theresistance.logic.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Testing Player.
 */
public class PlayerTest {

    /**
     * Tests setting player username.
     */
    @Test
    public void testSetUsername() {
        Player player = new Player();
        String playerName = "Candace";
        player.setUsername(playerName);
        try {
            player.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(playerName, player.getUsername());
    }

    /**
     * Tests setting player type.
     */
    @Test
    public void testSetPlayerType() {
        Player player = new Player();
        Player.PlayerType type = Player.PlayerType.RESISTOR;
        player.setPlayerType(type);
        try {
            player.save();
        } catch (ParseException e) {
            assertTrue(false);
        }
        assertEquals(type, player.getPlayerType());
    }

}