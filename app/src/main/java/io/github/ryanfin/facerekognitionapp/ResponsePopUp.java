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
    TextView smileValueTv, eyeGlassesValueTv;
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

        getWindow().setLayout((int)(width * 0.81), (int)(height * 0.81));

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

            }
            //Toast.makeText(this, "Json: "+temp, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }
}
