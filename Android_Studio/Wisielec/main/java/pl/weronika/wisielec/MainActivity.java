package pl.weronika.wisielec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText literaEd;
    TextView probaTxt;
    TextView wyrazTxt;
    TextView uzyteLitTxt;
    TextView informacje;
    ImageView obrazek;
    Button button;

    String[] slowa;
    int losowa;
    String losoweSlowo;
    List<String> szukane = new ArrayList<String>();
    boolean koniecZgadyw = false;
    String uzyteLitery = new String();

    int proba = 0;
    int pozostalo = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        literaEd = findViewById(R.id.literaEdit);
        probaTxt = findViewById(R.id.probaText);
        wyrazTxt = findViewById(R.id.szukaneText);
        uzyteLitTxt = findViewById(R.id.odgadnieteText);
        informacje = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        obrazek = findViewById(R.id.imageView);


        slowa = new String[] {"pegaz", "kot", "uczulenie", "osa", "drozd", "wirus",
        "automat", "drzewo", "gwiazda", "kometa", "woda", "druid", "czarownica", "kluska",
        "rzeka", "geografia", "fantastyka", "brzoza", "grzyb"};
        losowa = (int)(Math.random() * slowa.length);
        losoweSlowo = slowa[losowa];
        pusteLiterki(losoweSlowo);
        ustawObrazek(pozostalo);
        //pozostaleProbyTxt.setText(Integer.toString(pozostalo));
        probaTxt.setText(Integer.toString(proba));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean sprLiter = TextUtils.isEmpty(literaEd.getText());
                if (sprLiter) {
                    //nowy text edit do wyswietlania komunikatow
                    informacje.setText("Wpisz literkę!");
                } else {
                    String lit = literaEd.getText().toString();
                    //funkcja sprawdzająca? i wywowałująca kolejne funkcje
                    if (sprawdz(lit) && koniecZgadyw == false) {
                        //funkcja sprawdzajaca i wpisujaca
                        informacje.setText("");
                        uzyteLitery += lit + ", ";
                        uzyteLitTxt.setText(uzyteLitery);
                        wypelnianie(lit);
                    } else if (koniecZgadyw == true) {
                        losowa = (int)(Math.random() * slowa.length);
                        losoweSlowo = slowa[losowa];
                        proba = 0;
                        pozostalo = 7;
                        ustawObrazek(pozostalo);
                        //pozostaleProbyTxt.setText(Integer.toString(pozostalo));
                        probaTxt.setText(Integer.toString(proba));
                        uzyteLitery = "";
                        uzyteLitTxt.setText(uzyteLitery);
                        informacje.setText("");
                        pusteLiterki(losoweSlowo);
                        koniecZgadyw = false;
                    }
                }
            }
        });

    }


    void pusteLiterki(String litery) {
        for (int i = 0; i < litery.length(); i++) {
            szukane.add("-");
        }
        String wynik = new String();
        for (String kol : szukane) {
            wynik += kol + " ";
        }
        wyrazTxt.setText(wynik);
    }

    void wypelnianie(String litera) {
        litera.toLowerCase();
        int tempZycia = 0;
        for (int i = 0; i < losoweSlowo.length(); i++) {

            if (litera.equals(String.valueOf(losoweSlowo.charAt(i)))) {
                szukane.set(i, litera);
                informacje.setText("Poprawna literka");
            } else {
                tempZycia++;
            }
        }
        proba++;

        if (tempZycia == losoweSlowo.length()) {
            pozostalo--;
        }

        if (pozostalo == 0) {
            informacje.setText("Przegrałeś! Skończyły Ci się próby. Zacznij grę od nowa!");
            koniecZgadyw = true;
            szukane.clear();
        }
        ustawObrazek(pozostalo);
        //pozostaleProbyTxt.setText(Integer.toString(pozostalo));
        probaTxt.setText(Integer.toString(proba));

        String wynik = new String();
        for (String kol : szukane) {
            wynik += kol + " ";
        }
        wyrazTxt.setText(wynik);
        String sprawdz = new String();
        for (String kol : szukane) {
            sprawdz += kol;
        }
        if (sprawdz.equals(losoweSlowo)) {
            koniecZgadyw = true;
            szukane.clear();
            informacje.setText("Brawo, odgadłeś!");
        }
    }
    boolean sprawdz(String wejscie) {
        wejscie.toLowerCase();
        if (wejscie.length() > 1) {
            //wpisz tylko 1 litere
            informacje.setText("Wpisz tylko 1 literę!");
            return false;
        } else {
            for (int i = 0; i < wejscie.length(); i++) {
                char c = wejscie.charAt(i);
                if (!(c >= 'a' && c <= 'z')) {
                    //wpisz wylacznie litere
                    informacje.setText("Wpisz literę nie cyfrę.");
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
    void ustawObrazek(int proba){
        switch(proba) {
            case 7:
                obrazek.setImageResource(R.drawable.wisielec0);
                break;
            case 6:
                obrazek.setImageResource(R.drawable.wisielec1);
                break;
            case 5:
                obrazek.setImageResource(R.drawable.wisielec2);
                break;
            case 4:
                obrazek.setImageResource(R.drawable.wisielec3);
                break;
            case 3:
                obrazek.setImageResource(R.drawable.wisielec4);
                break;
            case 2:
                obrazek.setImageResource(R.drawable.wisielec5);
                break;
            case 1:
                obrazek.setImageResource(R.drawable.wisielec6);
                break;
            case 0:
                obrazek.setImageResource(R.drawable.wisielec7);
                break;

        }
    }
}