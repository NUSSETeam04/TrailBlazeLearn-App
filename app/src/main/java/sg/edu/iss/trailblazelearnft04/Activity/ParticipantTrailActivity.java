package sg.edu.iss.trailblazelearnft04.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.Adapter.ParticipantTrailListAdapter;
import sg.edu.iss.trailblazelearnft04.DBDao.ParticipantTrailHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Trail;
import sg.edu.iss.trailblazelearnft04.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao.getUid;


/**
 * Created by wangzuxiu on 14/03/18.
 */

public class ParticipantTrailActivity extends AppCompatActivity {
    private RecyclerView rvTrailList;
    private ParticipantTrailListAdapter trailListAdapter;
    private RecyclerView.LayoutManager trailListManager;
    private FloatingActionButton fabAddTrail;
    private TextView tvEmptyTrailList;
    private DatabaseReference mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private String uid= getUid();
    private ParticipantTrailHelperDao participantTrailHelperDao;

    private ArrayList<Trail> trailList=new ArrayList<Trail>();
    //private String[][] trailList = {{"Tour to ISS", "20180301-ISS", "2018-03-01"}, {"Tour to NUS", "20180401-NUS", "2018-04-01"}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_trail_list);
        setContentView(R.layout.activity_trail_list);

        rvTrailList = (RecyclerView) findViewById(R.id.trail_list);
        rvTrailList.setHasFixedSize(true);

        trailListManager = new LinearLayoutManager(this);
        rvTrailList.setLayoutManager(trailListManager);

        tvEmptyTrailList = findViewById(R.id.tv_empty_participant_list);
        trailListAdapter = new ParticipantTrailListAdapter(trailList,false);
        rvTrailList.setAdapter(trailListAdapter);

        participantTrailHelperDao = new ParticipantTrailHelperDao();
        participantTrailHelperDao.getTrailsForParticipant(uid, rvTrailList, trailListAdapter, tvEmptyTrailList);


        fabAddTrail = findViewById(R.id.fab_add_trail);

        fabAddTrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ParticipantTrailActivity.this,InputTrailIdActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            signOut();
        }
        return true;
    }

    private void signOut() {
        // Firebase sign out
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signOut();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  //google log out
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(ParticipantTrailActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(ParticipantTrailActivity.this, MainActivity.class));
    }

}
