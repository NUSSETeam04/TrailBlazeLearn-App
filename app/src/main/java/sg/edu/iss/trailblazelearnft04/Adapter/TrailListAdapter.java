package sg.edu.iss.trailblazelearnft04.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Activity.AddNewTrailActivity;
import sg.edu.iss.trailblazelearnft04.Activity.StationListActivity;
import sg.edu.iss.trailblazelearnft04.DBDao.TrailHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Trail;
import sg.edu.iss.trailblazelearnft04.R;
import java.util.ArrayList;

import static sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao.getUid;


/**
 * Created by mia on 28/02/18.
 */

public class TrailListAdapter extends RecyclerView.Adapter<TrailListAdapter.ViewHolder> {

    private ArrayList<Trail> myDataSet=new ArrayList<>();
    private boolean editable;
//    private Context context;
    private String trailId;
    private Trail trail;
    private Intent intent;
    private TrailHelperDao trailHelperDao;
    private String uid = getUid();

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
//            btnDeleteTrail = (ImageButton) v.findViewById(R.id.btn_delete_trail);

        }
    }

    public TrailListAdapter(ArrayList<Trail> trailList, boolean editable) {
        myDataSet = trailList;
        this.editable = editable;
    }

    public void updateDataSet (ArrayList<Trail> trailList) {
        myDataSet = trailList;
        notifyDataSetChanged();
    }

    @Override
    public TrailListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trail_list, parent, false);

        ViewHolder holder= new ViewHolder(v, editable);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvTrailName.setText(myDataSet.get(position).getTrailName());
        trailId=myDataSet.get(position).getTrailDate()+"-"+myDataSet.get(position).getTrailName();
        viewHolder.tvTrailId.setText(trailId);
        viewHolder.tvTrailDate.setText(myDataSet.get(position).getTrailDate());
        final Context context = viewHolder.itemView.getContext();
        if (editable){
            ImageButton btnDeleteTrail = (ImageButton) viewHolder.itemView.findViewById(R.id.btn_delete_trail);
            btnDeleteTrail.setVisibility(View.VISIBLE);
            //delete a learning trail
            btnDeleteTrail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert(context,position);
                    Log.i("tag", String.valueOf(position));
                }
            });

            //update trail
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trail = myDataSet.get(position);
//                    String Id=trail.trailDate+"-"+trail.trailName;
                    intent = new Intent(context, AddNewTrailActivity.class);
                    //intent.putExtra("trailId",key[0]);

                    intent.putExtra("flag",1);
                    intent.putExtra("trailName",trail.getTrailName());
                    intent.putExtra("trailDate",trail.getTrailDate());
                    intent.putExtra("timestamp",trail.getTimestamp());
                    intent.putExtra("key",trail.getKey());
                    //intent.putExtra("trailId",trail.trailDate+"-"+trail.trailName);
                    context.startActivity(intent);

                }
            });

        }

        if (! editable) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Launch StationListActivity
                    final Context context = v.getContext();
                    trail = myDataSet.get(position);
                    Intent intent = new Intent(context, StationListActivity.class);
                    intent.putExtra("key",trail.getKey());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //return myDataSet==null ? 0 : myDataSet.size();
        return  myDataSet.size();
    }

    public void alert(Context context, final int position){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure to delete this trail?");
        builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Trail trail = myDataSet.get(position);
                trailHelperDao = new TrailHelperDao();
                trailHelperDao.deleteTrail(trail, uid);

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

}