package com.example.littlebig.simpleimageviewer.simpleimageviewer.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.littlebig.simpleimageviewer.R;
import com.example.littlebig.simpleimageviewer.simpleimageviewer.ui.ImageViewActivity;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by littleBIG on 3/16/2016.
 */
public class ImageDownloaderTask extends AsyncTask<HttpUrl, Void, byte[]> {

    public final String LOG_TAG = ImageDownloaderTask.class.getSimpleName();
    private Activity activity;
    private Context context;
    ProgressDialog dialog;

    private OkHttpClient client = new OkHttpClient();

    public ImageDownloaderTask(Activity a,Context c){
        this.activity = a;
        this.context = c;
        dialog = new ProgressDialog(activity);

    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Downloading image...Please wait.");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected byte[] doInBackground(HttpUrl... params) {

        try{

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);

            Request request = builder.build();
            Response response = client.newCall(request).execute();

            return response.body().bytes();

        }catch(Exception e){
            Log.d(LOG_TAG, "download...failed.");
            e.printStackTrace();
            return null;
            }

        }


    protected void onPostExecute(byte[] image) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(image != null && image.length > 0) {
            Intent intent = new Intent(context.getApplicationContext(), ImageViewActivity.class);
            intent.putExtra("image_in_bytes", image);
            activity.startActivity(intent);
        }else{
            Toast toast;
            String message = activity.getString(R.string.error_wrong);
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        }


    }

}
