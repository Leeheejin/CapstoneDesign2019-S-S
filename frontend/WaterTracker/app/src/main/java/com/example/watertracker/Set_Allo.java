package com.example.watertracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class Set_Allo extends AppCompatActivity {

    SeekBar seekbar;
    EditText outcome;
    public int dailyGoal = ((MainActivity)MainActivity.mContext).account.getRecommendDrink(); //TODO: 일일 권장량 , 서버에 입력 (메인액티비티에서 사용)




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__allo);



        seekbar = (SeekBar) findViewById(R.id.water_SeekBar);
        outcome = (EditText) findViewById(R.id.water_input);

        outcome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    seekbar.setProgress(Integer.parseInt(s.toString()));
                } catch(Exception ex) {}

            }
        });


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                dailyGoal = seekbar.getProgress();
                update();
                outcome.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                dailyGoal = seekbar.getProgress();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                dailyGoal = seekbar.getProgress();

            }
        });
    }

    public void update(){
        outcome.setText(new StringBuilder().append(dailyGoal));

    }


}

