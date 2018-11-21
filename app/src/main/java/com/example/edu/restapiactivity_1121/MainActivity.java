package com.example.edu.restapiactivity_1121;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_london;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_london = (Button) findViewById(R.id.btn_london);
        btn_london.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View v) {
       OpenWeatherAPITask task = new OpenWeatherAPITask();
        try {
            String weather = task.execute("London").get();
            textView.setText(weather);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class OpenWeatherAPITask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String uriString = "http://api.openweathermap.org/data/2.5/weather?q="+id+"&appid=b791b62e686765c075b69f52c6525a5e";
            URL url = null;
            String weather = null;
            try {
                url = new URL(uriString);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                byte[] buffer = new byte[1000];
                in.read(buffer);
                weather = new String(buffer);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weather;
        }
    }
}
