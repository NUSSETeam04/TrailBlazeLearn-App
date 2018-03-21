package sg.edu.iss.trailblazelearnft04.Activity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import sg.edu.iss.trailblazelearnft04.DBDao.TrailHelperDao;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao.getUid;


public class AddNewTrailActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText tv_name,tv_date;
    private DatabaseReference mDatabase;
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    private SimpleDateFormat formatter_date=new SimpleDateFormat("yyyyMMdd");
    private Date date;
    private String et_trail_date, name, trailId, oldTrailId;
    private String uid = getUid();
    private Calendar selectedDate = Calendar.getInstance();
    private String trailName=null,trailDate=null,timestamp=null;
    private int flag;
    private TrailHelperDao trailHelperDao;
    private ArrayList<String> existingTrailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_trail);

        // Could use DatePicker to choose the date
        Intent intent=getIntent();

        flag=intent.getIntExtra("flag",0);

        tv_name=findViewById(R.id.et_trail_name);
        tv_date=findViewById(R.id.et_trail_date);

        trailHelperDao = new TrailHelperDao();
        existingTrailId = new ArrayList<String>();
        trailHelperDao.getExistingTrailId(existingTrailId);

        if(flag==0){  //add new trail
            setTitle(R.string.title_new_trail);

            date=new Date(System.currentTimeMillis());
            et_trail_date=formatter.format(date); //timestamp
            tv_date.setText(formatter_date.format(selectedDate.getTime()));
            oldTrailId = "";
        }

        else if (flag==1){  //edit a trail
            setTitle(R.string.title_edit_trail);
            trailName=intent.getStringExtra("trailName");
            trailDate=intent.getStringExtra("trailDate");
            timestamp=intent.getStringExtra("timestamp");
            et_trail_date=timestamp;
            oldTrailId = trailDate + "-" + trailName;

            tv_name.setText(trailName);
            tv_date.setText(trailDate);
        }



        mDatabase = FirebaseDatabase.getInstance().getReference();

        tv_date.setInputType(InputType.TYPE_NULL);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                tv_date.setText(formatter_date.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(AddNewTrailActivity.this, onDateSetListener,
                                selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                                selectedDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the new trail
                if(isValid()){
                    save(flag);
                    finish();
                }
            }
        });
    }


    public void save(int flag){

        if (flag==0){
            String key=mDatabase.child("trails").push().getKey();
            trailHelperDao.addNewTrail(name,tv_date.getText().toString(),et_trail_date,trailId,key,uid);
        }
        if(flag==1){
            String key=getIntent().getStringExtra("key");
            trailHelperDao.updateTrail(name,tv_date.getText().toString(),et_trail_date,trailId,key,uid);
        }

    }
    public boolean isValid(){
        boolean isValid=true;
        name=tv_name.getText().toString();
        trailId=tv_date.getText().toString()+"-"+name;

        if(TextUtils.isEmpty(tv_name.getText().toString().trim())){
            tv_name.setError("please fill in the trail name");
            isValid=false;
        }
        if(TextUtils.isEmpty(tv_date.getText().toString().trim())){
            tv_date.setError("please select a date");
            isValid=false;
        }
        for (String id : existingTrailId) {
            if (trailId.equals(id)) {
                if (trailId.equals(oldTrailId)){
                    break;
                } else {
                    tv_name.setError("This trail id has already existed!");
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }
}
