package sg.edu.iss.trailblazelearnft04.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.ContributedItem;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//This file is for add a new contribute item
public class AddNewItemActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnChooseFile;

    private Uri fileUri;
    private String fileName;
    private String fileType;
    private File photoFile;
    private String uid= FirebaseAuth.getInstance().getUid();
    private StorageReference mStorageRef;
    private Dialog dialog;
    private ImageView imageview;
    private SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle(R.string.title_new_item);
        setContentView(R.layout.activity_add_new_item);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        btnChooseFile = (Button) findViewById(R.id.btn_choose_file);
        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //show popwindow to choose file
                show(v);

            }
        });


        //create a firebase reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("items");

        mStorageRef = FirebaseStorage.getInstance().getReference();



        final String userID = uid;
        final String stationID=getIntent().getStringExtra("stationId");
        UserHelperDao userhelper=new UserHelperDao();
        Date date=new Date(System.currentTimeMillis());
        final String timeCreation=formatter.format(date);

        imageview=findViewById(R.id.imageview);

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save item in database
                Uri local_uri = AddNewItemActivity.this.fileUri;
                if(local_uri == null){
                    System.out.println("invalid file");
                    // Toast.makeText();
                    Toast.makeText(getApplicationContext(),"Please select a file to upload.", Toast.LENGTH_SHORT).show();
                } else {
                    final ContributedItem ci = new ContributedItem();
                    ci.setUserId(userID);
                    //ci.setUserName(userName);
                    ci.setTimeCreation(timeCreation);
                    ci.setFileType(AddNewItemActivity.this.fileType);
                    ci.setDescription("");

                    EditText tvDescription = (EditText) findViewById(R.id.et_description);
                    ci.setDescription(tvDescription.getText().toString());

                    ci.setFileURL("NA");
                    //add one more contribute to the items
                    DatabaseReference trailStationRef = ref.child(stationID);
                    DatabaseReference newContributedItem = trailStationRef.push();
                    newContributedItem.setValue(ci);
                    //add one more contribute to the participant items
                    DatabaseReference Ref = FirebaseDatabase.getInstance().
                            getReference("participant-items").child(uid).child(stationID);
                    DatabaseReference newParticipantItem = Ref.push();
                    newParticipantItem.setValue(ci);

                    String ci_Firebase_key = newContributedItem.getKey();
                    String local_uri_string = local_uri.toString();

                    new UploadFileTask(ci).execute(ci_Firebase_key, local_uri_string, stationID);

                }


            }
        });
    }
    public void show(View view){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);

        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_choose_file, null);

        TextView takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //take photo
                dialog.dismiss();
                File dirphoneFile=new File(Environment.getExternalStorageDirectory(),"pictures");
                photoFile=new File(dirphoneFile,System.currentTimeMillis() + ".jpg");
                Intent iimg=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(iimg,1);
            }
        });
        TextView choosefile=inflate.findViewById(R.id.choosefile);

        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose a exist file in phone
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                String[] mimetypes = {"image/*", "audio/*", "application/pdf", "application/msword"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 1001);
                    dialog.dismiss();
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    System.out.println("No file manager");
                }
            }
        });

        TextView cancel=inflate.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();

        dialogWindow.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//set the distance to the bottom
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1001:
                //save the exist file
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    this.fileUri = uri;

                    File selectedFile = new File(uri.getPath());
                    this.fileName = selectedFile.getName();

                    TextView textView = (TextView) findViewById(R.id.tv_file_name);
                    textView.setText(fileName);

                    String filenameArray[] = fileName.split("\\.");
                    String extension = filenameArray[filenameArray.length - 1];

                    if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                        this.fileType = "image";
                        imageview.setVisibility(View.VISIBLE);
                        imageview.setImageURI(uri);
                    } else if (extension.equals("mp3") || extension.equals("wav")) {
                        this.fileType = "audio";
                    } else if (extension.equals("pdf")) {
                        this.fileType = "pdf";
                    } else if (extension.equals("docx") || extension.equals("word")) {
                        this.fileType = "doc";
                    } else {
                        this.fileType = "image";
                    }
                }
                break;
            case 1:
                //save the photo we take
                Uri uri = Uri.fromFile(photoFile);
                this.fileUri = uri;

                File selectedFile = new File(uri.getPath());
                this.fileName = selectedFile.getName();

                TextView textView = (TextView) findViewById(R.id.tv_file_name);
                textView.setText(fileName);

                String filenameArray[] = fileName.split("\\.");
                String extension = filenameArray[filenameArray.length - 1];

                if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                    this.fileType = "image";
                    imageview.setVisibility(View.VISIBLE);
                    imageview.setImageURI(uri);

                } else if (extension.equals("mp3") || extension.equals("wav")) {
                    this.fileType = "audio";
                } else if (extension.equals("pdf")) {
                    this.fileType = "pdf";
                } else if (extension.equals("docx") || extension.equals("word")) {
                    this.fileType = "doc";
                } else {
                    this.fileType = "image";
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //use async task to upload file to the database
    private class UploadFileTask extends AsyncTask<String, Integer, String> {
        private ContributedItem ci;
        ProgressDialog  mProgressDialog;

        UploadFileTask(ContributedItem ci){
            this.ci = ci;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create ProgressBar
            mProgressDialog = new ProgressDialog(AddNewItemActivity.this);
            // Set your ProgressBar Title
            mProgressDialog.setTitle("Uploads");
            mProgressDialog.setIcon(R.drawable.com_facebook_button_icon);
            // Set your ProgressBar Message
            mProgressDialog.setMessage("Uploading contributed item, please Wait!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show ProgressBar
            mProgressDialog.setCancelable(false);
            //  mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final String ci_Firebase_key = params[0];
            String local_uri_string = params[1];
            final String stationID = params[2];

            Uri local_uri = Uri.parse(local_uri_string);

            // upload file to Firebase Cloud
            StorageReference catRef = mStorageRef.child(ci_Firebase_key);
            catRef.putFile(local_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebase_uri = taskSnapshot.getDownloadUrl();

                    ci.setFileURL(firebase_uri.toString());

                    String FirebaseLocation = "items/" + stationID + "/" + ci_Firebase_key + "/fileURL";
                    //save in the items table
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference(FirebaseLocation);
                    ref.setValue(firebase_uri.toString());

                    //save in the participant items table
                    String uid=FirebaseAuth.getInstance().getUid();
                    String participant_location="participant-items/"+uid+"/"+stationID+ "/" + ci_Firebase_key + "/fileURL";
                    DatabaseReference ref_participant=database.getReference(participant_location);
                    ref_participant.setValue(firebase_uri.toString());

                    mProgressDialog.dismiss();

                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    // ...
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    publishProgress((int)progress);
                    System.out.println("Upload is " + progress + "% done");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            });
            return "finish uploading";
        }
        //show the progress update
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}

