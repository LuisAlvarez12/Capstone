package com.example.luisalvarez.bagstar.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luisalvarez on 2/11/17.
 */

public class BagHelperDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bagstar.db";

    public BagHelperDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_PROFILE_TABLE = "CREATE TABLE " + DataContract.ProfileEntry.TABLE_NAME + " (" +
                DataContract.ProfileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_NAME + " TEXT NOT NULL, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_AGE + " TEXT NOT NULL, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_HEIGHT + " TEXT NOT NULL, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_WEIGHT + " TEXT NOT NULL, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_STREAK + " TEXT NOT NULL, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_LAST_WORKOUT + " TEXT NOT NULL, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_QUOTE + " TEXT NOT NULL, " +
                DataContract.ProfileEntry.COLUMN_PROFILE_ID + " TEXT NOT NULL" +
                ");";
        final String CREATE_WORKOUT_TABLE = "CREATE TABLE " + DataContract.WorkoutsEntry.TABLE_NAME + " (" +
                DataContract.WorkoutsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_NAME + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_TIME + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_CUSTOM_WORKOUT + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_DATE + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_CAL_BURNED_START + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_CAL_BURNED_END + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_IMG_LINK + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_ID + " TEXT NOT NULL, " +
                DataContract.WorkoutsEntry.COLUMN_WORKOUT_MOVE_ARRAY + " TEXT NOT NULL" +
                ");";
        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_PROFILE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.ProfileEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.WorkoutsEntry.TABLE_NAME);
        onCreate(db);
    }
}
