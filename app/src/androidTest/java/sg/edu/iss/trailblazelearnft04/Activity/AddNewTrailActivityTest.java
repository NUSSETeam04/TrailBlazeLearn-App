package sg.edu.iss.trailblazelearnft04.Activity;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.database.DatabaseReference;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import sg.edu.iss.trailblazelearnft04.R;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.firebase.database.DatabaseReference.goOffline;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Surbhi Tayal on 22/3/2018.
 */
public class AddNewTrailActivityTest {
    private DatabaseReference mockedDatabaseReference;

    @Rule
    public ActivityTestRule<AddNewTrailActivity> activityTestRule = new ActivityTestRule<AddNewTrailActivity>(AddNewTrailActivity.class);
    private AddNewTrailActivity testAddNewTrail = null;


    @Before
    public void setUp() throws Exception {
        goOffline();
        testAddNewTrail = activityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void validRuleTrue(){

        Intent intent = new Intent();
        intent.putExtra("flag",0);
        testAddNewTrail.setIntent(intent);
        String trailName = "Ganges123 Trails";
        Espresso.onView(withId(R.id.et_trail_name)).perform(typeText(trailName));
        Espresso.closeSoftKeyboard();
        assertNotNull(testAddNewTrail);
        assertTrue(testAddNewTrail.isValid());
        testAddNewTrail.finish();


    }


}