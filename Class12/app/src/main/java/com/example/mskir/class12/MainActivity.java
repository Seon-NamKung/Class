package com.example.mskir.class12;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    int index = 0;

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv.setText("숫지 : " + index);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.text1);

    }

    public void onClick(View v) {
        ThreadTest th = new ThreadTest();
        th.setDaemon(true);
        th.start();
    }

    public class ThreadTest extends Thread{
        @Override
        public void run() {
            for(index = 1; index<11; index++){
                try {
                    Thread.sleep(1000);
                    Message msg = myHandler.obtainMessage();
                    myHandler.sendMessage(msg);
                    /*
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(" 숫자 : " + index);
                        }
                    });*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
