package com.example.watertracker;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;


public class BluetoothService {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONNECT_BT = 2;
    private static  final int REQUEST_CONNECT_DEVICE = 1;
    private static final String TAG = "BluetoothService";
    private BluetoothAdapter btAdapter;
    private Activity mActivity;
    private Handler mHandler;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    private static final int STATE_NONE = 0;
    private static final int STATE_LISTEN = 1;
    private static  final int STATE_CONNECTING = 2;
    private static final int STATE_CONNECTED = 3;
    private int mState;

    public BluetoothService(Activity ac, Handler h){
        mActivity = ac;
        mHandler = h;

        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean getDeviceState(){
        Log.d(TAG, "블루투스 지원 확인");

        if(btAdapter == null){
            Log.d(TAG, "블루투스를 지원하지 않음");
            return false;
        }
        else{
            Log.d(TAG, "블루투스 지원");
            return true;
        }
    }

    public void enableBluetooth(){
        Log.i(TAG, "블루투스 상태 확인");

        if(btAdapter.isEnabled()){          //블루투스 상태 On인 경우
            Log.d(TAG, "블루투스 사용 가능");
            scanDevice();
        }
        else{                               //블루투스 상태 Off인 경우
            Log.d(TAG, "블루투스 사용 요청");
                                            //블루투스 활성화 요청 알림창 띄우기
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mActivity.startActivityForResult(i, REQUEST_ENABLE_BT);
        }
    }

    public void scanDevice(){
        Log.d(TAG, "Scan Device");

        Intent serverIntent = new Intent(mActivity, DeviceListActivity.class);
        mActivity.startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    public void getDeviceInfo(Intent data){                     //검색된 기기에 접속

        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        Log.d(TAG, "Get Device Info \n" + "address: " + address);

        connect(device);
    }

    private synchronized void setState(int state){              //블루투스 상태 set
        mState = state;
        Log.d(TAG, "setState() " + mState + "->" + state);
    }

    public synchronized int getState(){     //블루투스 상태 얻기
        return mState;
    }       //블루투스 상태 get

    public synchronized void start(){
        Log.d(TAG, "시작");

        if(mConnectThread == null){         //연결 시도하는 모든 thread 비우기

        }
        else{
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mConnectedThread == null) {

        } else {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
    }

    public synchronized void connect(BluetoothDevice device){       //connectthread 초기화
                                                                    //디바이스의 모든 연결 제거
        Log.d(TAG, "연결할 장치: " + device);

        if(mState == STATE_CONNECTING){
            if(mConnectThread == null){

            }
            else{
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        if(mConnectedThread == null){

        }
        else{
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectThread = new ConnectThread(device);     //디바이스와 연결하기 위한 스레드 시작

        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device){
        Log.d(TAG, "연결됨");

        if(mConnectThread == null){

        }
        else{
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if(mConnectedThread == null){

        }
        else{
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        setState(STATE_CONNECTED);
    }

    public synchronized void stop(){
        Log.d(TAG, "멈춤");

        if(mConnectThread != null){
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if(mConnectedThread != null){
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        setState(STATE_NONE);
    }

    public void write(byte[] out){
        ConnectedThread r;
        synchronized (this){
            if(mState != STATE_CONNECTED)
                return;
            r = mConnectedThread;
        }
    }

    private void connectionFailed(){
        setState(STATE_LISTEN);
    }

    private void connectionLost(){
        setState(STATE_LISTEN);
    }

    private class ConnectThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device){
            mmDevice = device;
            BluetoothSocket tmp = null;

            try{
                //tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                //tmp = createBluetoothSocket(mmDevice);
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);    //디바이스 정보 얻어서 소켓 생성
            }catch (IOException e){
                Log.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;             //디바이스 정보를 얻어서 BluetoothSocket 생성
        }

        public void run(){
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");

            btAdapter.cancelDiscovery();    //기기검색 중지지

           try {            //BluetoothSocket 연결 시도
                mmSocket.connect();
                Log.d(TAG, "연결 성공");
            } catch (IOException e) {
                connectionFailed();         //연결 실패시 불러오는 메소드
                Log.d(TAG, "연결 실패");
                Log.e(TAG, e.toString());
                try {
                    mmSocket.close();       //소켓 닫기
                } catch (IOException e2) {
                    Log.e(TAG,
                            "unable to close() socket during connection failure", e2);
                }
                BluetoothService.this.start();
                return;
           }

            synchronized (BluetoothService.this) {
                mConnectThread = null;      //ConnectThread 클래스 reset
            }

            connected(mmSocket, mmDevice);  //ConnectThread 시작
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread 생성");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

                                   // BluetoothSocket의 inputstream 과 outputstream을 얻기
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();          //데이터 송수신을 위한 스트림 열기

            } catch (IOException e) {
                Log.e(TAG, "소켓이 생성되지 않음", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }



        public void write(byte[] buffer) {
            try {
                                    // 값을 쓰는 부분(값을 보낸다)
                mmOutStream.write(buffer);

            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]
                        { UUID.class
                        });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }

        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }
}
