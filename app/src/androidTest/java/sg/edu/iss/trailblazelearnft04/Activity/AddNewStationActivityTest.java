package sg.edu.iss.trailblazelearnft04.Activity;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import sg.edu.iss.trailblazelearnft04.R;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.firebase.database.DatabaseReference.goOffline;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Surbhi Tayal on 22/3/2018.
 */
public class AddNewStationActivityTest {
    @Rule
    public ActivityTestRule<AddNewStationActivity> activityTestRule = new ActivityTestRule<AddNewStationActivity>(AddNewStationActivity.class);
    private AddNewStationActivity testAddNewStation = null;


    @Before
    public void setUp() throws Exception {
        goOffline();
        testAddNewStation = activityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void validRuleTrue(){

        Intent intent = new Intent();
        intent.putExtra("flag",0);
        testAddNewStation.setIntent(intent);
        Espresso.onView(withId(R.id.et_station_name)).perform(typeText("Station1"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.et_station_instruction)).perform(typeText("Click a photo"));
        Espresso.closeSoftKeyboard();
        //Espresso.onView(withId(R.id.tv_station_address)).perform(typeText("ISS NUS Singapore"));
       // Espresso.closeSoftKeyboard();
       // Espresso.onView(withId(R.id.btn_getLocation)).perform(click());
        assertNotNull(testAddNewStation);
    }


}