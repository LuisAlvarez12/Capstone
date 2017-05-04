package com.example.luisalvarez.bagstar.fragments;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisalvarez.bagstar.R;
import com.example.luisalvarez.bagstar.adapter.MoveCountGridAdapter;
import com.example.luisalvarez.bagstar.data.Config;
import com.example.luisalvarez.bagstar.data.DataContract;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    public DetailFragment() {

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
        List<Integer> counter_list = new ArrayList<>();
        counter_list.add(counter_rightHook);
        counter_list.add(counter_leftHook);
        counter_list.add(counter_rightJab);
        counter_list.add(counter_leftJab);
        counter_list.add(counter_rightUpperCut);
        counter_list.add(counter_leftUpperCut);
        counter_list.add(counter_rightKnee);
        counter_list.add(counter_leftKnee);
        counter_list.add(counter_stepBack);
        counter_list.add(counter_dodgeLeft);
        counter_list.add(counter_dodgeRight);
        counter_list.add(counter_rightKick);
        counter_list.add(counter_leftKick);

        MoveCountGridAdapter gridAdapter = new MoveCountGridAdapter(getActivity(),R.layout.movecount_grid_item,counter_list);
        vRecyclerView.setAdapter(gridAdapter);
        counter_rightHook = 60;
        counter_list.set(0,counter_rightHook);
        Log.d("name",""+counter_list.get(0));
        gridAdapter.swapItems(counter_list);
        String recievedIntent = getActivity().getIntent().getStringExtra("id");
        Cursor cursor = getActivity().getContentResolver().query(DataContract.WorkoutsEntry.CONTENT_URI,
                DataContract.WorkoutsEntry.projection,"workoutID=?",new String[]{recievedIntent},null);
        cursor.moveToFirst();
        instantiateTouchEvents();
        return rootView;
    }

    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }



    private void instantiateTouchEvents() {
        isPlaying=false;
        vWorkoutControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){

                }else if(!isPlaying){
                    MediaPlayer mediaPlayer = null;
                    mediaPlayer = new MediaPlayer();

                    String fileName = Config.MOVE_RIGHT_JAB;
                    try {
                        AssetFileDescriptor afd = getActivity().getAssets().openFd(fileName);
                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        afd.close();
                        mediaPlayer.prepare();
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                }
            }
        });
    }





}
