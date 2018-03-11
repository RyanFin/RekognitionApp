package io.github.ryanfin.facerekognitionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class LocalImageRecognitionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_image_recognition);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
    }
}
