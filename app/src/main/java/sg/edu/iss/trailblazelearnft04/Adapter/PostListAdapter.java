package sg.edu.iss.trailblazelearnft04.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Post;
import sg.edu.iss.trailblazelearnft04.R;

import java.util.ArrayList;


/**
 * Created by mia on 05/03/18.
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder>{

    private ArrayList<Post> myDataSet=new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPost;
        private TextView tvUserName;
        private TextView tvCreatedDate;

        private ViewHolder(View v) {
            super(v);
            tvPost = (TextView) v.findViewById(R.id.tv_post);
            tvUserName = (TextView) v.findViewById(R.id.tv_user_name);
            tvCreatedDate = (TextView) v.findViewById(R.id.tv_created_date);
        }
    }

    public PostListAdapter(ArrayList<Post> postList) {
        myDataSet = postList;
    }

    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post_list, parent, false);

        return new PostListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PostListAdapter.ViewHolder viewHolder, final int position) {

        String userId=myDataSet.get(position).getUserId();

        viewHolder.tvPost.setText(myDataSet.get(position).getPost());
        viewHolder.tvCreatedDate.setText(myDataSet.get(position).getTimestamp());

        UserHelperDao userHelperDao = new UserHelperDao();
        userHelperDao.setUserNameByUserId(userId, viewHolder.tvUserName);

    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

    public void updateDataSet(ArrayList<Post> postList) {
        myDataSet = postList;
        notifyDataSetChanged();
    }

}
