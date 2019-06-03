package com.example.watertracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watertracker.domain.Account;
import com.example.watertracker.domain.Cup;
import com.example.watertracker.domain.Water;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Switch alarmSwitch;

    private static final String TAG = "MainActivity";
    private HttpConnection httpConn = HttpConnection.getInstance();

    public Account account = new Account();
    public Cup cup = new Cup();
    public Water water = new Water();

    public static Context mContext;
    public TextView remainToGoal;
    public TextView daily_allo;
    public ProgressBar progressBar;
    public int remaintogoal;
    public int dailySum;
    public float dailyGoal;
    public  int dailyPercent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;
        getUserInfo();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //Alarm Default

        SharedPreferences prefs2 = getSharedPreferences("PushTerm", MODE_PRIVATE);
        int term = prefs2.getInt("term", 60);

        new AlarmHATT(getApplicationContext()).Alarm(term); // Alarm default


        // Date and Clock

        SimpleDateFormat datef = new SimpleDateFormat("yyyy년 MM월 dd일");
        Calendar c1 = Calendar.getInstance();
        String strDate = datef.format(c1.getTime());

        TextView date = (TextView)findViewById(R.id.txt_date);
        date.setText(strDate);

        float user_weight = ((MainActivity)MainActivity.mContext).account.getWeight();
        //TODO : 이 부분이 어플 실행 시 어느 시점인지 모름 account.setWeight((int) user_weight);

        dailyGoal = ((MainActivity)MainActivity.mContext).account.getRecommendDrink(); //TODO:  일일 권장량, 서버에서 LOAD , SET_ALLO 페이지에서 서버로 입력

        dailySum = ((MainActivity)MainActivity.mContext).account.getNowDrink();  //TODO: 일일 누적 음수량, 서버에서 LOAD

        dailyPercent =  (int)((dailySum/dailyGoal) *100); // 일일 누적 달성량
        remaintogoal = (int)dailyGoal - dailySum; // 목표달성까지 남은 음수량

        Log.d("init account : ", account.toString());
        //TODO :: 값 불러오는 속도보다 어플리케이션에서 화면에 찍는 속도가 훨씬 빨라서 항상 초기값만 찍힘

        /* Test
        dailyGoal = user_weight*30;
        account.setRecommendDrink((int)dailyGoal);
        confirm();
        */

        //remainToGoal.setText("목표 달성까지 " + remaintogoal + "mL"  );

        daily_allo = (TextView)findViewById(R.id.txt_allo);
        //daily_allo.setText(dailyPercent+"%");

        //ImageView waterdrop = (ImageView)findViewById(R.id.img_waterdrop);
        progressBar = (ProgressBar) findViewById(R.id.water_prog);
        progressBar.setMax(100);
        progressBar.setProgress(5);
        //progressBar.setSecondaryProgress(dailyPercent);


        setScreen();


        //Alarm Switch

        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        alarmSwitch = (Switch) findViewById(R.id.main_switch);
        alarmSwitch.setChecked(prefs.getBoolean("Alarm",true));



        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){

                    /*Test
                    dailySum = dailySum +100;
                    account.setNowDrink(dailySum);
                    confirm();
                    setScreen();
                    */

                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean("Alarm", true);
                    ed.commit();
                    Toast.makeText(MainActivity.this, "알람이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                    alarmSwitch.setChecked(true);

                } else {
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putBoolean("Alarm", false);
                    ed.commit();
                    Toast.makeText(MainActivity.this, "알람이 해제되었습니다.", Toast.LENGTH_SHORT).show();
                    alarmSwitch.setChecked(false);
                }
            }
        });
    }

    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm(int term) {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, BroadcastD.class);
            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 00, 00, 00);
            //알람 예약
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),term*60*1000, sender);
        }
        public void cancelAlarm(int alarmId)
        {
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, BroadcastD.class);
            PendingIntent pIntent = PendingIntent.getBroadcast(context,alarmId,intent,0);
            alarmManager.cancel(pIntent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Alarm term Setting
    public void term_set() {
        final String[] items = new String[]{"10분", "20분", "30분", "40분", "50분", "60분"};
        final int[] selectedindex = {0};

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("알림 간격 설정")
                .setSingleChoiceItems(items
                        , 0
                        , new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedindex[0] = which;
                            }
                        })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        SharedPreferences prefs2 = getSharedPreferences("PushTerm", MODE_PRIVATE);
                        SharedPreferences.Editor ed2 = prefs2.edit();
                        switch (items[selectedindex[0]]) {
                            case "10분":
                                ed2.putInt("term", 10);
                                ed2.commit();
                                new AlarmHATT(getApplicationContext()).cancelAlarm(0);
                                new AlarmHATT(getApplicationContext()).Alarm(prefs2.getInt("term", 60));
                                break;
                            case "20분":
                                ed2.putInt("term", 20);
                                ed2.commit();
                                new AlarmHATT(getApplicationContext()).cancelAlarm(0);
                                new AlarmHATT(getApplicationContext()).Alarm(prefs2.getInt("term", 60));
                                break;
                            case "30분":
                                ed2.putInt("term", 30);
                                ed2.commit();
                                new AlarmHATT(getApplicationContext()).cancelAlarm(0);
                                new AlarmHATT(getApplicationContext()).Alarm(prefs2.getInt("term", 60));
                                break;
                            case "40분":
                                ed2.putInt("term", 40);
                                ed2.commit();
                                new AlarmHATT(getApplicationContext()).cancelAlarm(0);
                                new AlarmHATT(getApplicationContext()).Alarm(prefs2.getInt("term", 60));
                                break;
                            case "50분":
                                ed2.putInt("term", 50);
                                ed2.commit();
                                new AlarmHATT(getApplicationContext()).cancelAlarm(0);
                                new AlarmHATT(getApplicationContext()).Alarm(prefs2.getInt("term", 60));
                                break;
                            case "60분":
                                ed2.putInt("term", 60);
                                ed2.commit();
                                new AlarmHATT(getApplicationContext()).cancelAlarm(0);
                                new AlarmHATT(getApplicationContext()).Alarm(prefs2.getInt("term", 60));
                                break;
                        }

                        Toast.makeText(MainActivity.this, "알림간격이 " + items[selectedindex[0]] + "으로 설정되었습니다.", Toast.LENGTH_SHORT).show();
                    }


                }).create().show();
    }

    //Screen TextView and ImageView
    public void setScreen() {

        dailySum = account.getNowDrink();
        dailyGoal = account.getRecommendDrink();
        dailyPercent =  (int)((dailySum/dailyGoal) *100); // 일일 누적 달성량
        remaintogoal = (int)dailyGoal - dailySum; // 목표달성까지 남은 음수량

        remainToGoal.setText("목표 달성까지 " + remaintogoal + "mL"  );
        daily_allo.setText(dailyPercent+"%");
        progressBar.setSecondaryProgress(dailyPercent);

        if(remaintogoal<=0)
        {
            remainToGoal.setText("목표 달성 완료!");
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_change_info) {
            Intent intent = new Intent(MainActivity.this, Change_information.class);
            startActivity(intent);
        } else if (id == R.id.nav_change_cup) {
            Intent intent = new Intent(MainActivity.this, Change_cup.class);
            startActivity(intent);
        } else if (id == R.id.nav_set_allo) {
            Intent intent = new Intent(MainActivity.this, Set_Allo.class);
            startActivity(intent);
        } else if (id == R.id.nav_set_push) {
            term_set();
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, History.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getUserInfo() {
        new Thread() {
            public void run() {
                ((MainActivity)MainActivity.mContext).account.setId((long) 1);
                httpConn.getUserInfo(((MainActivity)MainActivity.mContext).account, userCallback);
            }
        }.start();
    }


    public void confirm() {
        new Thread() {
            public void run() {
                httpConn.confirm(account, userCallback);
            }
        }.start();
    }

    public void saveCup() {
        new Thread() {
            public void run() {
                httpConn.saveCup(cup, cupCallback);
            }
        }.start();
    }

    public void chanceCup() {
        new Thread() {
            public void run() {
                cup.setUid((long) 1);
                cup.setCid((long) 3);
                httpConn.changeCup(cup, cupCallback);
            }
        }.start();
    }

    public void editCup() {
        new Thread() {
            public void run() {
                httpConn.editCup(cup, cupCallback);
            }
        }.start();
    }

    public void deleteCup() {
        new Thread() {
            public void run() {
                httpConn.deleteCup(cup);
            }
        }.start();
    }

    public void cupInfo() {
        new Thread() {
            public void run() {
                httpConn.cupInfo(cup);
            }
        }.start();
    }

    public void drinkWater() {
        new Thread() {
            public void run() {
                httpConn.drinkWater(water, waterCallback);
            }
        }.start();
    }


    public final Callback userCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "콜백오류:"+e.getMessage());
            Log.d(TAG, e.toString());
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            ((MainActivity)MainActivity.mContext).account = objectMapper.readValue(responseBytes, Account.class);

            Log.d(TAG, "Account Info: " + ((MainActivity)MainActivity.mContext).account.toString());
        }
    };

    public final Callback cupCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "콜백오류:"+e.getMessage());
            Log.d(TAG, e.toString());
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            cup = objectMapper.readValue(responseBytes, Cup.class);

            Log.d(TAG, "Cup Info: " + cup.toString());
        }
    };

    public final Callback waterCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "콜백오류:"+e.getMessage());
            Log.d(TAG, e.toString());
        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {

            final byte[] responseBytes = response.body().bytes();
            ObjectMapper objectMapper = new ObjectMapper();
            water = objectMapper.readValue(responseBytes, Water.class);

            Log.d(TAG, "Water Info: " + water.toString());
        }
    };
}