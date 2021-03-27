package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class CheckPicture extends AppCompatActivity {
    ImageView pies, kot, lis, wilk;
    Button bPies, bKot, bLis, bWilk;
    Intent puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_picture);

        pies = findViewById(R.id.dog);
        kot = findViewById(R.id.cat);
        lis = findViewById(R.id.fox);
        wilk = findViewById(R.id.wolf);
        bPies = findViewById(R.id.bPies);
        bKot = findViewById(R.id.bKot);
        bLis = findViewById(R.id.bLis);
        bWilk = findViewById(R.id.bWilk);
        puzzle = new Intent(this, Puzzle.class);
        int czasRozg = getIntent().getIntExtra("czasRozgrywki", 0);

        bPies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puzzle.putExtra("zwierze", "pies");
                puzzle.putExtra("czasRozgrywki", czasRozg);
                startActivity(puzzle);
                finish();
            }
        });
        bKot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puzzle.putExtra("zwierze", "kot");
                puzzle.putExtra("czasRozgrywki", czasRozg);
                startActivity(puzzle);
                finish();
            }
        });
        bWilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puzzle.putExtra("zwierze", "wilk");
                puzzle.putExtra("czasRozgrywki", czasRozg);
                startActivity(puzzle);
                finish();
            }
        });
        bLis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                puzzle.putExtra("zwierze", "lis");
                puzzle.putExtra("czasRozgrywki", czasRozg);
                startActivity(puzzle);
                finish();
            }
        });

    }
}