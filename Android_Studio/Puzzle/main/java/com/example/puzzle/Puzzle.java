package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Puzzle extends AppCompatActivity {
    Intent finished, over;
    Chronometer czas;
    ImageView im0, im1, im2, im3, im4,im5, im6, im7, im8;
    Button up, down, left, right;
    TextView licznik;
    Timer timer;
    Drawable tmp, d0, d1, d2, d3, d4, d5, d6, d7, d8;
    CharSequence formatka, minutes;
    Drawable[] puz;
    int xPos, yPos, xMove, yMove;
    int liczRuchy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        finished = new Intent(Puzzle.this, FinishedPuzzle.class);
        over = new Intent(this, GameOver.class);

        String obrazek = getIntent().getStringExtra("zwierze");
        pobierzObrazki(obrazek);
        final MediaPlayer sound = MediaPlayer.create(this, R.raw.meow);
        xPos = 0;
        yPos = 0;
        xMove = 0;
        yMove = 0;
        liczRuchy = 0;
        timer = new Timer();

        im0 = findViewById(R.id.img0);
        im1 = findViewById(R.id.img1);
        im2 = findViewById(R.id.img2);
        im3 = findViewById(R.id.img3);
        im4 = findViewById(R.id.img4);
        im5 = findViewById(R.id.img5);
        im6 = findViewById(R.id.img6);
        im7 = findViewById(R.id.img7);
        im8 = findViewById(R.id.img8);
        up = findViewById(R.id.button3);
        down = findViewById(R.id.button6);
        left = findViewById(R.id.button4);
        right = findViewById(R.id.button5);
        licznik = findViewById(R.id.textView4);
        czas = findViewById(R.id.simpleChronometer);

        int czasRozg = getIntent().getIntExtra("czasRozgrywki", 0);
        switch (czasRozg){
            case 15:
                minutes = "14:59";
                break;
            case 7:
                minutes = "06:59";
                break;
            case 1:
                minutes = "00:59";
                break;
            default:
                minutes = "09:59";
                break;
        }
        czas.start();

        d0.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        losowanie();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkTime();
            }
        }, 0 , 500);

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                liczRuchy++;
                yMove -= 1;
                update();
                check();

            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                liczRuchy++;
                yMove += 1;
                update();
                check();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                liczRuchy++;
                xMove -= 1;
                update();
                check();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.start();
                liczRuchy++;
                xMove += 1;
                update();
                check();
            }
        });

    }
    void checkTime() {
        formatka = czas.getText();
        if(formatka.toString().compareTo(minutes.toString()) > 0) {
            timer.cancel();
            startActivity(over);
        }
    }

    void update() {
        int oldXPos = xPos;
        int oldYPos = yPos;

        xPos += xMove;
        yPos += yMove;

        if(xPos > 2) {
            xPos = 2;
        } else if (xPos < 0) {
            xPos = 0;
        }
        if(yPos > 2) {
            yPos = 2;
        } else if (yPos < 0) {
            yPos = 0;
        }
        tmp = puz[yPos*3 + xPos];
        puz[yPos*3 + xPos] = puz[oldYPos*3 + oldXPos];
        puz[oldYPos*3 + oldXPos] = tmp;
        xMove = 0;
        yMove = 0;
        change();


    }

    void change(){
        im0.setImageDrawable(puz[0]);
        im1.setImageDrawable(puz[1]);
        im2.setImageDrawable(puz[2]);
        im3.setImageDrawable(puz[3]);
        im4.setImageDrawable(puz[4]);
        im5.setImageDrawable(puz[5]);
        im6.setImageDrawable(puz[6]);
        im7.setImageDrawable(puz[7]);
        im8.setImageDrawable(puz[8]);
        licznik.setText(String.valueOf(liczRuchy));

    }

    void check() {
        if(puz[0].equals(d0) && puz[1].equals(d1) && puz[2].equals(d2)
        && puz[3].equals(d3) && puz[4].equals(d4) && puz[5].equals(d5)
        && puz[6].equals(d6) && puz[7].equals(d7) && puz[8].equals(d8)) {
            timer.cancel();
            formatka = czas.getText();
            finished.putExtra("formatka", formatka);
            finished.putExtra("liczRuchy", liczRuchy);
            startActivity(finished);
            liczRuchy = 0;
        }
    }
    void losowanie() {
        for (int i =0; i < 50; i++) {
            if((int)(Math.random() * 2) == 1){
                xMove = 1;
            }else {
                xMove = -1;
            }
            if((int)(Math.random() * 2) == 1){
                yMove = 1;
            }else{
                yMove = -1;
            }
            update();
        }
    }
    void pobierzObrazki(String obrazek) {
        Bitmap mBitmap;
        switch (obrazek) {
            case "pies":
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
                break;
            case "kot":
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
                break;
            case "wilk":
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wolf);
                break;
            case "lis":
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fox);
                break;
            default:
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
                break;
        }

        List<Bitmap> kafle = new ArrayList<>();
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                Bitmap b = Bitmap.createBitmap(mBitmap, j*(mBitmap.getWidth()/3),
                        i*(mBitmap.getHeight()/3),(mBitmap.getWidth()/3), (mBitmap.getHeight()/3));
                kafle.add(Bitmap.createScaledBitmap(b, 100, 100, false));
            }
        }
        d0 = new BitmapDrawable(getResources(), kafle.get(0));
        d1 = new BitmapDrawable(getResources(), kafle.get(1));
        d2 = new BitmapDrawable(getResources(), kafle.get(2));
        d3 = new BitmapDrawable(getResources(), kafle.get(3));
        d4 = new BitmapDrawable(getResources(), kafle.get(4));
        d5 = new BitmapDrawable(getResources(), kafle.get(5));
        d6 = new BitmapDrawable(getResources(), kafle.get(6));
        d7 = new BitmapDrawable(getResources(), kafle.get(7));
        d8 = new BitmapDrawable(getResources(), kafle.get(8));
        puz = new Drawable[]{d0, d1, d2, d3, d4, d5, d6, d7, d8};

    }
}