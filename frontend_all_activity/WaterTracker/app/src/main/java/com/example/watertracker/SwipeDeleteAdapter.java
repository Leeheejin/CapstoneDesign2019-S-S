package com.example.watertracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SwipeDeleteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class SwipeHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvName;

        SwipeHolder(View view){
            super(view);
            ivPicture = view.findViewById(R.id.iv_picture);
            tvName = view.findViewById(R.id.tvName);
        }
    }



    private ArrayList<CupInfo> cupInfoArrayList;
    SwipeDeleteAdapter(ArrayList<CupInfo> cupInfoArrayList){
        this.cupInfoArrayList = cupInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);

        return new MyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyAdapter.MyViewHolder myViewHolder = (MyAdapter.MyViewHolder) holder;

        myViewHolder.ivPicture.setImageResource(cupInfoArrayList.get(position).drawableId);
        myViewHolder.tvName.setText(cupInfoArrayList.get(position).cupname);
    }

    @Override
    public int getItemCount() {
        return cupInfoArrayList.size();
    }



    public void remove(int position) {
        cupInfoArrayList.remove(position);
        notifyItemRemoved(position);
    }
}
