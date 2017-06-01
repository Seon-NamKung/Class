package com.example.mskir.class13;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.etmsg);
    }

    public void onClick(View v){
        if(v.getId() == R.id.next){
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
            finish();
        }
        else{
            msg = e1.getText().toString();
        }
    }

    String SERVER_IP = "172.17.67.14";
    int SERVER_PORT = 200;
    String msg = "";

    Handler myHandler = new Handler();
    Thread myThread = new Thread(){
        @Override
        public void run() {
            try {
                Socket socket = new Socket(SERVER_IP,SERVER_PORT);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                outputStream.writeObject(msg);
                outputStream.flush();

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                final String data = (String)inputStream.readObject();

                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Server>>>" + data,Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };
}
