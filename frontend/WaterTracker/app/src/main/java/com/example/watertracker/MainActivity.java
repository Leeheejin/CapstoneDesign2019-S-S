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

        int remaintogoal = 700;

        TextView remainToGoal = (TextView)findViewById(R.id.txt_remaintToGoal);
        remainToGoal.setText("목표 달성까지 " + remaintogoal + "mL"  );

        int allo = 30;

        TextView daily_allo = (TextView)findViewById(R.id.txt_allo);
        daily_allo.setText(allo+"%");

        ImageView waterdrop = (ImageView)findViewById(R.id.img_waterdrop);

        if(allo>=30) {
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(0<allo && allo <= 10){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(10<allo && allo <= 20){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(20<allo && allo <= 30){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(30<allo && allo <= 40){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(40<allo && allo <= 50){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(50<allo && allo <= 60){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(60<allo && allo <= 70){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(70<allo && allo <= 80){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        } else if(80<allo && allo <= 90){
            waterdrop.setImageResource(R.drawable.waterdrop30);
        }  else if(90 <= allo){
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
