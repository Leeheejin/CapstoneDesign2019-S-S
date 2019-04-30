package com.example.watertracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        int acc_day = 6;
        int achivement_week = acc_day%7;
        int achivement_30day = acc_day%30;
        int achivement_100day = acc_day%100;

        ProgressBar progress_week = (ProgressBar) findViewById(R.id.progress_goal_week);
        progress_week.setProgress(achivement_week);
        ProgressBar progress_30day = (ProgressBar) findViewById(R.id.progress_goal_30day);
        progress_30day.setProgress(achivement_30day);
        ProgressBar progress_100day = (ProgressBar) findViewById(R.id.progress_goal_100day);
        progress_100day.setProgress(achivement_100day);

        TextView txt_progress_week = (TextView) findViewById(R.id.txt_goal_week);
        txt_progress_week.setText(String.format("%.3f",((float)achivement_week/7*100)) + "%");
        TextView txt_progress_30day = (TextView) findViewById(R.id.txt_goal_30day);
        txt_progress_30day.setText(String.format("%.3f",((float)achivement_30day/30*100)) + "%");
        TextView txt_progress_100day = (TextView) findViewById(R.id.txt_goal_100day);
        txt_progress_100day.setText(String.format("%.3f",((float)achivement_100day/100*100)) + "%");

    }
}
