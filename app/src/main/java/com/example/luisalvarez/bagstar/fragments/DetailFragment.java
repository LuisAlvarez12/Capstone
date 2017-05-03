package com.example.luisalvarez.bagstar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisalvarez.bagstar.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView vRecyclerView;

    @BindView(R.id.tv_time)
    TextView vElapstedTime;

    @BindView(R.id.tv_calories)
    TextView vCaloriesBurned;

    @BindView(R.id.btn_workout_control)
    ImageView vWorkoutControl;



    public DetailFragment() {

    }

     public static DetailFragment newInstance(String param1, String param2) {
      DetailFragment fragment = new DetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
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
        ButterKnife.bind(getActivity(),rootView);
        instantiateTouchEvents();

        return rootView;
    }

    private void instantiateTouchEvents() {

    }

}
