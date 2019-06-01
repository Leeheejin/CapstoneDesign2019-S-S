package com.example.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.watertracker.MainActivity;
import com.example.watertracker.R;

public class InputCupData extends AppCompatActivity {

    private Button measure_btn;
    private Button pass_btn;
    private ProgressBar progressCircle;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_cup_data);

        measure_btn = (Button)findViewById(R.id.Measure_btn);
        text = (TextView)findViewById(R.id.textView2);
        progressCircle = (ProgressBar)findViewById(R.id.progressBar);
        pass_btn = (Button)findViewById(R.id.pass_btn);

        progressCircle.setVisibility(View.INVISIBLE);
        text.setVisibility(View.INVISIBLE);
        pass_btn.setVisibility(View.INVISIBLE);

        measure_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                progressCircle.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                measure_btn.setVisibility(View.INVISIBLE);
                pass_btn.setVisibility(View.VISIBLE);

            }
        });

        pass_btn.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View view){
                Intent intent = new Intent(
                        getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
