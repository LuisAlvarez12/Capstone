package com.example.luisalvarez.bagstar.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by luisalvarez on 2/11/17.
 */

public class DataContract {


    public static final String uriAUTHORITY =
            "com.example.luisalvarez.bagstar";

    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" +
                    uriAUTHORITY);

    public static final String sWorkouts = "/workouts";
    public static final String sProfile = "/profile";



    public static final class WorkoutsEntry implements BaseColumns {

        // Content URI represents the base location for the table
        public static final String strContent = BASE_CONTENT_URI+ sWorkouts;
        public static final Uri CONTENT_URI =
                Uri.parse(strContent);

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + sWorkouts;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + sWorkouts;

        // Define the table schema
        public static final String TABLE_NAME = "favoritesTable";
        public static final String COLUMN_PROFILE_ID = "workoutID";
        public static final String COLUMN_WORKOUT_CAL_BURNED_START = "workoutCalStart";
        public static final String COLUMN_WORKOUT_CAL_BURNED_END = "workoutCalEnd";
        public static final String COLUMN_WORKOUT_CUSTOM_WORKOUT = "workoutCustom";
        public static final String COLUMN_WORKOUT_TIME = "workoutTime";
        public static final String COLUMN_WORKOUT_DATE = "workoutDate";
        public static final String COLUMN_WORKOUT_NAME = "workoutName";
        public static final String COLUMN_WORKOUT_IMG_LINK = "workoutImageLink";

        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class ProfileEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final String strContent = BASE_CONTENT_URI+ sProfile;
        public static final Uri CONTENT_URI =
                Uri.parse(strContent);

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + sProfile;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + sProfile;

        // Define the table schema
        public static final String TABLE_NAME = "upcomingTable";
        public static final String COLUMN_PROFILE_ID = "profileID";
        public static final String COLUMN_PROFILE_NAME = "profileName";
        public static final String COLUMN_PROFILE_AGE = "profileAge";
        public static final String COLUMN_PROFILE_WEIGHT = "profileWeight";
        public static final String COLUMN_PROFILE_HEIGHT = "profileHeight";
        public static final String COLUMN_PROFILE_STREAK = "profileStreak";
        public static final String COLUMN_PROFILE_LAST_WORKOUT = "profileLastWorkout";
        public static final String COLUMN_PROFILE_QUOTE = "profileQuote";


        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


}
