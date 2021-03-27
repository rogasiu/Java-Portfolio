package com.example.pong;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private CustomView cView;
    Timer timBall, timButton;
    Button left, right;
    TextView pointU, pointD;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cView = (CustomView) findViewById(R.id.CustomView);
        timBall = new Timer();
        left = findViewById(R.id.button);
        right = findViewById(R.id.button2);
        pointU = findViewById(R.id.textView);
        pointD = findViewById(R.id.textView2);
        pointU.setText("0");
        pointD.setText("0");
        timButton = new Timer();


        timBall.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cView.poruszanie();
                cView.invalidate();
                runOnUiThread(() -> {
                    pointU.setText(String.valueOf(cView.getPointUp()));
                    pointD.setText(String.valueOf(cView.getPointDown()));
                });


            }

        }, 0, 5);


        left.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    timButton.scheduleAtFixedRate(new TimerTask(){
                        @Override
                        public void run(){
                            cView.moveLeft();
                            cView.invalidate();
                        }
                    },0,2);
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(timButton != null) {
                        timButton.cancel();
                        timButton.purge();
                        timButton = new Timer();
                    }
                }
                return true;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    timButton.scheduleAtFixedRate(new TimerTask(){
                        @Override
                        public void run(){
                            cView.moveRight();
                            cView.invalidate();
                        }
                    },0,2);
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(timButton != null) {
                        timButton.cancel();
                        timButton.purge();
                        timButton = new Timer();
                    }
                }
                return true;
            }
        });



    }
}