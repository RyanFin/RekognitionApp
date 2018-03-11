package io.github.ryanfin.facerekognitionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

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

import io.github.ryanfin.facerekognitionapp.RecyclerViewConfig.ImageGalleryAdapter;
import io.github.ryanfin.facerekognitionapp.RecyclerViewConfig.ImageModel;

public class ImageGalleryActivity extends Activity {
    ImageView imageView;
    Drawable celebPic;
    Resources res;
    Image celebImage;
    static AmazonRekognition client = null;
    ImageModel[] imageModelArray = ImageModel.getSpacePhotos();
    public int celebPicIndex = 0; //Arnold at the start
    Spinner celebSpinner;
    TextView celebResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        new CredRetriever().execute();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, ImageModel.getSpacePhotos());
        recyclerView.setAdapter(adapter);

        res = getResources();
        celebPic = res.getDrawable(R.drawable.arnold_schwarzenegger);

        celebSpinner = (Spinner) findViewById(R.id.celeb_spinner);
        celebResultTextView = (TextView) findViewById(R.id.celeb_output_txt);

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

    public void onCelebResponseButtonClick(View view){
        celebPicIndex = celebSpinner.getSelectedItemPosition();
        Log.d("CELEBTHREAD", "onCelebResponseButtonClick: " + celebPicIndex);
//        for(int i = 0; i < 100; i++) {
//            new celebThread().execute();
//        }
        new celebThread().execute();
    }

    class celebThread extends AsyncTask<Void,Void,Void>{

        Handler handler = new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //instantiate the View
        }

        @Override
        protected Void doInBackground(Void... in) {

            celebPic = getResources().getDrawable(imageModelArray[celebPicIndex].getmDrawable());
//            String[] celebArray = getResources().getStringArray(R.array.celeb_array);
            Log.d("CELEBTHREAD", "celebThread thread running...");
//            Log.d("CELEBTHREAD", "doInBackground: " + celebArray[1]);
            Bitmap bitmap = ((BitmapDrawable)celebPic).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,25,stream);

            ByteBuffer imageBytes = ByteBuffer.wrap(stream.toByteArray());
            celebImage = new Image();
            celebImage.withBytes(imageBytes);
            DetectFacesRequest request = new DetectFacesRequest()
                    .withAttributes(Attribute.ALL.toString())
                    .withImage(celebImage);
            final DetectFacesResult result = client.detectFaces(request);
            Log.d("CELEBTHREAD", result.toString());

            //update the TextView in the main thread
            handler.post(new Runnable() {
                @Override
                public void run() {
                    celebResultTextView.setText(result.toString());
                }
            });

            result.getFaceDetails();
            return null;
        }
    }

//    public void imageSelected(int pos){
//        Log.d("IMAGESELECTED", "imageSelected: " + String.valueOf(pos));
//    }

//    public void onGalleryButtonClick(View v) {
//        Cursor c = MainActivity.db.rawQuery("select * from photo", null);
//        if(c.moveToNext()){
//            byte[] image = c.getBlob(0);
//            Bitmap bmp = DbBitmapUtility.getImage(image);
//            imageView.setImageBitmap(bmp);
//            Toast.makeText(this, "View Cached Gallery...", Toast.LENGTH_SHORT).show();
//        }
//    }
}
