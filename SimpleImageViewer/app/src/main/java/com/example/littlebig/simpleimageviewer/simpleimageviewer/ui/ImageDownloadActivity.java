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
                    //Make and display new toast
                    mToast = Toast.makeText(getApplicationContext(), getString(R.string.error_connection), Toast.LENGTH_SHORT);
                    mToast.show();
                } else {
                    final String url = e.getText().toString();
                    HttpUrl downloadUrl = HttpUrl.parse(url);

                    if(downloadUrl!=null) {
                        ImageDownloaderTask task = new ImageDownloaderTask(ImageDownloadActivity.this, getApplicationContext());
                        task.execute(downloadUrl);
                    } else {
                        mToast = Toast.makeText(getApplicationContext(), getString(R.string.error_download), Toast.LENGTH_SHORT);
                        mToast.show();
                    }
                }
            }
        });
    }
}


