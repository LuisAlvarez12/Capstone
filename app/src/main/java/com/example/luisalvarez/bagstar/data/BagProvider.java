package com.example.luisalvarez.bagstar.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by luisalvarez on 2/11/17.
 */


public class BagProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = createUriBuilder();
    private BagHelperDb mOpenHelper;
    private static final int WORKOUT_LIST = 100;
    private static final int WORKOUT_ID = 101;
    private static final int PROFILE_LIST = 106;
    private static final int PROFILE_ID = 107;

    @Override
    public boolean onCreate() {
        mOpenHelper = new BagHelperDb(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Cursor retCursor;
        long _id;
        switch (sUriMatcher.match(uri)){
            case PROFILE_LIST:
                retCursor = db.query(
                        DataContract.ProfileEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PROFILE_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        DataContract.ProfileEntry.TABLE_NAME,
                        projection,
                        DataContract.ProfileEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            case WORKOUT_LIST:
                retCursor = db.query(
                        DataContract.WorkoutsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case WORKOUT_ID:
                _id = ContentUris.parseId(uri);
                retCursor = db.query(
                        DataContract.WorkoutsEntry.TABLE_NAME,
                        projection,
                        DataContract.WorkoutsEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case PROFILE_LIST:
                return DataContract.ProfileEntry.CONTENT_TYPE;
            case PROFILE_ID:
                return DataContract.ProfileEntry.CONTENT_ITEM_TYPE;
            case WORKOUT_LIST:
                return DataContract.WorkoutsEntry.CONTENT_TYPE;
            case WORKOUT_ID:
                return DataContract.WorkoutsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " +uri);
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long _id;
        Uri returnUri;
        switch (sUriMatcher.match(uri)){
            case PROFILE_LIST:
                _id = db.insert(DataContract.ProfileEntry.TABLE_NAME,null,values);
                if(_id > 0){
                    returnUri =  DataContract.ProfileEntry.buildProfileUri(_id);
                    getContext().getContentResolver().notifyChange(uri,null);
                    return returnUri;
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
            case PROFILE_ID:
                _id = db.insert(DataContract.ProfileEntry.TABLE_NAME,null,values);
                if(_id > 0){
                    returnUri =  DataContract.ProfileEntry.buildProfileUri(_id);
                    getContext().getContentResolver().notifyChange(uri,null);
                    return returnUri;
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
            case WORKOUT_LIST:
                _id = db.insert(DataContract.WorkoutsEntry.TABLE_NAME,null,values);
                if(_id > 0){
                    returnUri =  DataContract.WorkoutsEntry.buildMovieUri(_id);
                    getContext().getContentResolver().notifyChange(uri,null);
                    return returnUri;
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
            case WORKOUT_ID:
                _id = db.insert(DataContract.WorkoutsEntry.TABLE_NAME,null,values);
                if(_id > 0){
                    returnUri =  DataContract.WorkoutsEntry.buildMovieUri(_id);
                    getContext().getContentResolver().notifyChange(uri,null);
                    return returnUri;
                } else{
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows=-1;
        switch (sUriMatcher.match(uri)){
            case PROFILE_LIST:
                rows = db.delete(DataContract.ProfileEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case PROFILE_ID:
                rows = db.delete(DataContract.ProfileEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case WORKOUT_ID:
                rows = db.delete(DataContract.WorkoutsEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case WORKOUT_LIST:
                rows = db.delete(DataContract.WorkoutsEntry.TABLE_NAME,selection,selectionArgs);
                break;
        }
        // Because null could delete all rows:
//        if(selection == null || rows != 0){
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rows=-1;
        switch (sUriMatcher.match(uri)){
            case PROFILE_LIST:
                rows = db.update(DataContract.ProfileEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            case PROFILE_ID:
                rows = db.update(DataContract.ProfileEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            case WORKOUT_LIST:
                rows = db.update(DataContract.WorkoutsEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            case WORKOUT_ID:
                rows = db.update(DataContract.WorkoutsEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
        }
        // Because null could delete all rows:
//        if(selection == null || rows != 0){
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
        return rows;
    }

    public static UriMatcher createUriBuilder(){
        String content = DataContract.uriAUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, DataContract.sWorkouts, WORKOUT_LIST);
        matcher.addURI(content, DataContract.sWorkouts + "/#", WORKOUT_ID);
        matcher.addURI(content, DataContract.sProfile, PROFILE_LIST);
        matcher.addURI(content, DataContract.sProfile + "/#", PROFILE_ID);


//        matcher.addURI(content, DataContract.PATH_MOVIE, MOVIE);
//        matcher.addURI(content, DataContract.PATH_MOVIE + "/#", MOVIE_ID);
        return matcher;
    }
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount;
        switch (match) {
            case PROFILE_LIST:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DataContract.ProfileEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case WORKOUT_LIST:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DataContract.WorkoutsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
