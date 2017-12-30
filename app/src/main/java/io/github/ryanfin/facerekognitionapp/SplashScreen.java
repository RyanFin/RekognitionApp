package io.github.ryanfin.facerekognitionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                try{
                    //1500 milliseconds -> 1.5 seconds
                    sleep(1500);
                    //Launch Login Activity after SplashScreen
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    //destroy
                    finish();

                } catch (InterruptedException e){
                    e.printStackTrace();

                }

            }
        };
        //call run() method
        myThread.start();
    }
}
