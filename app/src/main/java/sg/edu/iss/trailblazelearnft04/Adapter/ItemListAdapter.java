package sg.edu.iss.trailblazelearnft04.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.iss.trailblazelearnft04.DBDao.UserHelperDao;
import sg.edu.iss.trailblazelearnft04.Model.ContributedItem;
import sg.edu.iss.trailblazelearnft04.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mia on 05/03/18.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    private ArrayList<ContributedItem> myDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // just static data now
        private ImageView ivFile;
        private TextView tvParticipantName;
        private TextView tvDescription;
        private TextView tvCreatedDate;

        private String ContributedItem_Type;
        private String ContributedItem_FirebaseURL;

        private ViewHolder(View v) {
            super(v);
            ivFile = v.findViewById(R.id.iv_file);
            tvParticipantName = (TextView) v.findViewById(R.id.tv_participant_name);
            tvDescription = (TextView) v.findViewById(R.id.tv_description);
            tvCreatedDate = (TextView) v.findViewById(R.id.tv_created_date);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context context = v.getContext();

                    String fileURL = ContributedItem_FirebaseURL;
                    String fileURL_split[]= fileURL.split("/");
                    String fileKeyAndAuth = fileURL_split[fileURL_split.length - 1];

                    String fileKeyAndAuth_split[]= fileKeyAndAuth.split("\\?");
                    String fileKey = fileKeyAndAuth_split[0];

                    String localFile_path = context.getExternalCacheDir() + fileKey + "." + ContributedItem_Type;
                    Log.i("tag pa",localFile_path);
                    final File localFile = new File(localFile_path);
                    if(!localFile.exists()) {

                        new DownloadFileTask().execute(localFile_path);
                    } else {

                        String ArgFileType;
                        if (ContributedItem_Type.equals("image")) {
                            ArgFileType = "image/*";
                        } else if (ContributedItem_Type.equals("audio")) {
                            ArgFileType = "audio/*";
                        } else if (ContributedItem_Type.equals("pdf")) {
                            ArgFileType = "application/pdf";
                        } else if (ContributedItem_Type.equals("doc")) {
                            ArgFileType = "application/msword";
                        } else {
                            ArgFileType = "image/*";
                        }

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(localFile), ArgFileType);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent);
                    }

                }
            });

        }


        private class DownloadFileTask extends AsyncTask<String, Integer, String> {
            ProgressDialog mProgressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create ProgressBar
                mProgressDialog = new ProgressDialog(itemView.getContext());
                // Set your ProgressBar Title
                mProgressDialog.setTitle("Downloads");
                mProgressDialog.setIcon(R.drawable.com_facebook_button_icon);
                // Set your ProgressBar Message
                mProgressDialog.setMessage("Downloading contributed item, please Wait!");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // Show ProgressBar
                mProgressDialog.setCancelable(false);
                //  mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
            }

            protected String doInBackground(String... params) {
                String localFile_path = params[0];
                final File localFile = new File(localFile_path);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(ContributedItem_FirebaseURL);

                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        mProgressDialog.dismiss();

                        // Successfully downloaded data to local file
                        String ArgFileType;
                        if (ContributedItem_Type.equals("image")) {
                            ArgFileType = "image/*";
                        } else if (ContributedItem_Type.equals("audio")) {
                            ArgFileType = "audio/*";
                        } else if (ContributedItem_Type.equals("pdf")) {
                            ArgFileType = "application/pdf";
                        } else if (ContributedItem_Type.equals("doc")) {
                            ArgFileType = "application/msword";
                        } else {
                            ArgFileType = "image/*";
                        }

                        Context context = itemView.getContext();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(localFile), ArgFileType);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        publishProgress((int)progress);
                        System.out.println("Upload is " + progress + "% done");
                    }
                });
                return "finish downloading";
            }

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

    public ItemListAdapter(ArrayList<ContributedItem> ContributedItemList, Context context) {
        myDataSet = ContributedItemList;
        this.context = context;
    }

    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_list, parent, false);
        return new ItemListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemListAdapter.ViewHolder viewHolder, int position) {
        // ivFile set Thumbnail for each file according to its file type (not implemented yet)
        viewHolder.tvDescription.setText(myDataSet.get(position).getDescription());
        UserHelperDao userhelper=new UserHelperDao();
        String uid= myDataSet.get(position).getUserId();
        userhelper.setUserNameByUserId(uid,viewHolder.tvParticipantName);
        //viewHolder.tvParticipantName.setText(myDataSet.get(position).getUserName());
        viewHolder.tvCreatedDate.setText(myDataSet.get(position).getTimeCreation());
        if (myDataSet.get(position).getFileType().equals("image")) {
            String fileURL = myDataSet.get(position).getFileURL();
            String fileURL_split[]= fileURL.split("/");
            String fileKeyAndAuth = fileURL_split[fileURL_split.length - 1];

            String fileKeyAndAuth_split[]= fileKeyAndAuth.split("\\?");
            String fileKey = fileKeyAndAuth_split[0];

            final File localFile = new File(this.context.getExternalCacheDir(), fileKey + ".jpg");
            if(localFile.exists()){
                try {
                    //Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    Bitmap bitmap=getImageThumbnail(localFile.getAbsolutePath(),65,65);
                    viewHolder.ivFile.setImageBitmap(bitmap);
                } catch (OutOfMemoryError e){
                    viewHolder.ivFile.setImageResource(R.mipmap.ic_launcher);
                }
            }else{
                // Download the file
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(myDataSet.get(position).getFileURL());

                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // View relevant file
                        try {
                            //Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            Bitmap myBitmap=getImageThumbnail(localFile.getAbsolutePath(),65,65);
                            viewHolder.ivFile.setImageBitmap(myBitmap);
                            viewHolder.ivFile.setImageBitmap(myBitmap);
                        } catch (OutOfMemoryError e){
                            viewHolder.ivFile.setImageResource(R.mipmap.ic_launcher);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        viewHolder.ivFile.setImageResource(R.mipmap.ic_launcher);
                    }
                });
            }
        } else if (myDataSet.get(position).getFileType().equals("audio")) {
            viewHolder.ivFile.setImageResource(R.mipmap.audio);
        } else if (myDataSet.get(position).getFileType().equals("pdf")) {
            viewHolder.ivFile.setImageResource(R.mipmap.pdf);
        } else if (myDataSet.get(position).getFileType().equals("doc")) {
            viewHolder.ivFile.setImageResource(R.mipmap.doc);
        } else {
            viewHolder.ivFile.setImageResource(R.mipmap.ic_launcher);
        }

        viewHolder.ContributedItem_Type = myDataSet.get(position).getFileType();
        viewHolder.ContributedItem_FirebaseURL = myDataSet.get(position).getFileURL();
    }

    @Override
    public int getItemCount() {
        return myDataSet.size();
    }

    public Bitmap getImageThumbnail(String uri,int width,int height){ //获取缩略图
        Bitmap bitmap=null;
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        bitmap=BitmapFactory.decodeFile(uri,options);
        options.inJustDecodeBounds=false;
        int beWidth=options.outWidth/width; //缩略图宽度
        int beHeight=options.outHeight/height;
        int be=1;
        if(beWidth<beHeight){
            be=beWidth;
        }else {
            be=beHeight;
        }
        if(be<=0){
            be=1;
        }
        options.inSampleSize=be;
        bitmap=BitmapFactory.decodeFile(uri,options);
        bitmap= ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;

    }


}