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

import java.util.ArrayList;

public class StationListActivity extends AppCompatActivity {
    private RecyclerView rvStationList;
    private StationListAdapter stationListAdapter;
    private RecyclerView.LayoutManager stationListManager;
    private FloatingActionButton fabAddStation;
    private TextView tvEmptyStationList;
    private GoogleSignInClient mGoogleSignInClient;
    private StationHelperDao stationHelperDao;
    private ArrayList<Station> stationList = new ArrayList<Station>();

    String key;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.title_station_list);
        setContentView(R.layout.activity_station_list);

        rvStationList = (RecyclerView) findViewById(R.id.station_list);
        rvStationList.setHasFixedSize(false);
        tvEmptyStationList = findViewById(R.id.tv_empty_station_list);

        stationListManager = new LinearLayoutManager(this);
        rvStationList.setLayoutManager(stationListManager);

        intent = getIntent();
        key = intent.getStringExtra("key");
        intent.putExtra("key",key);

        stationListAdapter = new StationListAdapter(stationList, false, key);
        rvStationList.setAdapter(stationListAdapter);

        stationHelperDao = new StationHelperDao();
        stationHelperDao.getStationListForTrainer(key, rvStationList, stationListAdapter, tvEmptyStationList);

        fabAddStation = findViewById(R.id.fab_add_station);
        fabAddStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StationListActivity.this, AddNewStationActivity.class);
                intent.putExtra("key",key);
                intent.putExtra("flag",0);
                int seqNo = stationListAdapter.getItemCount();
                intent.putExtra("seqNo",seqNo);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trainer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            signOut();

        } else if (i == R.id.action_edit) {
            Intent intent = new Intent(StationListActivity.this, EditStationActivity.class);
            intent.putExtra("key", key);
            startActivity(intent);
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
                        startActivity(new Intent(StationListActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(StationListActivity.this,MainActivity.class));
    }


}
