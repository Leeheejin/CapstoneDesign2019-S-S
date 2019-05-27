package com.example.watertracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Set_Push extends AppCompatActivity {
    private TimePicker mTimePicker;
    private Calendar mCalendar;

    private Button term_button;
    public int term;

    private Switch alarmSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__push);


        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        alarmSwitch = (Switch) findViewById(R.id.alarm_switch);
        alarmSwitch.setChecked(prefs.getBoolean("Alarm",true));



        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){

                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean("Alarm", true);
                    ed.commit();

                    alarmSwitch.setChecked(true);



                } else {
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean("Alarm", false);
                    ed.commit();

                    alarmSwitch.setChecked(false);
                }
            }
        });


        term_button = (Button) findViewById(R.id.term);
        final int[] selectedindex = {0};

        term_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"10분","20분","30분","40분","50분","60분"};



                AlertDialog.Builder dialog = new AlertDialog.Builder(Set_Push.this);
                dialog.setTitle("알림 간격 설정")
                        .setSingleChoiceItems(items
                                ,0
                                ,new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                        selectedindex[0] = which;
                                    }
                                })
                        .setPositiveButton("확인",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Set_Push.this, MainActivity.class);

                                switch (items[selectedindex[0]]) {
                                    case "10분" :
                                        intent.putExtra("알림간격",1);
                                        startActivity(intent);
                                        break;
                                    case "20분" :
                                        intent.putExtra("알림간격",20);
                                        startActivity(intent);
                                        break;
                                    case "30분" :
                                        intent.putExtra("알림간격",30);
                                        startActivity(intent);
                                        break;
                                    case "40분" :
                                        intent.putExtra("알림간격",40);
                                        startActivity(intent);
                                        break;
                                    case "50분" :
                                        intent.putExtra("알림간격",50);
                                        startActivity(intent);
                                        break;
                                    case "60분" :
                                        intent.putExtra("알림간격",60);
                                        startActivity(intent);
                                        break;
                                }

                                Toast.makeText(Set_Push.this, "알림간격이 "+items[selectedindex[0]]+"으로 설정되었습니다.",Toast.LENGTH_SHORT).show();
                            }


                        }).create().show();

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
        });
    }


}
