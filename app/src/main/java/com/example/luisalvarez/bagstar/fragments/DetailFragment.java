package com.example.luisalvarez.bagstar.fragments;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisalvarez.bagstar.R;
import com.example.luisalvarez.bagstar.adapter.MoveCountGridAdapter;
import com.example.luisalvarez.bagstar.data.Config;
import com.example.luisalvarez.bagstar.data.DataContract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.LogRecord;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.bitmap;


public class DetailFragment extends Fragment {

    @BindView(R.id.tv_time)
    TextView vElapstedTime;

    @BindView(R.id.tv_calories)
    TextView vCaloriesBurned;

    private ImageView vWorkoutControl;
    private RecyclerView vRecyclerView;
    private boolean isPlaying;
    private int counter_rightHook,counter_leftHook,counter_rightJab,
            counter_leftJab,counter_rightUpperCut,counter_leftUpperCut,
            counter_rightKnee,counter_leftKnee,counter_stepBack,
            counter_dodgeLeft,counter_dodgeRight, counter_rightKick,counter_leftKick = 0;
    private List<Integer> list_occuringCount;
    private List<Integer> list_currentCount;
    private MediaPlayer mediaPlayer = null;


    public DetailFragment() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this,rootView);
        vWorkoutControl = (ImageView)rootView.findViewById(R.id.btn_workout_control);
        vRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler);
        vRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        String recievedIntent = getActivity().getIntent().getStringExtra("id");
        Cursor cursor = getActivity().getContentResolver().query(DataContract.WorkoutsEntry.CONTENT_URI,
                DataContract.WorkoutsEntry.projection,"workoutID=?",new String[]{recievedIntent},null);
        cursor.moveToFirst();
        instantiateMoveOccuranceRate(cursor.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_MOVE_COUNTER));
        instantiateCurrentMoveCounter();
        MoveCountGridAdapter gridAdapter = new MoveCountGridAdapter(getActivity(),R.layout.movecount_grid_item, list_currentCount);
        vRecyclerView.setAdapter(gridAdapter);
        counter_rightHook = 60;
        list_currentCount.set(0,counter_rightHook);
        gridAdapter.swapItems(list_currentCount);
        instantiateTouchEvents();
        return rootView;
    }

    //the list that determines the occurances of every move
    private void instantiateMoveOccuranceRate(String s){
        list_occuringCount = new ArrayList<Integer>();
        String[] strArray = s.split(",");
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        for (int index = 0; index < intArray.length; index++)
        {
            list_occuringCount.add(intArray[index]);
        }
    }


    //the list that holds the current count of moves as time goes on
    @NonNull
    private List<Integer> instantiateCurrentMoveCounter() {
        list_currentCount=new ArrayList<Integer>();
        list_currentCount.add(counter_rightHook);
        list_currentCount.add(counter_leftHook);
        list_currentCount.add(counter_rightJab);
        list_currentCount.add(counter_leftJab);
        list_currentCount.add(counter_rightUpperCut);
        list_currentCount.add(counter_leftUpperCut);
        list_currentCount.add(counter_rightKnee);
        list_currentCount.add(counter_leftKnee);
        list_currentCount.add(counter_stepBack);
        list_currentCount.add(counter_dodgeLeft);
        list_currentCount.add(counter_dodgeRight);
        list_currentCount.add(counter_rightKick);
        list_currentCount.add(counter_leftKick);
        return list_currentCount;
    }

    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    //media playback and touch events
    private void instantiateTouchEvents() {
        isPlaying=false;
        vWorkoutControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){

                }else if(!isPlaying){
                    mediaPlayer = new MediaPlayer();
                    String fileName = Config.MOVE_RIGHT_JAB;
                    try {
                        AssetFileDescriptor afd = getActivity().getAssets().openFd(fileName);
                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        afd.close();
                        mediaPlayer.prepare();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            try {
                                mediaPlayer.reset();
                                AssetFileDescriptor afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                                afd.close();
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    mediaPlayer.start();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }







}
