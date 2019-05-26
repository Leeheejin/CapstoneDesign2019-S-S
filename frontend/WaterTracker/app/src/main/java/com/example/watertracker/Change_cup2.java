package com.example.watertracker;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.watertracker.databinding.ActivityChangeCupBinding;
import com.example.watertracker.databinding.RecyclerViewRowBinding;

import java.util.ArrayList;
import java.util.List;

public class Change_cup2 extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();
    private ActivityChangeCupBinding mBinding;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_cup);

        // make sample data
        for (int i = 0; i < 5; i++) {
            mList.add(String.format("sample item %s", i + 1));
        }

        // init recyclerView
        mBinding.recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView1.setAdapter(new RecyclerView.Adapter() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                CustViewHolder holder1 = (CustViewHolder) holder;
                holder1.setData(mList.get(position));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                RecyclerViewRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(Change_cup2.this), R.layout.recycler_view_row, parent, false);
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
        }
    };

    private void showToast(String msg) {
        if (mToast != null) mToast.cancel();

        mToast = Toast.makeText(Change_cup2.this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}

