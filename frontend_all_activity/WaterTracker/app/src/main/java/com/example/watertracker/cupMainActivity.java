package com.example.watertracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class cupMainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cup);

        final ArrayList<CupInfo> cupInfoArrayList = new ArrayList<>();
        cupInfoArrayList.add(new CupInfo(R.drawable.cup1, "나의 컵1"));
        cupInfoArrayList.add(new CupInfo(R.drawable.cup2, "나의 컵2"));
        cupInfoArrayList.add(new CupInfo(R.drawable.cup3, "나의 컵3"));
        cupInfoArrayList.add(new CupInfo(R.drawable.cup4, "나의 컵4"));
        cupInfoArrayList.add(new CupInfo(R.drawable.glass_water, "나의 컵5"));

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(cupInfoArrayList);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //mRecyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(divider);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {

                int position = target.getAdapterPosition();
                cupInfoArrayList.remove(position);
                mAdapter.notifyDataSetChanged();;

            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }
}
