package com.example.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.watertracker.R;
import com.example.watertracker.domain.Cup;

import java.util.List;

public class SaveCupName extends AppCompatActivity {

    Button btn;
    private List<Cup> cupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_cup_name);

        btn = (Button)findViewById(R.id.button5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cupList = ((MainActivity)MainActivity.mContext).account.getCupList();

                ((MainActivity)MainActivity.mContext).cup.setUid((long)1);
                ((MainActivity)MainActivity.mContext).cup.setCupName("나의컵 1");
                ((MainActivity)MainActivity.mContext).cup.setCupWeight(1111);
                ((MainActivity)MainActivity.mContext).saveCup();

                //cupList.add(cup);
                ((MainActivity)MainActivity.mContext).getUserInfo();

                Intent intent = new Intent(SaveCupName.this, MainActivity.class);
                startActivity(intent);



            }
        });








    }
}
