package sg.edu.iss.trailblazelearnft04.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Activity.AddNewStationActivity;
import sg.edu.iss.trailblazelearnft04.Activity.StationDetailActivity;
import sg.edu.iss.trailblazelearnft04.DBDao.StationHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Station;
import sg.edu.iss.trailblazelearnft04.R;
import sg.edu.iss.trailblazelearnft04.Util.ItemTouchHelperAdapter;
import sg.edu.iss.trailblazelearnft04.Util.ItemTouchHelperViewHolder;
import sg.edu.iss.trailblazelearnft04.Util.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mia on 04/03/18.
 */


public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.ViewHolder> implements ItemTouchHelperAdapter {


    private ArrayList<Station> myDataSet=new ArrayList<Station>();
    private boolean editable;
    private ImageButton btnDeleteStation;
    private String key;
    private Station station;
    private Intent intent;
    private  int position;
    private final OnStartDragListener mDragStartListener;
    private OnItemClickListener mItemClickListener;
    private StationHelperDao stationHelperDao;

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        System.out.print("from:"+fromPosition+" to position"+toPosition);
        if (fromPosition < myDataSet.size() && toPosition < myDataSet.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(myDataSet, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(myDataSet, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    public void updateStationList(ArrayList<Station> stationList) {
        myDataSet = stationList;
        notifyDataSetChanged();

    }

    @Override
    public void onItemDismiss(int position) {
        myDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        // each data item is just a string in this case
        private TextView tvStationName;
        private TextView tvStationSequence;
        private ImageView ivUploaded;
        private ImageButton btnAdjustUp;
        private ImageButton btnAdjustDown;
        private ImageView btnprogress;
        private String key;


        private ViewHolder(View v, boolean editable, final String key) {
            super(v);
            tvStationName = (TextView) v.findViewById(R.id.tv_station_name);
            tvStationSequence = (TextView) v.findViewById(R.id.tv_station_sequence);
            ivUploaded = (ImageView) v.findViewById(R.id.iv_uploaded);
            // now just use iv_up to instead the whole button, should be an whole ImageButton
            btnAdjustUp = (ImageButton) v.findViewById(R.id.iv_up);
            btnAdjustDown = (ImageButton) v.findViewById(R.id.iv_down);
            this.key=key;
            btnprogress=v.findViewById(R.id.iv_uploaded);

            btnprogress.setVisibility(View.GONE);


        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, position);
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public StationListAdapter(ArrayList<Station> stationList, boolean editable, String key, OnStartDragListener dragListner) {
        myDataSet = stationList;
        this.editable = editable;
        this.key=key;
        mDragStartListener = dragListner;
    }

    @Override
    public StationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station_list, parent, false);

        return new ViewHolder(v, editable,key);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        this.position=position;
        viewHolder.tvStationName.setText(myDataSet.get(position).getStationName());
        viewHolder.tvStationSequence.setText(String.valueOf(position+1));

        Resources res = viewHolder.itemView.getContext().getResources();
        final Context context = viewHolder.itemView.getContext();
        if(editable) {
            btnDeleteStation = (ImageButton) viewHolder.itemView.findViewById(R.id.btn_delete_station);
            btnDeleteStation.setVisibility(View.VISIBLE);

            btnDeleteStation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert(context,position);
                    Log.i("tag", String.valueOf(position));
                }
            });
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                    station=myDataSet.get(position);
                    intent = new Intent(context, AddNewStationActivity.class);
                    //intent.putExtra("trailId",key[0]);
                    System.out.println("station name"+station.getStationName());
                    intent.putExtra("flag",1);
                    intent.putExtra("stationName",station.getStationName());
                    intent.putExtra("location",station.getAddress());
                    System.out.println("location:"+station.getAddress());
                    intent.putExtra("instructions",station.getInstructions());
                    intent.putExtra("stationKey",station.getStationKey());
                    intent.putExtra("lati",station.getGps().get("latitude"));
                    intent.putExtra("longi",station.getGps().get("longitude"));
                    intent.putExtra("key",key);

                    context.startActivity(intent);
                }
            });

        }
        if (! editable) {
            // btnAdjust & btnDeleteStation is invisible now
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationDetailActivity
                    Context context = v.getContext();
                    TextView tvStationName = (TextView) v.findViewById(R.id.tv_station_name);
                    Station station=myDataSet.get(position);
                    Intent intent = new Intent(context, StationDetailActivity.class);
                    intent.putExtra("stationName", tvStationName.getText().toString());
                    intent.putExtra("stationId",station.getStationKey());
                    intent.putExtra("trailKey", key);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void alert(Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Delete this station?");
        final Context context1 = context;
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("tag","in on click");
                Station station = myDataSet.get(position);
                stationHelperDao = new StationHelperDao();
                stationHelperDao.deleteStation(station, key, context1);

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }
    @Override
    public int getItemCount() {
        return myDataSet.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }




}