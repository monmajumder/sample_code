package logic;

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.resistance.theresistance.activities.MainActivity;
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
public class PlayerNameHandlerTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mMainActivity;

    public PlayerNameHandlerTest(){
        super(MainActivity.class);
    }


/*    public PlayerNameHandler data(){
        return new PlayerNameHandler(getActivity());
    }

    *//**
     * Creates GameNameHandler
     *//*
    @DataPoint
    public static PlayerNameHandler dataP(){
        PlayerNameHandlerTest p = new PlayerNameHandlerTest();
        return p.data();
    }

    *//**
     * Verifying that the createGame works
     *@param pnh the GameNameHandler to be tested
     *//*
    @Theory @Test
    public void createGameHandlerTest(PlayerNameHandler pnh){
        String pName = "Mindy";
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameObject");
        query.whereEqualTo("Name", pName);
        boolean exception;
        try {
            ParseObject object = query.getFirst();
            exception = false;
        }catch (ParseException e) {
            exception = true;
        }
        assertTrue(exception);
        pnh.handleUsername(getActivity(), pName);
        try {
            ParseObject object = query.getFirst();
            exception = false;
        }catch (ParseException e) {
            exception = true;
        }
        assertFalse(exception);
    }*/

}
