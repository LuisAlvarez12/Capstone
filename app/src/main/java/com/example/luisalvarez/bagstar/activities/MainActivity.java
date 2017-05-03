package com.example.luisalvarez.bagstar.activities;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.luisalvarez.bagstar.R;
import com.example.luisalvarez.bagstar.data.DataContract;
import com.example.luisalvarez.bagstar.data.RemoteEndpointUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            Syncr s = new Syncr();
            s.execute();


    }

    public class Syncr extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Cursor cursor = getContentResolver().query(DataContract.ProfileEntry.CONTENT_URI,
                    DataContract.ProfileEntry.projection,null,null,null);
            cursor.moveToFirst();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //fetch data
                //uri leading to the directory of items
                Uri dirUri = DataContract.ProfileEntry.CONTENT_URI;

                // Delete all items
                JSONArray array = RemoteEndpointUtil.fetchJsonArray();
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
