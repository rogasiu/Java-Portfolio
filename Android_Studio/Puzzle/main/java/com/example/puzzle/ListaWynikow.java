package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaWynikow extends AppCompatActivity {
    Button addNick, mainMenu, endGame;
    EditText nickl;
    ListView lista;
    TextView informacja;
    ArrayAdapter<String> adapter;
    List<String> tabWyn = new ArrayList<String>();
    Intent main, credits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_wynikow);

        DatabaseHandler db = new DatabaseHandler(this);
        addNick = findViewById(R.id.button8);
        mainMenu = findViewById(R.id.back);
        endGame = findViewById(R.id.endGame);
        nickl = findViewById(R.id.inNick);
        lista = findViewById(R.id.listaWyn);
        main = new Intent(this, MainActivity.class);
        credits = new Intent(this, Credits.class);

        informacja = findViewById(R.id.info);
        CharSequence czas = getIntent().getCharSequenceExtra("czas");


        addNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sprLiter = TextUtils.isEmpty(nickl.getText());
                if (sprLiter) {
                    //nowy text edit do wyswietlania komunikatow
                    informacja.setText("Wpisz nick!");
                } else {
                    String nick = nickl.getText().toString();
                    db.addWynik(new Wyniki(nick, czas));
                    List<Wyniki> wszystkie = db.getAllWyniki();

                    for (Wyniki wy : wszystkie) {
                        tabWyn.add("Id: " + wy.getId() + " , Nick: " + wy.getNickName() + " , Time: " +
                                wy.getTime());
                    }
                    adapter = new ArrayAdapter<String>(ListaWynikow.this, R.layout.row, tabWyn);
                    lista.setAdapter(adapter);
                }
            }
        });
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(main);
                finish();
            }
        });
        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(credits);
                finish();
            }
        });


    }
}