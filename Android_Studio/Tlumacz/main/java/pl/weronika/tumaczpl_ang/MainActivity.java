package pl.weronika.tumaczpl_ang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText odpow;
    TextView punkty;
    TextView liczbaSlow;
    TextView slowo;
    TextView odpowiedz;
    Button button;
    String[] slowka;
    String[] angielskie;
    String wylosowane;
    String angOdpow;
    int random;
    int punktIle = 0;
    int wyrazKtory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        odpow = findViewById(R.id.tlumaczenie);
        punkty = findViewById(R.id.pktText);
        liczbaSlow = findViewById(R.id.ktoryText);
        slowo = findViewById(R.id.slowoText);
        odpowiedz = findViewById(R.id.odpText);
        button = findViewById(R.id.button);

        slowka = new String[] {"kot", "pies", "gorąco", "pomarańcz", "jedzenie",
        "kurczak", "koń", "twarz", "oko", "królik", "owca"};
        angielskie = new String[] {"cat", "dog", "hot", "orange", "food",
                "chicken", "horse", "face", "eye", "rabbit", "sheep"};

        random = (int)(Math.random()*slowka.length);
        wylosowane = slowka[random];
        angOdpow = angielskie[random];
        slowo.setText(wylosowane);
        punkty.setText(Integer.toString(punktIle));
        liczbaSlow.setText(Integer.toString(wyrazKtory));

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean checkInput = TextUtils.isEmpty(odpow.getText().toString());
                if (checkInput) {
                    odpowiedz.setText("Wpisz tlumaczenie!");
                } else {
                    String przetl = odpow.getText().toString();
                    odpowiedz.setText(checkAns(angOdpow, przetl));
                    random = (int)(Math.random()*slowka.length);
                    wylosowane = slowka[random];
                    angOdpow = angielskie[random];
                    slowo.setText(wylosowane);


                }

            }
        });



    }
    public String checkAns(String wzor, String answer) {
        String wynik;
        if (wzor.equals(answer)) {
            wynik = "Poprawna odpowiedź. Wylosowano nowe słowo.";
            punktIle++;
            wyrazKtory++;
            punkty.setText(Integer.toString(punktIle));
            liczbaSlow.setText(Integer.toString(wyrazKtory));
            return wynik;
        } else {
            wynik = "Odpowiedź niepoprawna. Wylosowano nowe słowo.";
            wyrazKtory++;
            liczbaSlow.setText(Integer.toString(wyrazKtory));
            return wynik;
        }
    }
}