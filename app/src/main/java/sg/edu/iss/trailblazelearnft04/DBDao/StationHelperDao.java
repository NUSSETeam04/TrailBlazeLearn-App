package sg.edu.iss.trailblazelearnft04.DBDao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import sg.edu.iss.trailblazelearnft04.Adapter.StationListAdapter;
import sg.edu.iss.trailblazelearnft04.Model.Station;

/**
 * Created by mia on 19/03/18.
 */

public class StationHelperDao {
    private DatabaseReference mDatabase;

    // Get station list for a trainer from firebase
    public void getStationListForTrainer(final String key, final RecyclerView rvStationList,
                                         final StationListAdapter stationListAdapter, final TextView tvEmptyStationList) {
        mDatabase= FirebaseDatabase.getInstance().getReference("stations");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Station> stationList =new ArrayList<Station>();
                for(DataSnapshot child:dataSnapshot.child(key).getChildren()){
                    Station station=child.getValue(Station.class);
                    stationList.add(station);
                }
                stationListAdapter.updateStationList(stationList);
                rvStationList.setAdapter(stationListAdapter);

                tvEmptyStationList.setVisibility(stationListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Add new station to firebase
    public void addNewStation(int seqNo,String key, String et_stationName, HashMap<String,Double> location, String address, String et_instruction) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String stationKey = mDatabase.child(key).push().getKey();
        Station station = new Station(seqNo,et_stationName, location, address, et_instruction, stationKey);
        mDatabase.child("stations/" + key).child(stationKey).setValue(station);
    }

    // Update station in firebase
    public void updateStation(int seqNo,String key, String stationKey, String et_stationName, HashMap<String,Double> location, String address, String et_instruction) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Station station = new Station(seqNo,et_stationName, location, address,et_instruction, stationKey);
        mDatabase.child("stations/" + key).child(stationKey).setValue(station);
    }

    // Delete station from firebase
    public void deleteStation(final Station station, final String key, final Context context1) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Query query = ref.child("stations").child(key).orderByChild("stationName").equalTo(station.getStationName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("tag11100", String.valueOf(dataSnapshot.getChildrenCount()));
                if(dataSnapshot.getChildrenCount()==1)
                {

                    for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                    {
                        String key1 = childSnapshot.getKey();

                        FirebaseDatabase.getInstance().getReference("stations").child(key).child(key1).removeValue();
                        Toast.makeText(context1, station.getStationName()+" deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
