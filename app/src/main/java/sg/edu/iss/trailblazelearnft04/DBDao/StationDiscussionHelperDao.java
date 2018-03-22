package sg.edu.iss.trailblazelearnft04.DBDao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Adapter.DiscussionListAdapter;
import sg.edu.iss.trailblazelearnft04.Model.Discussion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mia on 19/03/18.
 */

public class StationDiscussionHelperDao {

    // Get discussion list from firebase
    public void getDiscussionList(final String stationId, final DiscussionListAdapter discussionListAdapter,
                                  final RecyclerView rvDiscussionList, final TextView tvEmptyDiscussionList) {
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("discussions");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Discussion> discussionList=new ArrayList<Discussion>();
                for(DataSnapshot child:dataSnapshot.child(stationId).getChildren()){
                    Discussion discussion=child.getValue(Discussion.class);
                    discussionList.add(discussion);

                }

                discussionListAdapter.updateDataSet(discussionList);
                rvDiscussionList.setAdapter(discussionListAdapter);

                tvEmptyDiscussionList.setVisibility(discussionListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Add new discussion to firebase
    public void addNewDiscussion(String stationId, String uid, String topic, String discussionTimestamp) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("discussions");
        String discussionKey = ref.child(stationId).push().getKey();
        Discussion discussion = new Discussion(uid, topic, discussionTimestamp, discussionKey);
        ref.child(stationId).child(discussionKey).setValue(discussion);
    }
}
