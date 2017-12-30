package io.github.ryanfin.facerekognitionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;

import java.util.Arrays;

public class ImageGallery extends Activity {

    Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);



//        final ImageView imageView = (ImageView) findViewById(R.id.galleryimageview);
//
//        downloadButton = (Button) findViewById(R.id.button);
//        downloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Glide.with(getBaseContext())
//                        .load("https://s3-eu-west-1.amazonaws.com/mobile-face-recognition-bucket/ryan_barbados.JPG")
//                        .centerCrop()
//                        .into(imageView);
//            }
//        });

    }

    class s3Setup extends AsyncTask<Void,Void,Void>{

        Handler handler = new Handler();
        ImageView imageView = (ImageView) findViewById(R.id.galleryimageview);
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("S3THREAD", "S3 thread running...");
            try {
                //Cognitio Credential Confirmation
                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(),
                        "eu-west-1:cc3b47ba-5468-4f32-83c1-c03bc223a378", // Identity pool ID
                        Regions.EU_WEST_1 // Region
                );
                Log.i("TEST",credentialsProvider.getCredentials().toString());

                // Create an S3 client
                AmazonS3Client s3Client = new AmazonS3Client(credentialsProvider);
                s3Client.setEndpoint("s3.eu-west-1.amazonaws.com");
                S3Object object = s3Client.getObject("mobile-face-recognition-bucket","ryan_front.JPG");
                final String ryanURL = s3Client.getResourceUrl("mobile-face-recognition-bucket","ryan_front.JPG");
                Log.d("TEST", object.toString());
                Log.d("TEST", ryanURL);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getBaseContext())
                        .load(ryanURL)
                        .centerCrop()
                        .into(imageView);
                    }
                });

            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public void startRetrieval(View view){
        new s3Setup().execute();
    }


}
