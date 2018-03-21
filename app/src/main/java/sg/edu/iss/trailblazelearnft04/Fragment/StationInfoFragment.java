package sg.edu.iss.trailblazelearnft04.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Activity.AddNewItemActivity;
import sg.edu.iss.trailblazelearnft04.Adapter.ItemListAdapter;
import sg.edu.iss.trailblazelearnft04.DBDao.StationInfoHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.ContributedItem;
import sg.edu.iss.trailblazelearnft04.Model.Station;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StationInfoFragment extends Fragment {
    private TextView tvStationName,tv_station_address,tv_station_instruction;
    private Button btnUpload;
    private RecyclerView rvMyItemList;
    private RecyclerView.Adapter itemListAdapter;
    private RecyclerView.LayoutManager itemListManager;
    private ArrayList<ContributedItem> ContributedItemList;
    private String uid= FirebaseAuth.getInstance().getUid();
    private DatabaseReference mDatabase;
    private Station station;
    private String stationName;
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private StationInfoHelperDao stationInfoHelperDao;

    public StationInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.fragment_station_info, container, false);
        Bundle bundle = this.getArguments();
        String trailKey =bundle.getString("trailKey");
        final String stationID=bundle.getString("stationId");


        tvStationName = (TextView) fragmentView.findViewById(R.id.tv_station_name);
        tv_station_address = fragmentView.findViewById(R.id.tv_station_address);
        tv_station_instruction = fragmentView.findViewById(R.id.tv_station_instruction);
        tv_station_instruction.setMovementMethod(new ScrollingMovementMethod());
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        stationName = getActivity().getTitle().toString();
        tvStationName.setText(stationName);

        stationInfoHelperDao = new StationInfoHelperDao();
        stationInfoHelperDao.getStationInfo(trailKey, stationName, tvStationName, mapFragment, tv_station_instruction, tv_station_address);


        btnUpload = (Button) fragmentView.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AddNewItemActivity.class);
                intent.putExtra("stationId",stationID);
                context.startActivity(intent);
            }
        });


        // modify this according to context
        final String userID = uid;
        //final String stationID = "-L7XDL5ditoY4_Dr0BWG";

        ContributedItemList = new ArrayList<ContributedItem>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String temp_ref = "items/" + stationID;
        DatabaseReference ref = database.getReference(temp_ref);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ContributedItemList.clear();
                for (DataSnapshot ContributedItemSnapshot : dataSnapshot.getChildren()) {
                    ContributedItem ci = ContributedItemSnapshot.getValue(ContributedItem.class);
                    if(ci.getUserId().equals(userID)) {
                        ContributedItemList.add(ci);
                    }
                }

                // use ListView / RecyclerView to display the Uploaded Items (Not implemented yet)
                rvMyItemList = (RecyclerView) fragmentView.findViewById(R.id.my_item_list);
                rvMyItemList.setHasFixedSize(false);

                itemListManager = new LinearLayoutManager(getActivity());
                rvMyItemList.setLayoutManager(itemListManager);

                itemListAdapter = new ItemListAdapter(ContributedItemList, getContext());
                rvMyItemList.setAdapter(itemListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return fragmentView;
    }

}

