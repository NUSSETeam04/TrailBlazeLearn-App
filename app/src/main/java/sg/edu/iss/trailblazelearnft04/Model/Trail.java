package sg.edu.iss.trailblazelearnft04.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzuxiu on 09/03/18.
 */

public class Trail {

    private String trailName;
    private String trailDate;
    private String timestamp;
    private String trailId;
    private String key;

    public String getTrailName() {
        return trailName;
    }

    public void setTrailName(String trailName) {
        this.trailName = trailName;
    }

    public String getTrailDate() {
        return trailDate;
    }

    public void setTrailDate(String trailDate) {
        this.trailDate = trailDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Trail(){


    }

    public Trail(String trailName, String trailDate, String timestamp,String trailId,String key){
        this.timestamp=timestamp;
        this.trailDate=trailDate;
        this.trailName=trailName;
        this.trailId=trailId;
        this.key=key;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("trailName", trailName);
        result.put("trailDate", trailDate);
        result.put("timestamp", timestamp);
        result.put("trailId",trailId);
        result.put("key",key);

        return result;
    }

}
