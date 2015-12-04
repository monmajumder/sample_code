package logic;

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseException;
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

    public GameNameHandler data(){
        return new GameNameHandler(getActivity());
    }

    /**
     * Creates GameNameHandler
     */
    @DataPoint
    public static GameNameHandler dataP(){
        GameNameHandlerTest g = new GameNameHandlerTest();
        return g.data();
    }


/**
 * Verifying that the createGame works
 *
 */
    @Theory
    public static void createGameHandlerTest(GameNameHandler gnh){
        //GameNameActivity activity = getActivity();
        //GameNameHandler gnh = new GameNameHandler(activity);
        String gameName = "Mindy's Game";
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", gameName);
        boolean exception;
        try {
            ParseObject object = query.getFirst();
            exception = false;
        }catch (ParseException e) {
            exception = true;
        }
        assertFalse(exception);
        assertEquals(1, 2);
      /*  gnh.createGameHandler(getActivity(), gameName, "Mindy");
        try {
            ParseObject object = query.getFirst();
            exception = false;
        }catch (ParseException e) {
            exception = true;
        }
        assertFalse(exception);*/
     }


}
