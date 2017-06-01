package com.example.mskir.class13;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main2Activity extends AppCompatActivity {
    EditText et;
    TextView tv;
    static String urlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et = (EditText)findViewById(R.id.et);
        tv = (TextView)findViewById(R.id.tv);
    }

    public void onClick(View v){
        if(v.getId() == R.id.next){
            Intent intent = new Intent(this,Main3Activity.class);
            startActivity(intent);
            finish();
        }else{
            urlStr = et.getText().toString();
            thread.start();
        }
    }

    String readData(InputStream is){
        String data = "";
        Scanner s = new Scanner(is);
        while(s.hasNext()) data+=s.nextLine() + "\n";
        s.close();
        return data;
    }

    Handler myHandler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    final String data = readData(urlConnection.getInputStream());
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(data);
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
