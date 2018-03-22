package sg.edu.iss.trailblazelearnft04.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Surbhi Tayal on 22/3/2018.
 */
public class DiscussionTest {

    Discussion discussion;
    @Before
    public void setUp() throws Exception {
        discussion = new Discussion("ABC1","Android","20180321 13:47:13","discussion1");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUserID()
    {
        Discussion testDiscussion = new Discussion("ABC1","Android","20180321 13:47:13","discussion1");
        assertEquals(discussion.userId,testDiscussion.userId );

    }

    @Test
    public void testTopic() {
        Discussion testDiscussion = new Discussion("ABC1","Android","20180321 13:47:13","discussion1");
        assertEquals(discussion.topic,testDiscussion.topic );
    }

    @Test
    public void testTimestamp() {
        Discussion testDiscussion = new Discussion("ABC1","Android","20180321 13:47:13","discussion1");
        assertEquals(discussion.timestamp,testDiscussion.timestamp );
    }

    @Test
    public void testDiscussionID() {
        Discussion testDiscussion = new Discussion("ABC1","Android","20180321 13:47:13","discussion1");
        assertEquals(discussion.discussionId,testDiscussion.discussionId );
    }
}