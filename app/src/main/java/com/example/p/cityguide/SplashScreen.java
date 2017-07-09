package com.example.p.cityguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    private final String fault = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                    String id = sharedPreferences.getString("category", fault);
                    if (id.equals(fault)) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Homepage.class);
                        startActivity(intent);
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
}
