package sg.edu.iss.trailblazelearnft04.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Activity.StationDetailActivity;
import sg.edu.iss.trailblazelearnft04.Model.Station;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mia on 04/03/18.
 */


public class ParticipantStationListAdapter extends RecyclerView.Adapter<ParticipantStationListAdapter.ViewHolder> {

    private ArrayList<Station> myDataSet=new ArrayList<>();
    private boolean editable;
    private ImageButton btnDeleteStation;
    private String key;
    private Station station;
    private Intent intent;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStationName;
        private TextView tvStationSequence;
        private ImageView ivUploaded;
        private String key;


        private ViewHolder(View v, boolean editable, final String key) {
            super(v);
            tvStationName = (TextView) v.findViewById(R.id.tv_station_name);
            tvStationSequence = (TextView) v.findViewById(R.id.tv_station_sequence);
            ivUploaded = (ImageView) v.findViewById(R.id.iv_uploaded);
            this.key=key;


        }
    }

    public ParticipantStationListAdapter(ArrayList<Station> stationList, boolean editable,String key) {
        myDataSet = stationList;
        this.editable = editable;
        this.key=key;
    }

    @Override
    public ParticipantStationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station_list, parent, false);

        return new ViewHolder(v, editable,key);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.tvStationName.setText(myDataSet.get(position).getStationName());
        viewHolder.tvStationSequence.setText(String.valueOf(position+1));
        // If user is trainer, ivUploaded should be invisible
        // viewholder.ivUploaded.setVisibility(View.GONE); if user is trainer

        // Change the image if this participant have uploaded contributed item for this station
        // Now just randomly pick the image according to position, should be implemented correctly
        // The image of "√" should be changed to a nicer picture, find it in Layout item_station_list
        final Resources res = viewHolder.itemView.getContext().getResources();
        final Context context = viewHolder.itemView.getContext();

        if (! editable) {
            // btnAdjust & btnDeleteStation is invisible now
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationDetailActivity
                    TextView tvStationName = (TextView) v.findViewById(R.id.tv_station_name);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, StationDetailActivity.class);
                    Station station=myDataSet.get(position);
                    intent.putExtra("stationName", tvStationName.getText().toString());
                    intent.putExtra("trailKey", key);
                    intent.putExtra("stationId",station.getStationKey());
                    context.startActivity(intent);
                }
            });
        }

        Station station=myDataSet.get(position);
        String stationkey=station.getStationKey();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("items");
        ref.child(stationkey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()!=0)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        viewHolder.ivUploaded.setImageDrawable(res.getDrawable(android.R.drawable.checkbox_on_background, null));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        if (position % 2 == 0) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                viewHolder.ivUploaded.setImageDrawable(res.getDrawable(android.R.drawable.checkbox_on_background, null));
//            }
//        }

    }

    public void updateDataSet(ArrayList<Station> stationList) {
        myDataSet = stationList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return myDataSet.size();
    }



}