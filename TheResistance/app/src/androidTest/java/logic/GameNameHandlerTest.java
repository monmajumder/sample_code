package logic;

import android.test.ActivityInstrumentationTestCase2;

import com.resistance.theresistance.activities.GameNameActivity;
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
 * Tests GameNameHandler.
 * */
@RunWith(Theories.class)
public class GameNameHandlerTest extends ActivityInstrumentationTestCase2<GameNameActivity>{

    public GameNameHandlerTest(){
        super(GameNameActivity.class);
    }


    /**
     * Fixture initialization (common initialization for all tests)
     */
   // private interface Fixture {
    //    GameNameHandler init();
   // }

    public void testActivityExists(){
        GameNameActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testActivity(){
        GameNameActivity activity = getActivity();
        GameNameHandler gnh = new GameNameHandler(activity);
        gnh.createGameHandler(activity, "Mindy's Game", "Mindy");
    }

    /**
     * Creates GameNameHandler
     */
    /*@DataPoint
    public static final Fixture gameNameHandlerObject = new Fixture() {
           // return new GameNameHandler(activity);
        return null;
    };*/

    /**
     * Verifying that the Player constuctor works
     * @param fix Fixture to test
     */
   // @Theory
   // public void testPlayer(Fixture fix) {
   // }

}
