package com.example.watertracker;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

public class Set_Push extends AppCompatActivity {
    private TimePicker mTimePicker;
    private Calendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__push);

/*
        mTimePicker = (TimePicker) findViewById(R.id.timePick);

        mCalendar = Calendar.getInstance();
        int hour, min;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            hour = mTimePicker.getHour();
            min = mTimePicker.getMinute();
        }
        else {
            hour = mTimePicker.getCurrentHour();
            min = mTimePicker.getCurrentMinute();
        }
*/
    }

}
