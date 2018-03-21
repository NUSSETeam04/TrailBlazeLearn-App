package sg.edu.iss.trailblazelearnft04.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Adapter.PostListAdapter;
import sg.edu.iss.trailblazelearnft04.DBDao.StationPostHelperDao;
import sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Post;
import sg.edu.iss.trailblazelearnft04.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao.getUid;


public class StationPostActivity extends AppCompatActivity {
    private RecyclerView rvPostList;
    private PostListAdapter postListAdapter;
    private RecyclerView.LayoutManager postListManager;
    private TextView tvDiscussionTopic;
    private TextView tvUserName;
    private TextView tvCreatedDate;
    private EditText etNewPost;
    private ImageButton btnPost;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private UserHelperDao userHelperDao = new UserHelperDao();
    private StationPostHelperDao stationPostHelperDao;
    private String uid = getUid();
    private ArrayList<Post> postList = new ArrayList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");
        this.setTitle(stationName);
        setContentView(R.layout.activity_station_post);

        final String discussionId = intent.getStringExtra("discussionId");
        Log.i("tag01", discussionId);
        String discussionTopic = intent.getStringExtra("discussionTopic");
        String userId = intent.getStringExtra("userId");

        String timestamp = intent.getStringExtra("timestamp");

        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        userHelperDao.setUserNameByUserId(userId, tvUserName);

        tvDiscussionTopic = (TextView) findViewById(R.id.tv_discussion_topic);
        tvDiscussionTopic.setText(discussionTopic);
        tvCreatedDate = (TextView) findViewById(R.id.tv_created_date);
        tvCreatedDate.setText(timestamp);


        rvPostList = (RecyclerView) findViewById(R.id.post_list);
        rvPostList.setHasFixedSize(false);

        postListManager = new LinearLayoutManager(this);
        rvPostList.setLayoutManager(postListManager);

        postListAdapter = new PostListAdapter(postList);
        rvPostList.setAdapter(postListAdapter);

        stationPostHelperDao = new StationPostHelperDao();
        stationPostHelperDao.getPostList(discussionId, postListAdapter, rvPostList);

        btnPost = findViewById(R.id.btn_post);
        etNewPost = findViewById(R.id.et_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post = etNewPost.getText().toString();
                Date date = new Date(System.currentTimeMillis());
                String postTimestamp = formatter.format(date);

                if (post.isEmpty()){
                    etNewPost.setError("Say something");
                } else {
                    stationPostHelperDao.addNewPost(discussionId, uid, post, postTimestamp);
                    etNewPost.setText("");
                }

            }
        });

    }
}
