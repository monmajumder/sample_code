package androidTest;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    public void testPreconditions() {
        assertTrue(2 + 2 == 4);
    }
    public void twoPlusTwo() {
        assertTrue(2+2 == 4);
    }
}