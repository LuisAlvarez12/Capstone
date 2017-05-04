package com.example.luisalvarez.bagstar.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisalvarez.bagstar.R;
import com.example.luisalvarez.bagstar.activities.DetailActivity;
import com.example.luisalvarez.bagstar.data.DataContract;
import com.squareup.picasso.Picasso;

/**
 * Created by luisalvarez on 5/3/17.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder>{

    private Cursor items;
    private Context context;
    private int itemLayout;

    public WorkoutAdapter(Context activity,Cursor items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = activity;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }
    interface StockAdapterOnClickHandler {
        void onClick(String symbol);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);
        holder.title.setText(items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_NAME));
        holder.date.setText(items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_DATE));
        holder.calories.setText(items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_CAL_BURNED_START) + " - "
        +items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_CAL_BURNED_END));
        holder.time.setText(items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_TIME));
        Picasso.with(context)
                .load(items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_IMG_LINK))
                .error(context.getResources().getDrawable(R.drawable.error))
                .into(holder.workout_background);

    }

    @Override public int getItemCount() {
        return items.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public TextView time;
        public TextView calories;
        public TextView date;
        public ImageView workout_background;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_title);
            time = (TextView) itemView.findViewById(R.id.card_time);
            calories = (TextView) itemView.findViewById(R.id.card_calories);
            date = (TextView) itemView.findViewById(R.id.card_date);
            workout_background = (ImageView) itemView.findViewById(R.id.background);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            items.moveToPosition(adapterPosition);
            Intent toDetail = new Intent(context, DetailActivity.class);
            toDetail.putExtra("id",items.getString(DataContract.WorkoutsEntry.POSITION_WORKOUT_ID));
            context.startActivity(toDetail);
        }
    }
}