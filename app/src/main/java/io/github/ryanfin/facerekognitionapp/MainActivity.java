package io.github.ryanfin.facerekognitionapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.example.DetectLabelsExample;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class MainActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static AmazonRekognition client = null;
    Image searchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(this).execute();
        //call
        new CredRetriever().execute();
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

    public void startCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            ByteBuffer imageBytes = ByteBuffer.wrap(stream.toByteArray());
            searchImage = new Image();
            searchImage.withBytes(imageBytes);

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);

            new detectFaceThread().execute();

        }
    }

    class detectFaceThread extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... in) {
            Log.d("DETECTFACETHREAD", "Face thread running...");
            DetectFacesRequest request = new DetectFacesRequest()
                    .withAttributes(Attribute.ALL.toString())
                    .withImage(searchImage);
            DetectFacesResult result = client.detectFaces(request);
            Log.d("DETECTFACETHREAD", result.toString());
            result.getFaceDetails();
            return null;
        }
    }




}
