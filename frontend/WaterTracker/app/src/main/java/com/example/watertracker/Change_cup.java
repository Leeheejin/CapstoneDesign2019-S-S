package com.example.watertracker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.watertracker.databinding.ActivityChangeCupBinding;
import com.example.watertracker.databinding.RecyclerViewRowBinding;
import com.example.watertracker.domain.Cup;

import java.util.ArrayList;
import java.util.List;

public class Change_cup extends AppCompatActivity {

    private List<String> mList = new ArrayList<>(); //TODO : cup Name
    private ActivityChangeCupBinding mBinding;
    private Toast mToast;
    private ArrayList<Integer> cupInfoArrayList = new ArrayList<>(); //TODO: cup Image
    private List<Cup> cupList;

    public Cup cup = new Cup();

    Button addCup;
    Button complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_cup);

        addCup = (Button)findViewById(R.id.cup_change);

        cupList = ((MainActivity)MainActivity.mContext).account.getCupList();

        for(int i =0; i<cupList.size(); i++)
        {
            mList.add(cupList.get(i).getCupName());
        }


        // init recyclerView
        mBinding.recyclerView1.setLayoutManager(new LinearLayoutManager(
                this));

        mBinding.recyclerView1.setAdapter(new RecyclerView.Adapter() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                CustViewHolder holder1 = (CustViewHolder) holder;
                holder1.setData(mList.get(position));
                //holder1.setImage(cupInfoArrayList.get(position));

                holder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((MainActivity)MainActivity.mContext).cup = cupList.get(position);
                        Log.d("Cup Change Method : ", ((MainActivity)MainActivity.mContext).cup.toString());
                        ((MainActivity)MainActivity.mContext).chanceCup();

                        mToast = Toast.makeText(Change_cup.this,((MainActivity)MainActivity.mContext).cup.getCupName() + "을 사용합니다", Toast.LENGTH_SHORT);
                        mToast.show();

                    }
                });


            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                RecyclerViewRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(Change_cup.this), R.layout.recycler_view_row, parent, false);
                return new CustViewHolder(binding);
            }

            @Override
            public int getItemCount() {
                return mList.size();
            }
        });


        // setup swipe to remove item
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mBinding.recyclerView1);




        addCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Change_cup.this, SaveCupName.class);
                startActivity(intent);




            }
        });

        complete = (Button)findViewById(R.id.btn_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });


    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(Change_cup.this, MainActivity.class);
        startActivity(intent);

    }




    /**
     * view holder class
     */
    class CustViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewRowBinding mBinding;

        public CustViewHolder(RecyclerViewRowBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }


        public void setData(String data) {
            mBinding.tvName.setText(data);
        }
        public void setImage(int image) { mBinding.ivPicture.setImageResource(image);}
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            showToast("on Move");
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            // 삭제되는 아이템의 포지션을 가져온다
            final int position = viewHolder.getAdapterPosition();
            // 데이터의 해당 포지션을 삭제한다
            showToast("on remove " + mList.remove(position));
            // 아답타에게 알린다
            mBinding.recyclerView1.getAdapter().notifyItemRemoved(position);

            //TODO: way to use method ((MainActivity)MainActivity.mContext).deleteCup();

            long temp_Cid = ((MainActivity)MainActivity.mContext).cup.getCid();
            String temp_CupName = ((MainActivity)MainActivity.mContext).cup.getCupName();
            int temp_CupWeight = ((MainActivity)MainActivity.mContext).cup.getCupWeight();

            Cup thisCup =((MainActivity)MainActivity.mContext).account.getCupList().get(position);
            ((MainActivity)MainActivity.mContext).cup.setCid(thisCup.getCid());
            ((MainActivity)MainActivity.mContext).cup.setUid(thisCup.getUid());
            ((MainActivity)MainActivity.mContext).cup.setCupName(thisCup.getCupName());
            ((MainActivity)MainActivity.mContext).cup.setCupWeight(thisCup.getCupWeight());
            ((MainActivity)MainActivity.mContext).deleteCup();

            ((MainActivity)MainActivity.mContext).cup.setCid(temp_Cid);
            ((MainActivity)MainActivity.mContext).cup.setCupName(temp_CupName);
            ((MainActivity)MainActivity.mContext).cup.setCupWeight(temp_CupWeight);

            ((MainActivity)MainActivity.mContext).cup = cupList.get(position);
            Log.d("Cup Delete Method : ", ((MainActivity)MainActivity.mContext).cup.toString());
            ((MainActivity)MainActivity.mContext).chanceCup();

        }
    };

    private void showToast(String msg) {
        if (mToast != null) mToast.cancel();

        mToast = Toast.makeText(Change_cup.this, "삭제되었습니다.", Toast.LENGTH_SHORT);
        mToast.show();
    }



}

