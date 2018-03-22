package sg.edu.iss.trailblazelearnft04.DBDao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Adapter.ParticipantTrailListAdapter;
import sg.edu.iss.trailblazelearnft04.Model.Trail;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mia on 19/03/18.
 */

public class ParticipantTrailHelperDao {
    private DatabaseReference mDatabase;

    // Get trail list for a participant from firebase
    public void getTrailsForParticipant(final String uid, final RecyclerView rvTrailList,
                                        final ParticipantTrailListAdapter trailListAdapter, final TextView tvEmptyTrailList) {
        mDatabase = FirebaseDatabase.getInstance().getReference("participant-trails");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Trail> trailList = new ArrayList<Trail>();
                for (DataSnapshot child : dataSnapshot.child(uid).getChildren()) {
                    Trail trail = child.getValue(Trail.class);
                    trailList.add(trail);
                }
                trailListAdapter.updateDataSet(trailList);
                rvTrailList.setAdapter(trailListAdapter);

                tvEmptyTrailList.setText(R.string.empty_trail_list_participant);
                tvEmptyTrailList.setVisibility(trailListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
