package com.example.watertracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lombok.ToString;

public class Change_information extends AppCompatActivity {

    // 싱글톤 패턴을 사용하여 이 어플리케이션에서 전역으로 사용하는 하나의 변수를 접근하기 위한 방법
    // ((MainActivity)MainActivity.mContext) 에 . 을 찍어서 해당 변수, 혹은 함수에 접근할 수 있음
    // ((MainActivity)MainActivity.mContext).account 는 유저의 정보
    // ((MainActivity)MainActivity.mContext).account.getㅇㅇㅇ 과 같이 get으로 시작하는 메소드는 해당 변수가 가지고 있는 정보를 불러 옴
    // ((MainActivity)MainActivity.mContext).account.getWeight(); 를 사용하여 유저의 현재 몸무게를 불러올 수 있음
    // ((MainActivity)MainActivity.mContext).account.setㅇㅇㅇ 과 같이 set으로 시작하는 메소드는 해당 변수에 값을 넣는 방식
    // ((MainActivity)MainActivity.mContext).account.setWeight(40); 과 같이 사용하면 현재 유저의 몸무게를 40으로 만들 수 있음
    // 단, set을 사용할 시에는 변경 내용을 서버에 알려야 하기 때문에 꼭 서버로 데이터를 전송해야 함.
    // ((MainActivity)MainActivity.mContext).confirm(); 과 같이 사용하면 현재 account 안에 든 정보가 서버로 전송되고
    // 동시에 서버로부터 해당 계정 정보를 불러와 자동으로 유저의 정보가 업데이트 됨
    // 현재 사용 가능한 메소드 종류
    // getUserInfo() = 유저 정보 불러옴 (처음 어플리케이션 실행 시 한번만 실행 됨
    // confirm() = 현재 유저 정보를 저장하고 유저 정보를 업데이트함
    // saveCup() = 컵을 생성하면 현재 생성된 컵 정보를 저장함
    // chanceCup() = 현재 유저가 사용할 컵 정보를 저장함
    // editCup() = 현재 컵 정보를 수정함
    // deleteCup() = 현재 컵 정보를 삭제함
    // cupInfo() = 서버에 저장된 컵 데이터를 불러옴
    // drinkWater() = 음수시 데이터를 저장함

    // 주의 : 절대 삭제되지 않는 유저 정보와는 달리, 컵 정보는 삭제 될 수 있는 정보이기 때문에
    // 컵 관련 페이지에서는 클릭으로 컵을 선택했을 때 항상 cupInfo 으로 컵 정보를 받아와야 하고
    // 해당 컵 정보를 서버로 전송하여 삭제해야 함.

    // 주의2 : 컵 정보에는 반드시 cid, uid가 포함되어야 함
    // 생성시에 컵 정보에 자동으로 cid가 부여되서 반환됨 이 정보를 컵이 가지고 있어야 함.
    // 컵의 수정 삭제 시 컵 정보에 cid, uid를 포함하여 request를 전송해야 함.
    // 예를 들어 삭제 시,

    // Cup cup = ((MainActivity)MainActivity.mContext).cup;
    // cup.setUid((long) 1);
    // cup.setCid((long) 2);
    // ((MainActivity)MainActivity.mContext).cupInfo();
    // ((MainActivity)MainActivity.mContext).deleteCup();
    // 과 같이 전송해야 정상 처리 됨. 만약 uid,cid를 포함하지 않고 전송 시에는 오류를 반환함.

    public int weight;
    public Float avg_drop = 0.0f; //TODO : 평균 한 모금 설정
    private static final String TAG = "InfoActivity";

    Button btn;
    EditText putweight;
    TextView avgdrop;
    public int mainweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);

        int size = ((MainActivity)MainActivity.mContext).account.getWaterLog().size();

        if (size > 0) {
            ArrayList<Float> tempList =  ((MainActivity) MainActivity.mContext).account.getWaterLog();
            Log.d(TAG, tempList.toString());
            for (int i = 0; i < size; i++) {
                avg_drop += tempList.get(i);
            }
            avg_drop /= size;

        } else {
            avg_drop = 0.0f;
        }
        avgdrop = (TextView)findViewById(R.id.ml_text);
        avgdrop.setText(avg_drop+"");

        putweight = (EditText)findViewById(R.id.put_weight);
        mainweight = ((MainActivity)MainActivity.mContext).account.getWeight();

        putweight.setText(mainweight+"");

        putweight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{

                    //weight = Integer.parseInt(putweight.getText().toString());
                    weight=Integer.parseInt((s.toString()));
                    Log.d(TAG, String.valueOf(weight));

                } catch(Exception ex) {}

            }
        });




        btn = (Button)findViewById(R.id.btn_edit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences prefs = getSharedPreferences("SetAllo", MODE_PRIVATE);

                if(prefs.getBoolean("Allo",true))
                {
                    ((MainActivity)MainActivity.mContext).account.setRecommendDrink(weight*30);
                }



                ((MainActivity)MainActivity.mContext).account.setWeight(weight);
                ((MainActivity)MainActivity.mContext).confirm();

                Log.d(TAG, ((MainActivity)MainActivity.mContext).account.toString());

                Toast.makeText(getApplicationContext(),"수정완료",Toast.LENGTH_LONG).show();






                SharedPreferences isNew = getSharedPreferences("isNew", MODE_PRIVATE);

                if(isNew.getBoolean("New", true)) {

                    Intent intent = new Intent(getApplicationContext(), Set_Allo.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(Change_information.this, MainActivity.class);
        startActivity(intent);

    }
}
