package io.github.ryanfin.facerekognitionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.Image;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class LocalImageRecognitionActivity extends Activity {

    static AmazonRekognition client = null;
    Image myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_image_recognition);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        new CredRetriever().execute();

    }

    public void onLocalRecognitionButtonClick(View view){
        for (int i = 0; i <10; i++){
            new localRecognitionThread().execute();
        }
        //new localRecognitionThread().execute();
    }

    class CredRetriever extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    "eu-west-1:cc3b47ba-5468-4f32-83c1-c03bc223a378", // Identity pool ID
                    Regions.EU_WEST_1 // Region
            );
            Log.i("TEST",credentialsProvider.getCredentials().toString());
            client = new AmazonRekognitionClient(credentialsProvider);
            return null;
        }
    }

    class localRecognitionThread extends AsyncTask<Void,Void,Void>{

        Handler handler = new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //instantiate the View
        }

        @Override
        protected Void doInBackground(Void... in) {

            Drawable myDrawable = getResources().getDrawable(R.drawable.img5);

            Bitmap bitmap = ((BitmapDrawable)myDrawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);

            ByteBuffer imageBytes = ByteBuffer.wrap(stream.toByteArray());
            myImage = new Image();
            myImage.withBytes(imageBytes);
            DetectFacesRequest request = new DetectFacesRequest()
                    .withAttributes(Attribute.ALL.toString())
                    .withImage(myImage);
            final DetectFacesResult result = client.detectFaces(request);
            Log.d("RYANRESULT", result.toString());

            //update the TextView in the main thread
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    celebResultTextView.setText(result.toString());
//                }
//            });

            result.getFaceDetails();
            return null;
        }
    }


}
