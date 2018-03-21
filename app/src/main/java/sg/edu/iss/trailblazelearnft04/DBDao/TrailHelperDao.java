package sg.edu.iss.trailblazelearnft04.DBDao;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sg.edu.iss.trailblazelearnft04.Adapter.TrailListAdapter;
import sg.edu.iss.trailblazelearnft04.Model.Trail;

/**
 * Created by mia on 19/03/18.
 */

public class TrailHelperDao {
    private DatabaseReference mDatabase;

    public void getTrailsForTrainer(final String uid, final RecyclerView rvTrailList,
                                    final TrailListAdapter trailListAdapter, final TextView tvEmptyTrailList) {

        mDatabase = FirebaseDatabase.getInstance().getReference("trainer-trails");

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

                tvEmptyTrailList.setVisibility(trailListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void addNewTrail(String name, String date, String timestamp, String trailId, String key, String uid) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Trail trail = new Trail(name, date, timestamp, trailId, key);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> trailList = trail.toMap();
        childUpdates.put("/trails/" + key, trailList);
        childUpdates.put("/trainer-trails/" + uid + "/" + key, trailList);

        mDatabase.updateChildren(childUpdates);
    }

    public void updateTrail(String name, String date, String timestamp, String trailId, final String key, String uid) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Trail trail = new Trail(name, date, timestamp, trailId, key);
        Map<String, Object> childUpdates = new HashMap<>();
        final Map<String, Object> trailList = trail.toMap();
        childUpdates.put("/trails/" + key, trailList);
        childUpdates.put("/trainer-trails/" + uid + "/" + key, trailList);
        mDatabase.updateChildren(childUpdates);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("participant-trails");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String uidKey = childSnapshot.getKey();
                    for (DataSnapshot child : childSnapshot.getChildren()) {
                        if (child.getKey().equals(key)) {
                            FirebaseDatabase.getInstance().getReference().child("participant-trails").child(uidKey).child(child.getKey()).setValue(trailList);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteTrail(Trail trail, final String uid) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        String trailId = trail.getTrailId();
        final Query query=ref.child("trails").orderByChild("trailId").equalTo(trailId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==1){
                    for(DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                        final String key=childSnapshot.getKey();
                        Log.i("tag",key);
                        FirebaseDatabase.getInstance().getReference().child("trails").child(key).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("trainer-trails").child(uid).child(key).removeValue();
                        //when delete a trail,participant-trails should also be deleted.
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("participant-trails");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                                    String uidKey=childSnapshot.getKey();
                                    for(DataSnapshot child:childSnapshot.getChildren()){
                                        if(child.getKey().equals(key)){
                                            FirebaseDatabase.getInstance().getReference().child("participant-trails").child(uidKey).child(child.getKey()).removeValue();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getExistingTrailId(final ArrayList<String> existingTrailId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String temp_ref = "trails/";
        DatabaseReference ref = database.getReference(temp_ref);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                existingTrailId.clear();
                for (DataSnapshot ContributedItemSnapshot : dataSnapshot.getChildren()) {
                    Trail trail = ContributedItemSnapshot.getValue(Trail.class);
                    existingTrailId.add(trail.getTrailId());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
