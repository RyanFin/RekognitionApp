package io.github.ryanfin.facerekognitionapp.AuxiliaryFiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ryan.F on 21/01/2018.
 */

public class RekognitionDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "rekognition";
    private static final int DB_VERSION = 1;

    public RekognitionDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PHOTO("
        + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        + "IMAGE_RESOURCE_ID BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
