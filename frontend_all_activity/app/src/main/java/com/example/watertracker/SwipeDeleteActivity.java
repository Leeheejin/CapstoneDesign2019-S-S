package com.example.watertracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SwipeDeleteActivity extends AppCompatActivity {
    List list;
    RecyclerView re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cup);

        re = (RecyclerView) findViewById(R.id.recycler_view1);
        re.setLayoutManager(new LinearLayoutManager(this));
        re.setAdapter(new SwipeDeleteAdapter((ArrayList<CupInfo>) setDateList()));
        re.setHasFixedSize(true); // 크기 고정
        setItemTouchHelper();

    }

    private List setDateList() {
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("List"+i);
        }

        return list;
    }

    private void setItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            // ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP
            // 하면 상하좌우 다 움직임

            Drawable background, mark;
            int markMargin;
            Context context;
            // 드래그 할 때 호출
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // 제공된 ViewHolder 의 Swipe 방향을 반환
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            // 사용자가 Swipe 할 때 호출
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                SwipeDeleteAdapter adapter = (SwipeDeleteAdapter) re.getAdapter();
                adapter.remove(swipedPosition);
            }

            // RecyclerView 의 onDraw 호출
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;

                    mark = ContextCompat.getDrawable(context, R.drawable.ic_remove_24dp);
                    mark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                    markMargin = (int) context.getResources().getDimension(R.dimen.ic_remove_margin);

                    // Item 을 좌측으로 Swipe 했을 때 Background 변화: ItemTouchHelper.LEFT
                    if (dX < 1) {
                        background = new ColorDrawable(Color.parseColor("#FFD32F2F"));
                        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                        //dX(dY): 사용자 동작에 의한 수평(수직) 변화의 양
                        background.draw(c); //Bounds: 범위. draw: 그리기. - 사용자 동작에 따라 Item 의 Background 변화

                        // Mark 그리기
                        int itemHeight = itemView.getBottom() - itemView.getTop(); // Item 높이
                        int markWidth = mark.getIntrinsicWidth(); // Intrinsic: 본질적 - xMark 의 실제 길이
                        int markHeight = mark.getIntrinsicHeight();

                        int markLeft = itemView.getRight() - markMargin - markWidth;
                        int markRight = itemView.getRight() - markMargin;
                        int markTop = itemView.getTop() + (itemHeight - markHeight) / 2;
                        int markBottom = markTop + markHeight;
                        mark.setBounds(markLeft, markTop, markRight, markBottom);
                        mark.draw(c);
                    } else { // ItemTouchHelper.RIGHT
                        background = new ColorDrawable(Color.parseColor("#FF388E3C"));
                        background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (int) dX, itemView.getBottom());
                        background.draw(c);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(re);
    }
}