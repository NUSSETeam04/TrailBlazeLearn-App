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

import sg.edu.iss.trailblazelearnft04.Adapter.ParticipantStationListAdapter;
import sg.edu.iss.trailblazelearnft04.DBDao.ParticipantStationHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Station;
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

public class ParticipantStationListActivity extends AppCompatActivity {
    private RecyclerView rvStationList;
    private ParticipantStationListAdapter stationListAdapter;
    private RecyclerView.LayoutManager stationListManager;
    private FloatingActionButton fabAddStation;
    private TextView tvEmptyStationList;
    private GoogleSignInClient mGoogleSignInClient;
    private ArrayList<Station> stationList = new ArrayList<Station>();
    private String uid= FirebaseAuth.getInstance().getUid();
    private FirebaseAuth mAuth;
    private String key;
    private Intent intent;
    private DatabaseReference mDatabase;
    private ParticipantStationHelperDao participantStationHelperDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_station_list);
        setContentView(R.layout.activity_station_list);

        rvStationList = (RecyclerView) findViewById(R.id.station_list);
        rvStationList.setHasFixedSize(false);

        stationListManager = new LinearLayoutManager(this);
        rvStationList.setLayoutManager(stationListManager);

        intent = getIntent();
        key = intent.getStringExtra("key");
        intent.putExtra("key",key);

        tvEmptyStationList = findViewById(R.id.tv_empty_station_list);
        //show a list of participant station
        stationListAdapter = new ParticipantStationListAdapter(stationList,false, key);
        rvStationList.setAdapter(stationListAdapter);

        participantStationHelperDao = new ParticipantStationHelperDao();
        participantStationHelperDao.getStationListForParticipant(key, rvStationList,stationListAdapter, tvEmptyStationList);

        fabAddStation = findViewById(R.id.fab_add_station);
        fabAddStation.setVisibility(View.GONE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if user is participant then
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
        // mAuth.signOut();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  //google log out
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(ParticipantStationListActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(ParticipantStationListActivity.this,MainActivity.class));
    }


}
