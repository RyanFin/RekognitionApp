package io.github.ryanfin.facerekognitionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class ResponsePopUp extends Activity {

    public static TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.8));

        //Get Intent details
        Intent intent = getIntent();
        String messageText = intent.getStringExtra("POPUPRESPONSE");
        //Initialise views
        responseText = (TextView) findViewById(R.id.responsetext);
        responseText.setText(messageText);

    }
}
