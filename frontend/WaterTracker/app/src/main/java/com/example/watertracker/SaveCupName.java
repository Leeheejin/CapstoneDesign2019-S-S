package com.example.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.watertracker.R;
import com.example.watertracker.domain.Cup;

import java.util.List;

public class SaveCupName extends AppCompatActivity {

    Button btn;
    EditText cupNameInput;
    public String cupName;
    private List<Cup> cupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_cup_name);

        btn = (Button)findViewById(R.id.button5);

        cupNameInput = (EditText) findViewById(R.id.cupName);

        cupNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cupName = s.toString();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cupList = ((MainActivity)MainActivity.mContext).account.getCupList();

                ((MainActivity)MainActivity.mContext).cup.setUid((long)1);
                ((MainActivity)MainActivity.mContext).cup.setCupName(cupName);
                ((MainActivity)MainActivity.mContext).cup.setCupWeight(1111);
                ((MainActivity)MainActivity.mContext).saveCup();

                //long savedCid = ((MainActivity)MainActivity.mContext).account.getCupList().get(0).getCid();
                //((MainActivity)MainActivity.mContext).cup.setCid(savedCid);
                //((MainActivity)MainActivity.mContext).chanceCup();
                //Toast.makeText(SaveCupName.this, "새로운 컵을 사용합니다.", Toast.LENGTH_SHORT).show();

                //cupList.add(cup);

                //Change_cup c_cAct = (Change_cup) Change_cup.c_cAct;
                //c_cAct.finish();

                ((MainActivity)MainActivity.mContext).getUserInfo();

                Intent intent = new Intent(SaveCupName.this, MainActivity.class);
                startActivity(intent);



            }
        });








    }
}
