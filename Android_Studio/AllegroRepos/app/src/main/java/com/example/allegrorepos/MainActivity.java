package com.example.allegrorepos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //method checks the connection to the internet
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    //method checks if it can connect to the given path and if it is possible
    //download the content from the page
    public static String getFromUrl(String path) throws IOException, WrongResponeCodeException {
        //String authToken = "token ...";
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //con.setRequestProperty("Authorization", authToken);
        con.setRequestMethod("GET");

        int response = con.getResponseCode();
        if(response != 200)
        {
            throw new WrongResponeCodeException("Response code: "+response);
        }else{
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            return content.toString();
        }
    }

    //method for downloading Allegro avatar from GitHub profile
    Bitmap getBitmapFromURL(String src) throws IOException {
        URL url = new URL(src);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
    }

    //method to add repos name to the list
    List<String> getRepos(String raw) throws JSONException {
        List<String> result = new ArrayList<>();
        JSONArray mainArr = new JSONArray(raw);
        for(int i=0; i<mainArr.length(); i++)
        {
            JSONObject obj = (JSONObject) mainArr.get(i);
            result.add((String)obj.get("name"));
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgAv = (ImageView) findViewById(R.id.image_avatar);
        TextView headerText = (TextView) findViewById(R.id.text_info);
        ListView lv = (ListView) findViewById(R.id.listView);
        TextView loading = (TextView) findViewById(R.id.text_loading);
        Intent intent = new Intent(this, DetailsActivity.class);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //checking internet connection
                    Boolean isInternet = isInternetAvailable();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!isInternet){
                                Toast.makeText(MainActivity.this, "Brak połączenia z internetem!", Toast.LENGTH_SHORT).show();
                                loading.setText("Połącz się z internetem i uruchom ponownie aplikację.");
                            }
                        }
                    });

                    //get numbers of repos from allegro account
                    String user = getFromUrl("https://api.github.com/users/allegro");
                    JSONObject userInfo = new JSONObject(user);
                    String numberRepos = userInfo.get("public_repos").toString();

                    //get Allegro avatar from JSONObject
                    String avatarUrl = userInfo.get("avatar_url").toString();
                    Bitmap avatar = getBitmapFromURL(avatarUrl);

                    //get all repos
                    String raw = getFromUrl("https://api.github.com/users/allegro/repos?per_page="+numberRepos);
                    List<String> allRepos = getRepos(raw);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                loading.setVisibility(View.INVISIBLE);
                                imgAv.setImageBitmap(avatar);
                                headerText.setText("Repozytoria GitHub na profilu Allegro");
                                lv.setAdapter(new MyListAdapter(MainActivity.this, R.layout.list_item, allRepos));
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        String value = allRepos.get(position);
                                        startActivityFromMainThread(value, avatar);
                                    }
                                });
                            }
                        });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } catch (WrongResponeCodeException e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            loading.setText("Wystąpił błąd. Zrestartuj aplikację lub spróbuj ponownie później.");
                        }
                    });
                }
            }
        });
        thread.start();
    }

    //method for switching to second activity and add extended data to the intent
    public void startActivityFromMainThread(String repo, Bitmap avatar){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    avatar.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    //passing the repo name and avatar to the DetailsActivity
                    Intent intent = new Intent (MainActivity.this, DetailsActivity.class);
                    intent.putExtra("repo_name", repo);
                    intent.putExtra("avatar", byteArray);
                    startActivity(intent);
            }
        });
    }

    //class which creates elements in ListView
    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;
        private List<String> mObjects;

        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            this.mObjects = objects;
            this.layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.title.setText(getItem(position));
            return convertView;
        }
    }
    public class ViewHolder{
        TextView title;
    }


}