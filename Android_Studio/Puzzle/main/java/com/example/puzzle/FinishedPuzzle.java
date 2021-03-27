package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FinishedPuzzle extends AppCompatActivity {
    Intent newPuzzle, endSub, mainMenu, wyniki;
    TextView wRuchy, wCzas;
    CharSequence czas;
    int ruchy;
    Button yes, no, menu, dodajWynik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_puzzle);

        newPuzzle = new Intent(FinishedPuzzle.this, Puzzle.class);
        endSub = new Intent(this, Credits.class);
        mainMenu = new Intent(this, MainActivity.class);
        wyniki = new Intent(this, ListaWynikow.class);


        wRuchy = findViewById(R.id.liczbRuch);
        wCzas = findViewById(R.id.czasUklad);

        ruchy = getIntent().getIntExtra("liczRuchy", 0);
        czas = getIntent().getCharSequenceExtra("formatka");
        wCzas.setText(String.valueOf(czas));
        wRuchy.setText(String.valueOf(ruchy));

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        menu = findViewById(R.id.menu);
        dodajWynik = findViewById(R.id.wynik);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newPuzzle);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(endSub);
                finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainMenu);
                finish();
            }
        });
        dodajWynik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wyniki.putExtra("czas", czas);
                startActivity(wyniki);
                finish();
            }
        });


    }
}