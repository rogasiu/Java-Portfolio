package com.example.padasnieg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.sql.DriverManager.println;

public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    int height;
    int width, widthF;

    List<Integer> padaY = new ArrayList<Integer>();
    List<Integer> padaX = new ArrayList<Integer>();
    List<Integer> szybX = new ArrayList<Integer>();
    List<Float> szybY = new ArrayList<Float>();
    List<Integer> iksy = new ArrayList<Integer>();
    List<Integer> igreki = new ArrayList<Integer>();

    int kierunek;
    int temporary = 0;
    boolean startt = true;
    Paint bialy = new Paint();

    public void init() {
        bialy.setColor(Color.WHITE);

    }
    public void start() {
        if(startt) {
            List<Integer> pom = new ArrayList<Integer>();
            for(int i = 0; i <= width; i++) {
                pom.add(i);
            }
            for(int i = 0; i <= width; i++) {
                    pom.add(i);
            }
            for(int i = 0; i <= width; i++) {
                pom.add(i);
            }
            //Collections.shuffle(pom);
            padaX.addAll(pom);
            Collections.shuffle(padaX);
            startt = false;
        }
    }
    public void scroll() {

        //padaX.add((int)(Math.random() * width));
        //padaY.add(1);

    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        height = getHeight();
        width = getWidth();
        widthF = width-5;

        start();
        //scroll();
        padaY.add(1);

        szybX.add((int)(Math.random() * widthF));
        szybY.add((float)1);

        for (int i = 0; i < padaY.size(); i++) {
            int x = padaX.get(i);
            int y = padaY.get(i);
            if(y+5 >= height) {
                iksy.add(x);
                igreki.add(y);
                padaX.remove(i);
                padaY.remove(i);
                continue;
            } else {
                int size = iksy.size();
                for(int j = 0; j < size; j++) {
                    if(y+5 == igreki.get(j) && (x+5 == iksy.get(j)+5)) {
                        iksy.add(x);
                        igreki.add(y);
                        padaX.remove(i);
                        padaY.remove(i);
                        continue;
                    }
                }
            }
            kierunek = (int)(Math.random() * 3);
            if (kierunek == 0) {
                x += 1;
                padaX.set(i, x);
            }else if (kierunek == 1) {
                x -= 1;
                padaX.set(i, x);
            }
            canvas.drawRect(x, y , x+5, y+5, bialy);
            y += 1;
            padaY.set(i, y);
        }
        int temp = iksy.size();
        for (int i = 0; i < temp; i++) {
            int tmpY = igreki.get(i);
            canvas.drawRect(iksy.get(i), igreki.get(i),iksy.get(i) + 7, igreki.get(i) + 7,bialy);
        }
        for (int i = 0; i < szybX.size(); i++) {
            int x = szybX.get(i);
            float y = szybY.get(i);
            kierunek = (int)(Math.random() * 3);
            if (kierunek == 0) {
                x += 1;
                szybX.set(i, x);
            }else if (kierunek == 1) {
                x -= 1;
                szybX.set(i, x);
            }
            canvas.drawRect(x, y , x+3, y+3, bialy);
            y += 0.75;
            if(y >= height) {
                szybX.remove(i);
                szybY.remove(i);
            }else {
                szybY.set(i, y);
            }

        }
        if(padaX.size() <= height) {
            startt = true;
        }
    }

}
