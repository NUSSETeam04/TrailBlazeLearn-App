package sg.edu.iss.trailblazelearnft04.DBDao;

import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import sg.edu.iss.trailblazelearnft04.Model.Station;

/**
 * Created by mia on 19/03/18.
 */

public class StationInfoHelperDao implements OnMapReadyCallback {
    private DatabaseReference mDatabase;
    private GoogleMap map;
    private Station station;

    // Get station info from firebase
    public void getStationInfo(String trailKey, String stationName, final TextView tvStationName,
                               final SupportMapFragment mapFragment, final TextView tv_station_instruction,
                               final TextView tv_station_address) {
        mDatabase= FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("stations").child(trailKey).
                orderByChild("stationName").equalTo(stationName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==1)
                {
                    for(DataSnapshot childSnapshot : dataSnapshot.getChildren())
                    {
                        String key1 = childSnapshot.getKey();

                        station = childSnapshot.getValue(Station.class);
                        tvStationName.setText(station.getStationName());
                        tv_station_instruction.setText(station.getInstructions());
                        tv_station_address.setText(station.getAddress());
                        //initializing map fragment
                        mapFragment.getMapAsync(StationInfoHelperDao.this);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Get the location from google map
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        HashMap<String,Double> location = station.getGps();
        LatLng latLng = new LatLng(location.get("latitude"),location.get("longitude"));
        // map fragment with the location co-ordinated (latitude-longitude)
        map.addMarker(new MarkerOptions().position(latLng));
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

    }
}
