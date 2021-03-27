package com.example.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Random;

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

    Paint paintRect, paintBall, paintPallets, paintLine;
    float xP, yP, xK, yK;
    float dX, dY;
    private int pointUp, pointDown;
    int xPallet, xKPallet;
    int znak;
    int licznik = 0;
    boolean isNew = true;
    boolean addPU = false;
    boolean addPD = false;


    void init() {

        paintRect = new Paint();
        paintBall = new Paint();
        paintPallets = new Paint();
        paintLine = new Paint();

        paintBall.setColor(Color.WHITE);
        paintBall.setStyle(Paint.Style.FILL);

        paintPallets.setColor(Color.WHITE);
        paintPallets.setStyle(Paint.Style.FILL);

        paintRect.setColor(Color.CYAN);
        paintRect.setStyle(Paint.Style.STROKE);
        paintRect.setStrokeWidth(3);

        paintLine.setColor(Color.MAGENTA);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setPathEffect(new DashPathEffect(new float[] {10f, 20f}, 0f));

        pointUp = 0;
        pointDown = 0;

        xPallet = 0;
        xKPallet = 200;

    }
    void moveLeft() {
        if(xPallet != 0) {
            xPallet--;
            xKPallet--;
        }
    }
    void moveRight(){
        if (xKPallet != getWidth()) {
            xPallet++;
            xKPallet++;
        }
    }

    void poruszanie(){

        xP += dX;
        xK += dX;
        yP += dY;
        yK += dY;
        if ((xK <= xKPallet && xP >= xPallet) && (yK >= getHeight()-30 || yP <= 30)) {
            //dY *= 1.10;
            dY = -dY;
        } else if ((xK >= getWidth() || xP <= 0) && (yK <= getHeight()-30 || yP >= 30)) {
            //dX *= 1.10;
            dX = -dX;
        } else if (((xK > xKPallet && xP > xKPallet) ||(xK < xPallet && xP < xPallet)) && (yK >= getHeight()-30 || yP <= 30)) {
            if (yK >= getHeight()-30) {
                addPU = true;
            } else if (yP <= 30) {
                addPD = true;
            }
            isNew = true;
        }
        licznik++;
        zwiekszSzybk();
    }

    void zwiekszSzybk() {
        if (licznik == 1000) {
         dX *= 1.10;
         dY *= 1.10;
         licznik = 0;
        }
    }


    void startKwadratAndPallets(){
        if(isNew) {
            xP = (float) getWidth()/2-25;
            yP = (float) getHeight()/2-25;
            xK = (float) getWidth()/2 + 25;
            yK = (float) getHeight()/2 + 25;

            if (addPD) {
                pointDown++;
                addPD = false;
            } else if (addPU) {
                pointUp++;
                addPU = false;
            }
            dX = 1;
            dY = 1;
            znak = new Random().nextInt(4);
            if (znak == 0) {
                dX = -dX;
                dY = -dY;
            } else if (znak == 1) {
                dX = -dX;
            } else if (znak == 2) {
                dY = -dY;
            }
            licznik = 0;
            isNew = false;
        }
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        startKwadratAndPallets();
        canvas.drawRect(xP,yP,xK,yK, paintBall);
        canvas.drawRect(1,1, getWidth()-2, getHeight()-1, paintRect);
        canvas.drawRect(xPallet, 5, xKPallet, 30,paintPallets);
        canvas.drawRect(xPallet, getHeight()-30, xKPallet, getHeight()-5, paintPallets);
        canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, paintLine);

    }

    int getPointUp() {
        return this.pointUp;
    }
    int getPointDown() {
        return this.pointDown;
    }

}
