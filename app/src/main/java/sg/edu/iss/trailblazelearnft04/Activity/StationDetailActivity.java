package sg.edu.iss.trailblazelearnft04.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import sg.edu.iss.trailblazelearnft04.Adapter.TabPagerAdapter;
import sg.edu.iss.trailblazelearnft04.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class StationDetailActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String stationName = intent.getStringExtra("stationName");
        String trailKey = intent.getStringExtra("trailKey");
        this.setTitle(stationName);
        setContentView(R.layout.activity_station_detail);

        String stationId=intent.getStringExtra("stationId");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //create pager adapter to show three fragment
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), 3 , trailKey,stationId);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
        // mAuth.signOut();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);  //google log out
        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(StationDetailActivity.this, MainActivity.class));
                    }
                });

        LoginManager.getInstance().logOut();  //facebook log out

        startActivity(new Intent(StationDetailActivity.this,MainActivity.class));
    }
}
