package activities;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class MainActivityTest
        extends ActivityInstrumentationTestCase2<MainActivity>  {

    private MainActivity mMainActivity;
    private TextView mMainActivityTestText;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
        //assertNotNull("mMainActivityTestText is null", mMainActivityText);
    }
}
