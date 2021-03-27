package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity {
    Button yes, no, menu;
    Intent menus, puzzles, credits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        yes = findViewById(R.id.button);
        no = findViewById(R.id.button2);
        menu = findViewById(R.id.button7);
        menus = new Intent(this, MainActivity.class);
        puzzles = new Intent(this, Puzzle.class);
        credits = new Intent(this, Credits.class);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(puzzles);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(credits);
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(menus);
                finish();
            }
        });
    }
}