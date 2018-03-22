package sg.edu.iss.trailblazelearnft04.Model;

/**
 * Created by wangzuxiu on 16/03/18.
 */

public class Post {

    private String userId;
    private String post;
    private String timestamp;
    public Post(){
    }
    public Post(String userId, String post, String timestamp){
        this.userId=userId;
        this.post=post;
        this.timestamp=timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
