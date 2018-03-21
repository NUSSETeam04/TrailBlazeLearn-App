package sg.edu.iss.trailblazelearnft04.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Adapter.DiscussionListAdapter;
import sg.edu.iss.trailblazelearnft04.DBDao.StationDiscussionHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Discussion;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao.getUid;


public class StationDiscussionFragment extends Fragment {
    private RecyclerView rvDiscussionList;
    private DiscussionListAdapter discussionListAdapter;
    private RecyclerView.LayoutManager discussionListManager;
    private EditText etNewDiscussion;
    private DatabaseReference mDatabase;
    private TextView tvEmptyDiscussionList;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private ImageButton btnPost;
    private ArrayList<Discussion> discussionList = new ArrayList<Discussion>();
    private String uid = getUid();
    private StationDiscussionHelperDao stationDiscussionHelperDao;
    ;

    public StationDiscussionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View fragmentView = inflater.inflate(R.layout.fragment_station_discussion, container, false);

        rvDiscussionList = (RecyclerView) fragmentView.findViewById(R.id.discussion_list);
        rvDiscussionList.setHasFixedSize(false);

        discussionListManager = new LinearLayoutManager(getActivity());
        rvDiscussionList.setLayoutManager(discussionListManager);

        Bundle bundle = new Bundle();
        bundle = this.getArguments();
        final String stationId = bundle.getString("stationId");

        discussionListAdapter = new DiscussionListAdapter(discussionList);
        rvDiscussionList.setAdapter(discussionListAdapter);

        tvEmptyDiscussionList = fragmentView.findViewById(R.id.tv_empty_discussion);

        stationDiscussionHelperDao = new StationDiscussionHelperDao();
        stationDiscussionHelperDao.getDiscussionList(stationId, discussionListAdapter, rvDiscussionList, tvEmptyDiscussionList);


        btnPost = fragmentView.findViewById(R.id.btn_post);
        etNewDiscussion = fragmentView.findViewById(R.id.et_discussion);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = etNewDiscussion.getText().toString();
                Date date = new Date(System.currentTimeMillis());
                String discussionTimestamp = formatter.format(date);

                if (topic.isEmpty()){
                    etNewDiscussion.setError("Say something");
                } else {
                    stationDiscussionHelperDao.addNewDiscussion(stationId, uid, topic, discussionTimestamp);
                    etNewDiscussion.setText("");
                }
            }
        });

        return fragmentView;
    }
}
