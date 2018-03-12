package io.github.ryanfin.facerekognitionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponsePopUp extends Activity {

    public static TextView responseText;
    TextView smileValueTv, eyeGlassesValueTv, genderValueTv, beardValueTv, mustacheValueTv, eyesOpenValueTv, mouthOpenValueTv, picQualityValueTV;
    JSONObject jsonResponse;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.815), (int)(height * 0.81));

        //Get Intent details
        Intent intent = getIntent();
        //JSON data
        String messageText = intent.getStringExtra("POPUPRESPONSE");
        //Initialise views
        responseText = (TextView) findViewById(R.id.responsetext);
        responseText.setText(messageText);
        Log.d("DETECTFACETHREAD", "onCreate: "+ messageText);

        //Populate the Views for Face Features
        smileValueTv = (TextView) findViewById(R.id.smile_value);
        eyeGlassesValueTv = (TextView) findViewById(R.id.eyeglasses_value);
        genderValueTv = (TextView) findViewById(R.id.gender_value);
        beardValueTv = (TextView) findViewById(R.id.beard_value);
        mustacheValueTv = (TextView) findViewById(R.id.mustache_value);
        eyesOpenValueTv = (TextView) findViewById(R.id.eyes_open_value);
        mouthOpenValueTv = (TextView) findViewById(R.id.mouth_open_value);
        picQualityValueTV = (TextView) findViewById(R.id.pic_quality_value);


        try {
            //ArrayList<String> temp = new ArrayList<String>();
            jsonResponse = new JSONObject(messageText);
            JSONArray faces = jsonResponse.getJSONArray("FaceDetails");
            for(int i=0;i<faces.length();i++){
                JSONObject face = faces.getJSONObject(i);
//                String smile = face.get("Smile").toString();
//                Log.d("DETECTFACETHREAD", "onCreate: "+ smile);

                String smile = face.get("Smile").toString();
                smileValueTv.setText(smile);
                String eyeGlasses = face.get("Eyeglasses").toString();
                eyeGlassesValueTv.setText(eyeGlasses);
                String gender = face.get("Gender").toString();
                genderValueTv.setText(gender);
                String mustache = face.get("Mustache").toString();
                mustacheValueTv.setText(mustache);
                String beard = face.get("Beard").toString();
                beardValueTv.setText(beard);
                String eyesOpen = face.get("EyesOpen").toString();
                eyesOpenValueTv.setText(eyesOpen);
                String mouthOpen = face.get("MouthOpen").toString();
                mouthOpenValueTv.setText(mouthOpen);
                String picQuality = face.get("Quality").toString();
                picQualityValueTV.setText(picQuality);


            }
            //Toast.makeText(this, "Json: "+temp, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }
}
