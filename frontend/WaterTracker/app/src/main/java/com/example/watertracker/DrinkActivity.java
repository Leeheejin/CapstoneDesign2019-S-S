package com.example.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.watertracker.R;

public class DrinkActivity extends AppCompatActivity {

    Button pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        pass = (Button) findViewById(R.id.pass_btn2);

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DrinkActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
