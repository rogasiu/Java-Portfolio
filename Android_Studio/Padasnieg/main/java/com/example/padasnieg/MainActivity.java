package com.example.padasnieg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private CustomView cView;
    Timer render;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cView = (CustomView) findViewById(R.id.CustomView);
        render = new Timer();

        render.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cView.invalidate();
            }

        }, 0, 10);

    }
}