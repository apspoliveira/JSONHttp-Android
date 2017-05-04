package com.example.jsonhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        System.out.println("Going to execute getIP");
        new GetIp().execute("https://api.ipify.org?format=json");
    }

    class GetIp extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... fileUrl) {
            String readStream = "";
            try {
                System.out.println(fileUrl[0]);
                URL url = new URL(fileUrl[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                readStream = readStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return readStream;
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Resposta JSON recebida com sucesso!", Toast.LENGTH_LONG).show();
            textView.setText(result);
        }
    }

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                System.out.println(nextLine);
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}