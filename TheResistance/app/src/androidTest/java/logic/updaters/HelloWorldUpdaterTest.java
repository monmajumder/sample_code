package logic.updaters;

import com.resistance.theresistance.logic.updaters.HelloWorldUpdater;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Testing HelloWorldUpdater.
 */
@RunWith(Theories.class)
public class HelloWorldUpdaterTest {

   /** Fixture initialization (common initialization
    *  for all tests). **/
    private interface Fixture {
        HelloWorldUpdater init();
    }

    /**
     * Creates HelloWorldUpdater
     */
    @DataPoint
    public static final Fixture helloWorldUpdater = new Fixture() {
        public HelloWorldUpdater init() {
            return new HelloWorldUpdater();
        }
    };

    /**
     * Tests that starting a starter timer doesn't break it.
     * @param fix Fixture to test
     */
    @Theory public void testStartTimer(Fixture fix){
        HelloWorldUpdater helloWorldUpdater = fix.init();
        helloWorldUpdater.start();
        helloWorldUpdater.start();
    }

    /**
     * Tests that stopping a stopped timer doesn't break it.
     * @param fix Fixture to test
     */
    @Theory public void testStopTimer(Fixture fix){
        HelloWorldUpdater helloWorldUpdater = fix.init();
        helloWorldUpdater.start();
        helloWorldUpdater.stop();
        helloWorldUpdater.stop();
    }

    /**
     * Tests that stopping a unstarted doesn't break it.
     * @param fix Fixture to test
     */
    @Theory public void testStopUnstartedTimer(Fixture fix){
        HelloWorldUpdater helloWorldUpdater = fix.init();
        helloWorldUpdater.stop();
    }
}
