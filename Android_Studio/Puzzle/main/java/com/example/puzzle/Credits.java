package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Credits extends AppCompatActivity {
    TextView napisy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        napisy = findViewById(R.id.textView8);
        napisy.setText("Dziękuję wszystkim za uruchomienie tych puzzli. To wiele dla mnie znaczy w" +
                " tworzeniu mojej kariery programisty puzzli. Dziękuję za wsparcie i wieczne przeszkadzanie " +
                "moim dwóm kotom: Czesterowi i Karmelowi. To dzięki Wam tworzenie tej gry trwało tak długo. " +
                "Wasze wieczne zabawo-walki sprawiają, że latające kłębki futra przypominają mi te wysuszone " +
                "krzaczki, które przecinają drogi na sawannach. Czasami zastanawiam się jak to jest, że wy dalej " +
                "nie jesteście już całkowicie łyse, patrząc ile tych kłaków się wszędzie pojawia... " +
                "Cóż, koty skrywają wiele sekretów.");
    }
}