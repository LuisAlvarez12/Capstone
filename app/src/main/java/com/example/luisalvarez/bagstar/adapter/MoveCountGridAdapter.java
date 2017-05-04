package com.example.luisalvarez.bagstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisalvarez.bagstar.R;
import com.example.luisalvarez.bagstar.activities.DetailActivity;
import com.example.luisalvarez.bagstar.data.DataContract;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by luisalvarez on 5/3/17.
 */

public class MoveCountGridAdapter extends RecyclerView.Adapter<MoveCountGridAdapter.ViewHolder>{

    private Cursor items;
    private Context context;
    private int itemLayout;
    private List<Integer> counter_list;

    public MoveCountGridAdapter(Context activity, Cursor items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = activity;
    }

    public MoveCountGridAdapter(Context activity, int itemLayout, List<Integer> list) {
//        this.items = items;
        this.itemLayout = itemLayout;
        this.context = activity;
        this.counter_list = list;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }
    interface StockAdapterOnClickHandler {
        void onClick(String symbol);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        items.moveToPosition(position);
        holder.count.setText(""+counter_list.get(position));

    }

    public void swapItems(List< Integer > list){
        this.counter_list = list;
        notifyDataSetChanged();
        Log.d("name","passed");

    }


    @Override public int getItemCount() {
        return counter_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            count = (TextView)itemView.findViewById(R.id.tv_move_counter);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

        }
    }
}