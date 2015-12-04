package logic;

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseObject;
import com.parse.ParseQuery;
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

   /* public static GameNameActivity testActivityExists(){
        GameNameActivity activity = getActivity();
        assertNotNull(activity);
        return activity;
    }

    static GameNameActivity act = testActivityExists();*/

    /**
     * Fixture initialization (common initialization for all tests)
     */
 /*   private interface Fixture {
        GameNameHandler init();
    }
    /**
     * Creates GameNameHandler
     */
   /* @DataPoint
    public static final Fixture gnhObject =
            new Fixture() {
                public GameNameHandler init() {
                    return new GameNameHandler();
                }*/
            //};

/**
 * Verifying that the Game constuctor works
 *
 */
    @Theory
    public void createGameHandlerTest(){
        GameNameActivity activity = getActivity();
        GameNameHandler gnh = new GameNameHandler(activity);
        String gameName = "Mindy's Game";
        gnh.createGameHandler(activity, gameName, "Mindy");
       // assertEquals(m, "success");
       // ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
       // query.whereEqualTo("Name", gameName);
        //int count = query.count();

    }

    public void joinGameHandlerTest(){
        GameNameActivity activity = getActivity();
        GameNameHandler gnh = new GameNameHandler(activity);
        String gameName = "Mindy's Game";
        gnh.createGameHandler(activity, gameName, "Mindy");
        gnh.joinGameHandler(activity, gameName, "Mindy");
    }



    /**
     * Verifying that the Player constuctor works
     * @param fix Fixture to test
     */
   // @Theory
   // public void testPlayer(Fixture fix) {
   // }

}
