package sg.edu.iss.trailblazelearnft04.DBDao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Adapter.ParticipantStationListAdapter;
import sg.edu.iss.trailblazelearnft04.Model.Station;
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

public class ParticipantStationHelperDao {
    private DatabaseReference mDatabase;

    public void getStationListForParticipant(final String key, final RecyclerView rvStationList, final ParticipantStationListAdapter stationListAdapter,
                                             final TextView tvEmptyStationList) {
        mDatabase= FirebaseDatabase.getInstance().getReference("stations");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Station> stationList = new ArrayList<Station>();
                for(DataSnapshot child:dataSnapshot.child(key).getChildren()){
                    Station station=child.getValue(Station.class);
                    stationList.add(station);
                }
//                key = intent.getStringExtra("key");
                stationListAdapter.updateDataSet(stationList);
                rvStationList.setAdapter(stationListAdapter);

                tvEmptyStationList.setText(R.string.empty_station_list_participant);
                tvEmptyStationList.setVisibility(stationListAdapter.getItemCount() != 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
