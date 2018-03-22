package sg.edu.iss.trailblazelearnft04.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Surbhi Tayal on 22/3/2018.
 */
public class StationTest {
    Station station;

    @Before
    public void setUp() throws Exception {
        HashMap<String,Double> gps = new HashMap<>();
        gps.put("1.2922479999999998", 103.77661099999999);
        station = new Station(0, "Station1", gps, "ISS Singapore", "Click a photo","key123");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testStationName()
    {
        HashMap<String,Double> gps = new HashMap<>();
        gps.put("1.2922479999999998", 103.77661099999999);
       Station testStation = new Station(0,"Station1", gps, "ISS Singapore", "Click a photo","key123");
        assertEquals(station.getStationName(),testStation.getStationName() );

    }

    @Test
    public void testAddress()
    {
        HashMap<String,Double> gps = new HashMap<>();
        gps.put("1.2922479999999998", 103.77661099999999);
        Station testStation = new Station(0,"Station1", gps, "ISS Singapore", "Click a photo","key123");
        assertEquals(station.getAddress(),testStation.getAddress());

    }

    @Test
    public void testInstruction() {
        HashMap<String,Double> gps = new HashMap<>();
        gps.put("1.2922479999999998", 103.77661099999999);
        Station testStation = new Station(0,"Station1", gps, "ISS Singapore", "Click a photo","key123");
        assertEquals(station.getInstructions(),testStation.getInstructions());
    }

    @Test
    public void testStationKey() {
        HashMap<String,Double> gps = new HashMap<>();
        gps.put("1.2922479999999998", 103.77661099999999);
        Station testStation = new Station(0,"Station1", gps, "ISS Singapore", "Click a photo","key123");
        assertEquals(station.getStationKey(),testStation.getStationKey());
    }

}