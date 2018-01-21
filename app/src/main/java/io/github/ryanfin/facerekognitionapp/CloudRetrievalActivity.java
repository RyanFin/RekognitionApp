package io.github.ryanfin.facerekognitionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.InvalidS3ObjectException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;

import io.github.ryanfin.facerekognitionapp.Fragments.CloudFaceStatsFragment;

public class CloudRetrievalActivity extends Activity {

    Button downloadButton;
    AmazonRekognition rekognitionClient= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_retrieval);

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

        Handler handler = new Handler(); //for main thread
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
                rekognitionClient = new AmazonRekognitionClient(credentialsProvider);
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

    class detectFacesTask extends AsyncTask<Void,Void,Void>{

        Handler handler = new Handler();
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("S3THREAD", "S3 thread running...");
            try {
                DetectFacesRequest request = new DetectFacesRequest()
                        .withAttributes(Attribute.ALL.toString())
                        .withImage(new Image().withS3Object(new com.amazonaws.services.rekognition.model.S3Object().withName("ryan_front.JPG").withBucket("mobile-face-recognition-bucket")));
                rekognitionClient.setEndpoint("https://rekognition.eu-west-1.amazonaws.com"); //get rekognition stats
                DetectFacesResult result  = rekognitionClient.detectFaces(request);
                Log.d("S3THREAD", result.toString());
                //use Bundle to pass data to Fragment like intent.putExtra()
                final Bundle bundle = new Bundle();
                bundle.putString("cloud_stats", result.toString());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Place new Fragment
                        Fragment statsFrag = new CloudFaceStatsFragment();
                        statsFrag.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout, statsFrag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.commit();
                    }
                });

            } catch (InvalidS3ObjectException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public void startRetrieval(View view){
        Toast.makeText(getApplicationContext(), "Fetching image and stats from AWS...",
                Toast.LENGTH_SHORT).show();
        new s3Setup().execute();
        new detectFacesTask().execute();
    }


}
