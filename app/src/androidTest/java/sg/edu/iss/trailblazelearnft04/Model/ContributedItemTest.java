package sg.edu.iss.trailblazelearnft04.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Surbhi Tayal on 22/3/2018.
 */
public class ContributedItemTest {

    ContributedItem contributedItem;
    @Before
    public void setUp() throws Exception {
        contributedItem = new ContributedItem("ABC1", "https://firebasestorage.googleapis.com/v0/b/trailblazelearnft04", "Nice Cat Pic");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUserID()
    {
        ContributedItem testcontributedItem = new ContributedItem("ABC1", "https://firebasestorage.googleapis.com/v0/b/trailblazelearnft04", "Nice Cat Pic");
        assertEquals(contributedItem.getUserId(),testcontributedItem.getUserId() );

    }

    @Test
    public void testUrl() {
        ContributedItem testcontributedItem = new ContributedItem("ABC1", "https://firebasestorage.googleapis.com/v0/b/trailblazelearnft04", "Nice Cat Pic");
        assertEquals(contributedItem.getFileURL(),testcontributedItem.getFileURL() );
    }

    @Test
    public void testDescription() {
        ContributedItem testcontributedItem = new ContributedItem("ABC1", "https://firebasestorage.googleapis.com/v0/b/trailblazelearnft04", "Nice Cat Pic");
        assertEquals(contributedItem.getDescription(),testcontributedItem.getDescription() );
    }


}