package com.example.allegrorepos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    //method for changing the date format
    String changeFormat(String data) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = inputFormat.parse(data);
        String formattedDate = outputFormat.format(date);
        return formattedDate;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView nameText = (TextView) findViewById(R.id.text_name);
        TextView descriptionText = (TextView) findViewById(R.id.text_description);
        TextView languageText = (TextView) findViewById(R.id.text_language);
        TextView starsText = (TextView) findViewById(R.id.text_stars);
        TextView updatedText = (TextView) findViewById(R.id.text_updated);
        TextView createdText = (TextView) findViewById(R.id.text_created);
        ImageView avatarImg = (ImageView) findViewById(R.id.image_avatar_details);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Boolean isInternet = MainActivity.isInternetAvailable();
                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!isInternet){
                                Toast.makeText(DetailsActivity.this, "Brak połączenia z internetem!", Toast.LENGTH_SHORT).show();
                                DetailsActivity.super.onBackPressed();
                            }
                        }
                    });

                    //get selected name repository and avatar
                    String value = null;
                    Bitmap image = null;
                    Intent intent = getIntent();
                    if (intent != null) {
                        value = intent.getStringExtra("repo_name");
                        byte[] byteArray = intent.getByteArrayExtra("avatar");
                        image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    }

                    //get JSON selected repository
                    String allRepoData = MainActivity.getFromUrl("https://api.github.com/repos/allegro/"+value);
                    JSONObject repo = new JSONObject(allRepoData);

                    //get details about selected repository
                    String description = repo.get("description").toString();
                    String created = changeFormat(repo.get("created_at").toString());
                    String lastUpdate = changeFormat(repo.get("updated_at").toString());
                    String stars = repo.get("stargazers_count").toString();
                    String mainLanguage = repo.get("language").toString();
                    String name = value;
                    Bitmap avatar_details = image;

                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            avatarImg.setImageBitmap(avatar_details);
                            nameText.setText("Repozytorium\n"+name);
                            if(description == "null"){
                                descriptionText.setText("Brak");
                            }else {
                                descriptionText.setText(description);
                            }
                            if(mainLanguage == "null"){
                                languageText.setText("Brak");
                            }else {
                                languageText.setText(mainLanguage);
                            }
                            starsText.setText(stars);
                            updatedText.setText(lastUpdate);
                            createdText.setText(created);
                        }
                    });

                } catch (IOException | JSONException | ParseException e) {
                    e.printStackTrace();
                }catch (WrongResponeCodeException e) {
                    DetailsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        thread.start();

    }
}