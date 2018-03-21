package sg.edu.iss.trailblazelearnft04.DBDao;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sg.edu.iss.trailblazelearnft04.Adapter.PostListAdapter;
import sg.edu.iss.trailblazelearnft04.Model.Post;

/**
 * Created by mia on 20/03/18.
 */

public class StationPostHelperDao {
    public void getPostList(final String discussionId, final PostListAdapter postListAdapter, final RecyclerView rvPostList) {
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference("posts");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Post> postList=new ArrayList<Post>();
                for(DataSnapshot child:dataSnapshot.child(discussionId).getChildren()){
                    Post post=child.getValue(Post.class);
                    postList.add(post);
                }
                postListAdapter.updateDataSet(postList);
                rvPostList.setAdapter(postListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addNewPost(String discussionId, String uid, String post, String postTimestamp) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("posts");
        String postId=ref.child(discussionId).push().getKey();
        Post temp=new Post(uid, post, postTimestamp);
        ref.child(discussionId).child(postId).setValue(temp);
    }
}
