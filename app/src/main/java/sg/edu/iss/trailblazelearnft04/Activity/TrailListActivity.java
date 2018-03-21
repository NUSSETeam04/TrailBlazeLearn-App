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

import sg.edu.iss.trailblazelearnft04.Adapter.TrailListAdapter;
import sg.edu.iss.trailblazelearnft04.DBDao.TrailHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Trail;
import sg.edu.iss.trailblazelearnft04.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao.getUid;


/**
 * Created by wangzuxiu on 04/03/18.
 */

public class TrailListActivity extends AppCompatActivity {

    private RecyclerView rvTrailList;
    private TrailListAdapter trailListAdapter;
    private RecyclerView.LayoutManager trailListManager;
    private FloatingActionButton fabAddTrail;
    private TextView tvEmptyTrailList;
    private GoogleSignInClient mGoogleSignInClient;
    private String uid = getUid();
    private TrailHelperDao trailHelperDao;
    private ArrayList<Trail> trailList=new ArrayList<Trail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_trail_list);
        setContentView(R.layout.activity_trail_list);

        rvTrailList = (RecyclerView) findViewById(R.id.trail_list);
        rvTrailList.setHasFixedSize(true);
        tvEmptyTrailList = findViewById(R.id.tv_empty_trail_list);

        trailListManager = new LinearLayoutManager(this);
        rvTrailList.setLayoutManager(trailListManager);

        trailListAdapter = new TrailListAdapter(trailList,false);
        rvTrailList.setAdapter(trailListAdapter);

        trailHelperDao = new TrailHelperDao();
        trailHelperDao.getTrailsForTrainer(uid, rvTrailList, trailListAdapter, tvEmptyTrailList);

        fabAddTrail = findViewById(R.id.fab_add_trail);
        fabAddTrail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                Intent intent=new Intent(TrailListActivity.this,AddNewTrailActivity.class);
                intent.putExtra("flag",0);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if user is trainer then
        getMenuInflater().inflate(R.menu.menu_trainer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            signOut();

        } else if (i == R.id.action_edit) {

            startActivity(new Intent(TrailListActivity.this,EditTrailListActivity.class));

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
                        startActivity(new Intent(TrailListActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(TrailListActivity.this, MainActivity.class));
    }


}



