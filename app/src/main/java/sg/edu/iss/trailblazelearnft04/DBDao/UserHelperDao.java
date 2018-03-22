package sg.edu.iss.trailblazelearnft04.DBDao;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.iss.trailblazelearnft04.Model.User;

/**
 * Created by mia on 19/03/18.
 */

public class UserHelperDao {
    // Get current user id
    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    // Set the user name by user id
    public void setUserNameByUserId(String userId, final TextView tvUserName) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");

        ref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                String username=user.getUserName();
                tvUserName.setText(username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
