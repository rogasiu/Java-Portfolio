package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button bLatwy, bNormalny, bTrudny;
    Intent puzzle, obrazek;
    int czasRozgrywki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        puzzle = new Intent(this, Puzzle.class);
        obrazek = new Intent(this, CheckPicture.class);

        bLatwy = findViewById(R.id.latwy);
        bNormalny = findViewById(R.id.normalny);
        bTrudny = findViewById(R.id.trudny);

        bLatwy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                czasRozgrywki = 15;
                obrazek.putExtra("czasRozgrywki", czasRozgrywki);
                startActivity(obrazek);
                finish();
            }
        });

        bNormalny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                czasRozgrywki = 7;
                obrazek.putExtra("czasRozgrywki", czasRozgrywki);
                startActivity(obrazek);
                finish();
            }
        });

        bTrudny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                czasRozgrywki = 1;
                obrazek.putExtra("czasRozgrywki", czasRozgrywki);
                startActivity(obrazek);
                finish();
            }
        });
    }
}