package sg.edu.iss.trailblazelearnft04.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzuxiu on 15/03/18.
 */

public class Discussion {


    private String userId;
    private String topic;
    private String timestamp;
    private String discussionId;

    public Discussion(){

    }
    public Discussion(String userId,String topic,String timestamp,String discussionId){
        this.userId=userId;
        this.timestamp=timestamp;
        this.topic=topic;
        this.discussionId=discussionId;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("userId", userId);
        result.put("userId", userId);
        result.put("topic",topic);
        result.put("timestamp", timestamp);
        result.put("discussionId",discussionId);
        return result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }
}
