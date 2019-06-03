package com.example.watertracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watertracker.domain.Account;

import java.util.ArrayList;

public class Set_Allo extends AppCompatActivity {

    SeekBar seekbar;
    EditText outcome;
    TextView calWater;
    CheckBox defaultAllo;
    CheckBox customAllo;

    ArrayList<CheckBox> mCheckBoxes = new ArrayList<CheckBox>();

    public int dailyGoal = ((MainActivity)MainActivity.mContext).account.getRecommendDrink(); //TODO: 일일 권장량 , 서버에 입력 (메인액티비티에서 사용)
    //public int weight = ((MainActivity)MainActivity.mContext).account.getWeight();
    Button btn;
    Account account = ((MainActivity)MainActivity.mContext).account;

    private static final String TAG = "SetActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__allo);

        seekbar = (SeekBar) findViewById(R.id.water_SeekBar);
        outcome = (EditText) findViewById(R.id.water_input);
        calWater = (TextView) findViewById(R.id.cal_water);
        defaultAllo = (CheckBox) findViewById(R.id.check_1);
        customAllo = (CheckBox) findViewById(R.id.check_2);

        mCheckBoxes.add(defaultAllo);
        mCheckBoxes.add(customAllo);

        // calWater.setText(weight*30+"mL");   무게정보가 안들어가 있을때 팅김
        outcome.setText(dailyGoal+"");
        seekbar.setProgress(dailyGoal);

        defaultAllo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((CheckBox)v).isChecked())
                {
                    for (int i =0; i < mCheckBoxes.size(); i++)
                    {
                        if (mCheckBoxes.get(i) == v)
                        {

                        }
                        else
                        {
                            mCheckBoxes.get(i).setChecked(false);
                        }
                    }
                }

            }
        });

        customAllo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((CheckBox)v).isChecked())
                {
                    for (int i =0; i < mCheckBoxes.size(); i++)
                    {
                        if (mCheckBoxes.get(i) == v)
                        {

                        }
                        else
                        {
                            mCheckBoxes.get(i).setChecked(false);
                        }
                    }
                }
            }
        });







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
                    dailyGoal= Integer.parseInt((s.toString()));
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

        btn = (Button)findViewById(R.id.btn_edit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)MainActivity.mContext).account.setRecommendDrink(dailyGoal);
                ((MainActivity)MainActivity.mContext).confirm();

                //((MainActivity)MainActivity.mContext).remaintogoal = dailyGoal - ((MainActivity)MainActivity.mContext).dailySum;
                //((MainActivity)MainActivity.mContext).remainToGoal.setText("목표 달성까지 " + ((MainActivity)MainActivity.mContext).remaintogoal + "mL"  );
                ((MainActivity)MainActivity.mContext).setScreen();

                Toast.makeText(getApplicationContext(),"수정완료",Toast.LENGTH_LONG).show();

                Log.d(TAG, "Setting Page's Account Info: " + account.toString());
            }
        });
    }

    public void update(){
        outcome.setText(new StringBuilder().append(dailyGoal));

    }


}

