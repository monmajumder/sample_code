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
 * Tests PlayerNameHandler.
 * */
@RunWith(Theories.class)
public class PlayerNameHandlerTest {

    /**
     * Fixture initialization (common initialization for all tests)
     */
    private interface Fixture {
        PlayerNameHandler init();
    }

    /**
     * Creates Player.
     */
    @DataPoint
    public static final Fixture playerNameHandlerObject = new Fixture() {
        public PlayerNameHandler init() {
            return null;
        }
    };

    /**
     * Verifying that the Player constuctor works
     * @param fix Fixture to test
     */
    @Theory
    public void testPlayer(Fixture fix) {
    }

}
