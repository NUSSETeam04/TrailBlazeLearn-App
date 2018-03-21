package sg.edu.iss.trailblazelearnft04.Model;

import java.util.HashMap;

/**
 * Created by wangzuxiu on 09/03/18.
 */

public class Station {


    private int sequence;
    private String stationName;
    private String address;
//    private String gps;
    private String instructions;
    private String stationKey;
    private HashMap <String,Double> gps = new HashMap<>();

    public Station() {
    }

    public Station(String stationName, HashMap<String,Double> gps, String address, String instructions,String stationKey) {
        // this.sequence = sequence;
        this.stationName = stationName;
        this.gps.putAll(gps);
        this.address=address;
        this.instructions = instructions;
        this.stationKey = stationKey;
    }

    public int getSequence() {
        return sequence;
    }

    public String getStationName() {
        return stationName;
    }

    public HashMap<String,Double> getGps() {
        return gps;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getStationKey() {
        return stationKey;
    }

    public String getAddress() {
        return address;
    }


}
