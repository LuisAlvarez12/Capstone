package com.example.luisalvarez.bagstar.data;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class Config {
    public static final URL PROFILE_URL;
    public static final URL WORKOUT_URL;
    public static final String MOVE_RIGHT_HOOK = "right_hook.mp3";
    public static final String MOVE_LEFT_HOOK = "left_hook.mp3";
    public static final String MOVE_RIGHT_JAB = "right_jab.mp3";
    public static final String MOVE_LEFT_JAB = "left_jab.mp3";
    public static final String MOVE_RIGHT_UPPERCUT = "right_uppercut.mp3";
    public static final String MOVE_LEFT_UPPERCUT = "left_uppercut.mp3";
    public static final String MOVE_RIGHT_KNEE = "right_knee.mp3";
    public static final String MOVE_LEFT_KNEE = "left_knee.mp3";
    public static final String MOVE_STEP_BACK = "step_back.mp3";
    public static final String MOVE_DODGE_LEFT = "dodge_left.mp3";
    public static final String MOVE_DODGE_RIGHT = "dodge_right.mp3";
    public static final String MOVE_RIGHT_KICK = "right_kick.mp3";
    public static final String MOVE_LEFT_KICK = "left_kick.mp3";

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

        PROFILE_URL = url;
    }

    //get url to be parsed to json, called in RemoteEndpointUtil
    static {
        URL url = null;
        try {
            //json info
            url = new URL("https://api.myjson.com/bins/snq31" );
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
            Log.e(TAG, "Please check your internet connection.");
        }

        WORKOUT_URL = url;
    }
}
