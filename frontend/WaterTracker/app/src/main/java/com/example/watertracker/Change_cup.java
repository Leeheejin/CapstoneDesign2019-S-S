package com.example.watertracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;



public class Change_cup extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cup);

        mRecyclerView = findViewById(R.id.recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<CupInfo> cupInfoArrayList = new ArrayList<>();
        cupInfoArrayList.add(new CupInfo(R.drawable.cup1, "나의 컵1"));
        cupInfoArrayList.add(new CupInfo(R.drawable.cup2, "나의 컵2"));
        cupInfoArrayList.add(new CupInfo(R.drawable.cup3, "나의 컵3"));
        cupInfoArrayList.add(new CupInfo(R.drawable.cup4, "나의 컵4"));
        cupInfoArrayList.add(new CupInfo(R.drawable.glass_water, "나의 컵5"));

        MyAdapter myAdapter = new MyAdapter(cupInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);


    }

}

