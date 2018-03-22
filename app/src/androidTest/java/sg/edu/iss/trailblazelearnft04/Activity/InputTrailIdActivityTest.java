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
import static org.junit.Assert.assertTrue;

/**
 * Created by Surbhi Tayal on 22/3/2018.
 */
public class InputTrailIdActivityTest {
    @Rule
    public ActivityTestRule<InputTrailIdActivity> activityTestRule = new ActivityTestRule<InputTrailIdActivity>(InputTrailIdActivity.class);
    private InputTrailIdActivity inputTrailIdActivity = null;


    @Before
    public void setUp() throws Exception {
        goOffline();
        inputTrailIdActivity = activityTestRule.getActivity();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void validRuleTrue(){

        Intent intent = new Intent();
        intent.putExtra("flag",0);
        Espresso.onView(withId(R.id.inputID)).perform(typeText("20182103-Trail1"));
        Espresso.closeSoftKeyboard();
        assertNotNull(inputTrailIdActivity);
        assertTrue(inputTrailIdActivity.isValid());
    }


}