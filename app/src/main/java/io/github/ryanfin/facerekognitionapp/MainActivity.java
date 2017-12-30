package io.github.ryanfin.facerekognitionapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.InvalidS3ObjectException;
import com.amazonaws.services.rekognition.model.S3Object;
import com.example.DetectLabelsExample;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class MainActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static AmazonRekognition client = null;
    Image searchImage;
    Button showResponseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(this).execute();
        //call
        new CredRetriever().execute();

    }

    //Menu options


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);

        //Spinner selection
//        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch ((int)id){
//                    case 0:
//                        //Local Face Recognition Option Selected
//                        Log.d("FAce0", "onItemClick: 0");
//                        break;
//                    case 1:
//                        //Cloud Face Recognition Option Selected
//                        Log.d("Face1", "onItemClick: 1");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

        return super.onCreateOptionsMenu(menu);
    }

    //Background Threads
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

            new detectFaceThread().execute(); //Run local recognition task
            new detectLabelTask().execute(); //Run S3 recognition task

        }
    }

    class detectFaceThread extends AsyncTask<Void,Void,Void>{

        Handler handler = new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //instantiate the View

        }

        @Override
        protected Void doInBackground(Void... in) {
            Log.d("DETECTFACETHREAD", "Local face thread running...");
            DetectFacesRequest request = new DetectFacesRequest()
                    .withAttributes(Attribute.ALL.toString())
                    .withImage(searchImage);
            final DetectFacesResult result = client.detectFaces(request);
            Log.d("DETECTFACETHREAD", result.toString());

            //update the TextView in the main thread
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Local Face Image Received...",
                            Toast.LENGTH_SHORT).show();
                    showResponseButton = (Button) findViewById(R.id.showresponsebtn);
                    //On click
                    showResponseButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, ResponsePopUp.class);
                            intent.putExtra("POPUPRESPONSE", result.toString());
                            startActivity(intent);
                        }
                    });
                }
            });


            result.getFaceDetails();
            return null;
        }

    }

    class detectLabelTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("S3THREAD", "S3 thread running...");
            try {
                DetectFacesRequest request = new DetectFacesRequest()
                        .withAttributes(Attribute.ALL.toString())
                        .withImage(new Image().withS3Object(new S3Object().withName("ryan_front.JPG").withBucket("mobile-face-recognition-bucket")));
                client.setEndpoint("https://rekognition.eu-west-1.amazonaws.com");
                DetectFacesResult result = client.detectFaces(request);
                Log.d("S3THREAD", result.toString());

            } catch (InvalidS3ObjectException e){
                e.printStackTrace();
            }
            return null;
        }
    }




}
