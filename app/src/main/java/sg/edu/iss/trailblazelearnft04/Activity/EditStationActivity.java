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

import sg.edu.iss.trailblazelearnft04.Adapter.StationListAdapter;
import sg.edu.iss.trailblazelearnft04.DBDao.StationHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.Station;
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
 * Created by Neelam on 3/15/2018.
 */

public class EditStationActivity extends AppCompatActivity {


    private RecyclerView rvStationList;
    private StationListAdapter stationListAdapter;
    private RecyclerView.LayoutManager stationListManager;
    private FloatingActionButton fabAddStation;
    private TextView tvEmptyStationList;
    private GoogleSignInClient mGoogleSignInClient;
    private String uid = getUid();
    private String key;
    private Intent intent;
    private StationHelperDao stationHelperDao;
    private ArrayList<Station> stationList = new ArrayList<Station>();


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_station_list);
        setContentView(R.layout.activity_station_list);

        rvStationList = (RecyclerView) findViewById(R.id.station_list);
        rvStationList.setHasFixedSize(true);
        tvEmptyStationList = findViewById(R.id.tv_empty_station_list);

        stationListManager = new LinearLayoutManager(this);
        rvStationList.setLayoutManager(stationListManager);


        intent = getIntent();
        key = intent.getStringExtra("key");

        stationListAdapter = new StationListAdapter(stationList, true, key, null);
        rvStationList.setAdapter(stationListAdapter);

        stationHelperDao = new StationHelperDao();
        stationHelperDao.getStationListForTrainer(key, rvStationList, stationListAdapter, tvEmptyStationList);


        fabAddStation= findViewById(R.id.fab_add_station);
        fabAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;
                Intent intent=new Intent(EditStationActivity.this,AddNewStationActivity.class);
                intent.putExtra("key",key);
                intent.putExtra("flag",0);
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
                        startActivity(new Intent(EditStationActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(EditStationActivity.this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        stationHelperDao.getStationListForTrainer(key, rvStationList, stationListAdapter, tvEmptyStationList);
    }

}
