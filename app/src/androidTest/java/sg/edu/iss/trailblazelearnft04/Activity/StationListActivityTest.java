package sg.edu.iss.trailblazelearnft04.Activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import sg.edu.iss.trailblazelearnft04.R;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.firebase.database.DatabaseReference.goOffline;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Surbhi Tayal on 23/3/2018.
 */
public class StationListActivityTest {
    @Rule
    public ActivityTestRule<StationListActivity> activityTestRule = new ActivityTestRule<StationListActivity>(StationListActivity.class);
    private StationListActivity stationListActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(AddNewStationActivity.class.getName(), null,false);

    @Before
    public void setUp() throws Exception {
        goOffline();
        stationListActivity = activityTestRule.getActivity();
    }

    @Test
    public void testStationListToAddNewStation(){
        assertNotNull(stationListActivity);
        assertEquals(stationListActivity.getTitle(), "Trail Station List");
       assertNotNull(stationListActivity.findViewById(R.id.fab_add_station));
       onView(withId(R.id.fab_add_station)).perform(click());
       Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);
       assertNotNull(secondActivity);

    }

    @After
    public void tearDown() throws Exception {
    }

}