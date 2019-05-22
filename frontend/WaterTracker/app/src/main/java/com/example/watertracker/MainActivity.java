package com.example.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        SimpleDateFormat datef = new SimpleDateFormat("yyyy년 MM월 dd일");
        Calendar c1 = Calendar.getInstance();
        String strDate = datef.format(c1.getTime());

        TextView date = (TextView)findViewById(R.id.txt_date);
        date.setText(strDate);

        float user_weight = 70; // 유저 몸무게
        float dailyGoal = 0; // 일일 권장량

        dailyGoal = user_weight * 30;

        int dailySum = 700;  // 일일 누적 음수량

        int dailyPercent =  (int)((dailySum/dailyGoal) *100); // 일일 누적 달성량
        int remaintogoal = (int)dailyGoal - dailySum; // 목표달성까지 남은 음수량



        TextView remainToGoal = (TextView)findViewById(R.id.txt_remaintToGoal);
        remainToGoal.setText("목표 달성까지 " + remaintogoal + "mL"  );


        TextView daily_allo = (TextView)findViewById(R.id.txt_allo);
        daily_allo.setText(dailyPercent+"%");

        ImageView waterdrop = (ImageView)findViewById(R.id.img_waterdrop);

        if(dailyPercent == 0) {
            waterdrop.setImageResource(R.drawable.waterdrop);
        } else if(0<dailyPercent && dailyPercent <= 10){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(10<dailyPercent && dailyPercent <= 20){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(20<dailyPercent && dailyPercent <= 30){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(30<dailyPercent && dailyPercent <= 40){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(40<dailyPercent && dailyPercent <= 50){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(50<dailyPercent && dailyPercent <= 60){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(60<dailyPercent && dailyPercent <= 70){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(70<dailyPercent && dailyPercent <= 80){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(80<dailyPercent && dailyPercent <= 90){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        }  else if(90 <= dailyPercent){
            waterdrop.setImageResource(R.drawable.waterdrop30);
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
            Intent intent = new Intent(MainActivity.this, Set_Push.class);
            startActivity(intent);
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
