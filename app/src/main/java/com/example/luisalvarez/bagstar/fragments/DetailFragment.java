package com.example.luisalvarez.bagstar.fragments;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
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
    private Chronometer timer;
    private boolean isPlaying;
    private int counter_rightHook, counter_leftHook, counter_rightJab,
            counter_leftJab, counter_rightUpperCut, counter_leftUpperCut,
            counter_rightKnee, counter_leftKnee, counter_stepBack,
            counter_dodgeLeft, counter_dodgeRight, counter_rightKick, counter_leftKick = 0;
    private List<Integer> list_occuringCount;
    private List<Integer> list_currentCount;
    private MediaPlayer mediaPlayer = null;
    private MoveCountGridAdapter gridAdapter;
    private AssetFileDescriptor afd;
    private long timeWhenStopped = 0;

    public DetailFragment() {

    }

    @Override
    public void onDestroyView() {
        Log.d("count","dead");
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer=null;
        afd=null;
        super.onDestroyView();
            

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
        ButterKnife.bind(this, rootView);
        vWorkoutControl = (ImageView) rootView.findViewById(R.id.btn_workout_control);
        vRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        vRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        timer = (Chronometer)rootView.findViewById(R.id.tv_time);
        mediaPlayer = new MediaPlayer();
        String recievedIntent = getActivity().getIntent().getStringExtra("id");
        Cursor cursor = getActivity().getContentResolver().query(DataContract.WorkoutsEntry.CONTENT_URI,
                DataContract.WorkoutsEntry.projection, "workoutID=?", new String[]{recievedIntent}, null);
        cursor.moveToFirst();
        instantiateMoveOccuranceRate(cursor.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_MOVE_COUNTER));
        instantiateCurrentMoveCounter();
        gridAdapter = new MoveCountGridAdapter(getActivity(), R.layout.movecount_grid_item, list_currentCount);
        vRecyclerView.setAdapter(gridAdapter);
        instantiateTouchEvents();
        return rootView;
    }

    //the list that determines the occurances of every move
    private void instantiateMoveOccuranceRate(String s) {
        list_occuringCount = new ArrayList<Integer>();
        String[] strArray = s.split(",");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        for (int index = 0; index < intArray.length; index++) {
            list_occuringCount.add(intArray[index]);
        }
    }

    private void incrementCounterView(int index,int move) {
        list_currentCount.set(index,move);
        gridAdapter.swapItems(list_currentCount);
    }

    //the list that holds the current count of moves as time goes on
    @NonNull
    private List<Integer> instantiateCurrentMoveCounter() {
        list_currentCount = new ArrayList<Integer>();
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

    //media playback and touch events
    private void instantiateTouchEvents() {
        isPlaying = false;
        vWorkoutControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
                    timer.stop();
                    vWorkoutControl.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play));
                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    isPlaying = false;
                } else if (!isPlaying) {
                    timer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    timer.start();
                    vWorkoutControl.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pause));
                    String fileName = Config.MOVE_RIGHT_JAB;
                    try {
                        afd = getActivity().getAssets().openFd(fileName);
                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        afd.close();
                        mediaPlayer.prepare();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                if (!isPlaying) {
                                    mediaPlayer.stop();
                                    mediaPlayer.reset();
                                } else {
                                    mediaPlayer.stop();
                                    try {
                                        mediaPlayer.reset();
                                        int selectedIndex = new Random().nextInt(list_occuringCount.size());
                                        int itemInList = list_occuringCount.get(selectedIndex);
                                        AssetFileDescriptor afd = null;

                                        switch (itemInList) {
                                            //right hook
                                            case 0:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_rightHook);
                                                break;
                                            //left hook
                                            case 1:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_leftHook);
                                                break;
                                            //right jab
                                            case 2:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_rightJab);
                                                break;
                                            //left jab
                                            case 3:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_leftJab);
                                                break;
                                            //right uppercut
                                            case 4:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_rightUpperCut);
                                                break;
                                            //left uppercut
                                            case 5:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_leftUpperCut);
                                                break;
                                            //right knee
                                            case 6:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_rightKnee);
                                                break;
                                            //left knee
                                            case 7:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_leftKnee);
                                                break;
                                            //step back
                                            case 8:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_stepBack);
                                                break;
                                            //dodge left
                                            case 9:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_dodgeLeft);
                                                break;
                                            //dodge right
                                            case 10:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_dodgeRight);
                                                break;
                                            //right kick
                                            case 11:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_rightKick);
                                                break;
                                            //left kick
                                            case 12:
                                                afd = getActivity().getAssets().openFd(Config.MOVE_RIGHT_JAB);
                                                incrementCounterView(itemInList,++counter_leftKick);
                                                break;
                                        }
                                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                                        afd.close();
                                        mediaPlayer.prepare();
                                        mediaPlayer.start();
                                        isPlaying = true;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        mediaPlayer.start();
                        isPlaying = true;
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
