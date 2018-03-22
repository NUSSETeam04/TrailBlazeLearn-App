package sg.edu.iss.trailblazelearnft04.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.iss.trailblazelearnft04.DBDao.StationHelperDao;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;


public class AddNewStationActivity extends AppCompatActivity {
    private Button btnSave, btnGetLocation;
    private EditText et_stationName;
    private TextView tv_stationLocation;
    private EditText et_instruction;
    private int flag,seqNo;
    private String stationName, instructions, address;
    private Intent intent;
    private GoogleMap map;
    private int PLACE_PICKER_REQUEST;
    private Place place;
    private PlacePicker.IntentBuilder builder;
    private LatLng gps;
    private double lati,longi;
    private StationHelperDao stationHelperDao;
    HashMap<String, Double> location = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_station);
        intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        //initialize the view
        et_stationName = (EditText) findViewById(R.id.et_station_name);
        tv_stationLocation = (TextView) findViewById(R.id.tv_station_address);
        et_instruction = (EditText) findViewById(R.id.et_station_instruction);
        et_instruction.setMovementMethod(new ScrollingMovementMethod());
        btnGetLocation = (Button) findViewById(R.id.btn_getLocation);


        if (flag == 0) {
            //flag=0 means we want to create a new station
            this.setTitle(R.string.title_new_station);
            seqNo = intent.getIntExtra("seqNo",0);

        } else if (flag == 1) {
            //flag==1 means we want to edit a exist station
            this.setTitle(R.string.title_edit_station);
            seqNo = intent.getIntExtra("seqNo",0);
            stationName = intent.getStringExtra("stationName");
            address = intent.getStringExtra("location");
            instructions = intent.getStringExtra("instructions");
            lati = intent.getDoubleExtra("lati",0.0);
            longi = intent.getDoubleExtra("longi",0.0);
            location.put("latitude",lati);
            location.put("longitude",longi);
            et_stationName.setText(stationName);
            tv_stationLocation.setText(address);
            et_instruction.setText(instructions);
        }

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new station
                if (isValid()) {

                    save();
                    finish();
                }


            }
        });
    }

    public boolean isValid() {
        boolean isValid = true;
        //station name can not be null
        if (TextUtils.isEmpty(et_stationName.getText().toString().trim())) {
            et_stationName.setError("Please enter station name");
            isValid = false;
        }
        //station location can not be null
        if (TextUtils.isEmpty(tv_stationLocation.getText().toString().trim())) {
            tv_stationLocation.setError("Please pick a location");
            isValid = false;
        }
        if (TextUtils.isEmpty(et_instruction.getText().toString().trim())) {
            et_instruction.setError("Please enter instructions");
            isValid = false;
        }
        return isValid;
    }

    public void save() {

        if (flag == 0) {
            //for create a new station we need a new key
            String key = getTrailKey();
            HashMap<String, Double> location = new HashMap<>();
            location.put("latitude", gps.latitude);
            location.put("longitude", gps.longitude);
            stationHelperDao = new StationHelperDao();
            stationHelperDao.addNewStation(seqNo,key, et_stationName.getText().toString(), location, address, et_instruction.getText().toString());
        }
        if (flag == 1) {
            //edit a station get the exist key
            String key = getTrailKey();
            String stationKey = intent.getStringExtra("stationKey");
            stationHelperDao = new StationHelperDao();
            stationHelperDao.updateStation(seqNo,key, stationKey, et_stationName.getText().toString(), location, address, et_instruction.getText().toString());
        }
    }

    public String getTrailKey() {
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        return key;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onStart() {
        super.onStart();
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PLACE_PICKER_REQUEST = 1;
                builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(AddNewStationActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(this, data);
                final CharSequence name = place.getName();
                address = place.getName().toString()+"\n"+place.getAddress().toString();
                String attributions = PlacePicker.getAttributions(data);
                gps = place.getLatLng();

                if (attributions == null) {
                    attributions = "";
                }

                tv_stationLocation.setText(address);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
    }

}
