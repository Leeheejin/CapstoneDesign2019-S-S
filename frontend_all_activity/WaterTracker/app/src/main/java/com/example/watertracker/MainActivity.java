package com.example.watertracker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Switch alarmSwitch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        SharedPreferences prefs2 = getSharedPreferences("PushTerm", MODE_PRIVATE);
        int term = prefs2.getInt("term", 60);

        new AlarmHATT(getApplicationContext()).Alarm(term); // Alarm default


        SimpleDateFormat datef = new SimpleDateFormat("yyyy년 MM월 dd일");
        Calendar c1 = Calendar.getInstance();
        String strDate = datef.format(c1.getTime());

        TextView date = (TextView)findViewById(R.id.txt_date);
        date.setText(strDate);

        float user_weight = 70;
        float dailyGoal = 0; //TODO:  일일 권장량, 서버에서 LOAD , SET_ALLO 페이지에서 서버로 입력

        dailyGoal = user_weight * 30;

        int dailySum = 700;  //TODO: 일일 누적 음수량, 서버에서 LOAD

        int dailyPercent =  (int)((dailySum/dailyGoal) *100); // 일일 누적 달성량
        int remaintogoal = (int)dailyGoal - dailySum; // 목표달성까지 남은 음수량



        TextView remainToGoal = (TextView)findViewById(R.id.txt_remaintToGoal);
        remainToGoal.setText("목표 달성까지 " + remaintogoal + "mL"  );


        TextView daily_allo = (TextView)findViewById(R.id.txt_allo);
        daily_allo.setText(dailyPercent+"%");

        //ImageView waterdrop = (ImageView)findViewById(R.id.img_waterdrop);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.water_prog);
        progressBar.setMax(100);
        progressBar.setProgress(30);
        progressBar.setSecondaryProgress(dailyPercent);


        SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
        alarmSwitch = (Switch) findViewById(R.id.main_switch);
        alarmSwitch.setChecked(prefs.getBoolean("Alarm",true));



        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){

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



}
