package com.example.littlebig.simpleimageviewer.simpleimageviewer.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.littlebig.simpleimageviewer.R;
import com.example.littlebig.simpleimageviewer.simpleimageviewer.task.ImageDownloaderTask;
import com.example.littlebig.simpleimageviewer.simpleimageviewer.util.Utilities;

import okhttp3.HttpUrl;


public class ImageDownloadActivity extends AppCompatActivity {
    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);

        final Button downloadButton = (Button) findViewById(R.id.download_button);
        final EditText urlEdit = (EditText) findViewById(R.id.url_edit);


        startDownload(downloadButton, urlEdit);

    }

    public void startDownload(Button b, final EditText e) {

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!Utilities.isNetworkAvailable(getApplicationContext())) {
                    int duration = Toast.LENGTH_SHORT;
                    //Stop any previous toasts/tip from reviewer
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    //Make and display new toast
                    mToast = Toast.makeText(getApplicationContext(), getString(R.string.error_connection), duration);
                    mToast.show();
                } else {
                    final String url = e.getText().toString();
                    HttpUrl downloadUrl = HttpUrl.parse(url);
                    if(downloadUrl!=null) {
                        ImageDownloaderTask task = new ImageDownloaderTask(ImageDownloadActivity.this, getApplicationContext());
                        task.execute(downloadUrl);
                    }
                    else{
                        Toast toast;
                        String message = getString(R.string.error_download);
                        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
    }
}


