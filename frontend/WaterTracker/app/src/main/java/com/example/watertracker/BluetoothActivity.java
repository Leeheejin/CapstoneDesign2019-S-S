package com.example.watertracker;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BluetoothActivity extends AppCompatActivity implements OnClickListener
{

    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final String TAG = "MainBluetoothActivity";
    private BluetoothAdapter btAdapter;
    private BluetoothService btService = null;
    private Button connect_btn;
    private TextView txt_Result;
    private ProgressBar progressCircle;

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_bluetooth);

        connect_btn = (Button)findViewById(R.id.connect_btn);
        txt_Result = (TextView)findViewById(R.id.bt_name);
        progressCircle = (ProgressBar)findViewById(R.id.progressBar);

        connect_btn.setOnClickListener(this);

        progressCircle.setVisibility(View.INVISIBLE);

        if(btService == null){
            btService = new BluetoothService(this, mHandler);
        }

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        final TextView bt_name = (TextView)findViewById(R.id.bt_name);
       /*
        String device_name = btAdapter.getName();
        bt_name.setText(device_name);
        */

        Button pass_btn = (Button)findViewById(R.id.pass_btn);
        pass_btn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(
                        getApplicationContext(), InputCupData.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v){
        if(btService.getDeviceState()){
            btService.enableBluetooth();    //블루투스 지원 가능한 기기인 경우
            progressCircle.setVisibility(View.VISIBLE);
        }
        else{
                //블루투스 지원하지 않을 때....
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
            //블루투스 활성화 알림창 버튼 선택
        Log.d(TAG, "OnActivityResult" + resultCode);

        switch(requestCode){
            case REQUEST_CONNECT_DEVICE:
                if(resultCode == Activity.RESULT_OK){
                    btService.getDeviceInfo(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK){
                        //확인 버튼 누른 경우
                    btService.scanDevice();
                }
                else{
                    Log.d(TAG, "블루투스 사용 불가능");  //취소 버튼 누른 경우
                }
                break;
        }
    }


}

