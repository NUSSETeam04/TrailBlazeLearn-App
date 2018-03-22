package sg.edu.iss.trailblazelearnft04.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Activity.ParticipantStationListActivity;
import sg.edu.iss.trailblazelearnft04.Model.Trail;
import sg.edu.iss.trailblazelearnft04.R;
import java.util.ArrayList;


/**
 * Created by mia on 28/02/18.
 */

public class ParticipantTrailListAdapter extends RecyclerView.Adapter<ParticipantTrailListAdapter.ViewHolder> {

    private ArrayList<Trail> myDataSet=new ArrayList<>();
    private boolean editable;
    private Context context;
    private String trailId;
    private Trail trail;
    private Intent intent;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTrailName;
        private TextView tvTrailId;
        private TextView tvTrailDate;
        private ImageButton btnDeleteTrail;

        private ViewHolder(View v, boolean editable) {
            super(v);
            tvTrailName = (TextView) v.findViewById(R.id.tv_trail_name);
            tvTrailId = (TextView) v.findViewById(R.id.tv_trail_id);
            tvTrailDate = (TextView) v.findViewById(R.id.tv_trail_date);
            btnDeleteTrail = (ImageButton) v.findViewById(R.id.btn_delete_trail);

        }
    }

    public ParticipantTrailListAdapter(ArrayList<Trail> trailList, boolean editable) {
        myDataSet = trailList;
        this.editable = editable;
    }

    public void updateDataSet(ArrayList<Trail> trailList) {
        myDataSet = trailList;
        notifyDataSetChanged();
    }

    @Override
    public ParticipantTrailListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trail_list, parent, false);

        ViewHolder holder= new ViewHolder(v, editable);

        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int position) {
        viewHolder.tvTrailName.setText(myDataSet.get(position).getTrailName());
        trailId=myDataSet.get(position).getTrailDate()+"-"+myDataSet.get(position).getTrailName();
        viewHolder.tvTrailId.setText(trailId);
        viewHolder.tvTrailDate.setText(myDataSet.get(position).getTrailDate());
        final Context context = viewHolder.itemView.getContext();


        if (! editable) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationListActivity
                    final Context context = v.getContext();
                    trail=myDataSet.get(position);
                    Intent intent = new Intent(context, ParticipantStationListActivity.class);
                    intent.putExtra("key",trail.getKey());
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return  myDataSet.size();
    }



}