package com.example.mskir.class8;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by mskir on 2017-04-27.
 */

public class AddFruit extends LinearLayout implements View.OnClickListener{
    int imageno = 0;
    EditText et;
    ImageView img;
    Button b_next,b_add;

    public AddFruit(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.fruitadd,this);
        et = (EditText)findViewById(R.id.f_name);
        img = (ImageView)findViewById(R.id.image1);
        b_next = (Button)findViewById(R.id.b_next);
        b_add = (Button)findViewById(R.id.b_add);
        b_next.setOnClickListener(this);
        b_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == b_add){
            onAddListener.onAdd(et.getText().toString(),3);
        }

    }

    interface OnAddListener{
        void onAdd(String name, int imageno);
    }
    public OnAddListener onAddListener;

    public void setOnAddListener(OnAddListener onAddListener){
        this.onAddListener = onAddListener;
    }
}
