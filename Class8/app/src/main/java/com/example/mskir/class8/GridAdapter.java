package com.example.mskir.class8;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mskir on 2017-04-27.
 */

public class GridAdapter extends BaseAdapter {
       Context context;
    ArrayList<Fruit> fruit;

    public GridAdapter(ArrayList<Fruit> fruit,Context context){
        this.fruit = fruit;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fruit.size();
    }

    @Override
    public Object getItem(int position) {
        return fruit.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
           if(convertView == null)
               convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
           final TextView tv = (TextView)convertView.findViewById(R.id.tvname);
          final ImageView image = (ImageView)convertView.findViewById(R.id.img1);
            tv.setText(fruit.get(position).name);
             image.setImageResource(fruit.get(position).imgno);

        return convertView;
    }
}
