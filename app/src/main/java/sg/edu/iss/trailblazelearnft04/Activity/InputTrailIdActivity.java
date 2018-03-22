package sg.edu.iss.trailblazelearnft04.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import sg.edu.iss.trailblazelearnft04.Model.Trail;
import sg.edu.iss.trailblazelearnft04.R;

import static sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao.getUid;

/**
 * Created by wangzuxiu on 04/03/18.
 */

public class InputTrailIdActivity extends AppCompatActivity {

    private TextView inputId;
    private Button addButton;
    private String uid = getUid();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_trail_id);

        inputId=findViewById(R.id.inputID);
        addButton =findViewById(R.id.addTrail);

        //participant a new trail
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    String trailId=inputId.getText().toString();
                    Log.i("tag",trailId);
                    final DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
                    final Context context=v.getContext();
                    Query query=ref.child("trails").orderByChild("trailId").equalTo(inputId.getText().toString());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //save this trail in participant-trail
                            if (dataSnapshot.getChildrenCount() == 1) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String key = childSnapshot.getKey();
                                    Trail trail = childSnapshot.getValue(Trail.class);
                                    ref.child("participant-trails").child(uid).child(key).setValue(trail.toMap());
                                    finish();

                                }
                            }
                            //if the trail id doesn't exist
                            if (dataSnapshot.getChildrenCount() == 0) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                                builder.setTitle("Invalid trailId");
                                builder.setMessage("This trail Id doesn't exist");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog dialog= builder.create();
                                dialog.show();

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

        });



    }



    public boolean isValid(){
        boolean isValid=true;
        if(TextUtils.isEmpty(inputId.getText().toString().trim())){
            inputId.setError("Please input the trail id");
            isValid=false;
        }
        return isValid;
    }
}