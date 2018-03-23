package sg.edu.iss.trailblazelearnft04.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Surbhi Tayal on 22/3/2018.
 */
public class PostTest {
    Post post;

    @Before
    public void setUp() throws Exception {
        post = new Post("ABC1", "This is a new Post", "20181121");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUserID()
    {
        Post testPost = new Post("ABC1", "This is a new Post", "20181121");
        assertEquals(post.getUserId(),testPost.getUserId() );

    }

    @Test
    public void testPost() {
        Post testPost = new Post("ABC1", "This is a new Post", "20181121");
        assertEquals(post.getPost(),testPost.getPost() );
    }

    @Test
    public void testDescription() {
        Post testPost = new Post("ABC1", "This is a new Post", "20181121");
        assertEquals(post.getTimestamp(),testPost.getTimestamp() );
    }


}