package io.github.ryanfin.facerekognitionapp;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.sql.Blob;

import io.github.ryanfin.facerekognitionapp.AuxiliaryFiles.DbBitmapUtility;
import io.github.ryanfin.facerekognitionapp.AuxiliaryFiles.RekognitionDatabaseHelper;
import io.github.ryanfin.facerekognitionapp.RecyclerViewConfig.ImageGalleryAdapter;
import io.github.ryanfin.facerekognitionapp.RecyclerViewConfig.ImageModel;

public class ImageGalleryActivity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

//        imageView = (ImageView) findViewById(R.id.gallery_iv);
//
//        //Use Glide image gallery to create an image Corpus
//        Glide.with(getApplicationContext())
//                .load("https://static.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg")
//                .into(imageView);
//        Glide.with(getApplicationContext())
//                .load("https://www.wallpaperink.co.uk/gallery/shutterstock/sunsets-beaches/Pink_Sunset.jpg")
//                .into(imageView);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ImageGalleryAdapter adapter = new ImageGalleryAdapter(this, ImageModel.getSpacePhotos());
        recyclerView.setAdapter(adapter);


    }

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
