package sg.edu.iss.trailblazelearnft04.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Neelam on 3/21/2018.
 */
public class TrailTest {


    Trail tesTrail=new Trail("DemoTrail","20180321","20180321 19:59:10","20180321-DemoTrail","mnbv0987");

    @Test
    public void testDate()
    {
        assertEquals("20180321",tesTrail.getTrailDate());
    }

    @Test
    public void testTrailID()
    {
        assertEquals("20180321-DemoTrail",tesTrail.getTrailId());
    }
    public void testTrailName()
    {
        assertEquals("DemoTrail",tesTrail.getTrailName());
    }
}