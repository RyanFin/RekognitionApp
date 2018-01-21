package io.github.ryanfin.facerekognitionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Blob;

import io.github.ryanfin.facerekognitionapp.AuxiliaryFiles.DbBitmapUtility;
import io.github.ryanfin.facerekognitionapp.AuxiliaryFiles.RekognitionDatabaseHelper;

public class ImageGalleryActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        imageView = (ImageView) findViewById(R.id.gallery_iv);

    }

    public void onGalleryButtonClick(View v) {
        Cursor c = MainActivity.db.rawQuery("select * from photo", null);
        if(c.moveToNext()){
            byte[] image = c.getBlob(0);
            Bitmap bmp = DbBitmapUtility.getImage(image);
            imageView.setImageBitmap(bmp);
            Toast.makeText(this, "select success", Toast.LENGTH_SHORT);
        }

    }
}
