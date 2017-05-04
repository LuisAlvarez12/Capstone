package com.example.luisalvarez.bagstar.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.luisalvarez.bagstar.R;
import com.example.luisalvarez.bagstar.data.DataContract;
import com.example.luisalvarez.bagstar.data.RemoteEndpointUtil;
import com.example.luisalvarez.bagstar.adapter.WorkoutAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {


    private WorkoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        ButterKnife.bind(this);
            Syncr s = new Syncr();
            s.execute();


    }

    public class Syncr extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Cursor cursor = getContentResolver().query(DataContract.ProfileEntry.CONTENT_URI,
                    DataContract.ProfileEntry.projection,null,null,null);
            for(int i = 0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                for(int j = 0;j<cursor.getColumnCount();j++){
                    Log.d("columns",cursor.getColumnName(j) + " : "+cursor.getString(j));
                }
            }

            cursor = getContentResolver().query(DataContract.WorkoutsEntry.CONTENT_URI,
                    DataContract.WorkoutsEntry.projection,null,null,null);
            for(int i = 0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                for(int j = 0;j<cursor.getColumnCount();j++){
                    Log.d("columns",cursor.getColumnName(j)+ " : "+cursor.getString(j));
                }
            }
            adapter = new WorkoutAdapter(MainActivity.this,cursor,R.layout.card_workout_item);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rycycler_workout_list);
            recyclerView.setAdapter(adapter);




        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //fetch data
                //uri leading to the directory of items
                Uri dirUri = DataContract.ProfileEntry.CONTENT_URI;

                // Delete all items
                JSONArray array = RemoteEndpointUtil.fetchJsonArray(0);
                if (array == null) {
                    throw new JSONException("Invalid parsed item array" );
                }
                //add to sqldb
                for (int i = 0; i < array.length(); i++) {
                    ContentValues values = new ContentValues();
                    JSONObject object = array.getJSONObject(i);
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_AGE, object.getString("age" ));
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_HEIGHT, object.getString("height" ));
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_LAST_WORKOUT, object.getString("lastday" ));
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_NAME, object.getString("name" ));
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_QUOTE, object.getString("quote" ));
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_WEIGHT, object.getString("weight" ));
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_STREAK, object.getString("streak" ));
                    values.put(DataContract.ProfileEntry.COLUMN_PROFILE_ID, object.getString("id" ));

                    //add to arraylist
                    //ContentProviderOperation allows a batch insert for data integrity reasons
//                  cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                    getContentResolver().insert(DataContract.ProfileEntry.CONTENT_URI, values);

                }

                 array = RemoteEndpointUtil.fetchJsonArray(1);
                if (array == null) {
                    throw new JSONException("Invalid parsed item array" );
                }
                //add to sqldb
                for (int i = 0; i < array.length(); i++) {
                    ContentValues values = new ContentValues();
                    JSONObject object = array.getJSONObject(i);
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_NAME, object.getString("workoutName" ));
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_DATE, object.getString("workoutDate" ));
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_ID, object.getString("id" ));
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_CAL_BURNED_START, object.getString("workoutCalStart" ));
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_CAL_BURNED_END, object.getString("workoutCalEnd" ));
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_CUSTOM_WORKOUT, object.getString("workoutCustom" ));
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_IMG_LINK, object.getString("workoutImageLink" ));
                    values.put(DataContract.WorkoutsEntry.COLUMN_WORKOUT_TIME, object.getString("workoutTime" ));



                    //add to arraylist
                    //ContentProviderOperation allows a batch insert for data integrity reasons
//                  cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                    getContentResolver().insert(DataContract.WorkoutsEntry.CONTENT_URI, values);

                }
                //itemsProvider


            } catch (JSONException e){
                Log.e("name", "Error updating content.", e);
            }
            return null;}
    }




    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
