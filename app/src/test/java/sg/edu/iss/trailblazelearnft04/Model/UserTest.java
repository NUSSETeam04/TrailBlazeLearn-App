package sg.edu.iss.trailblazelearnft04.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Neelam on 3/21/2018.
 */
public class UserTest {

    User user;
    @Before
    public void setUp() throws Exception {
        user = new User("asdf123","asdf123@gmail.com");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUsername()
    {
        User testUser = new User("asdf123","asdf123@gmail.com");
        assertEquals(testUser.getUserName(),user.getUserName());

    }

    @Test
    public void testEmail() {
        User testUser = new User("asdf123","asdf123@gmail.com");
        assertEquals(testUser.getEmail(),user.getEmail());
    }


}