package com.example.luisalvarez.bagstar.data;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Config {
    public static final URL BASE_URL;
    private static String TAG = Config.class.toString();

    //get url to be parsed to json, called in RemoteEndpointUtil
    static {
        URL url = null;
        try {
            //json info
            url = new URL("https://api.myjson.com/bins/94jyp" );
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
            Log.e(TAG, "Please check your internet connection.");
        }

        BASE_URL = url;
    }
}