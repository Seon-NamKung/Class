package com.example.mskir.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.acl.*;
import java.util.ArrayList;

public class ExpandableActivity extends AppCompatActivity {
    ArrayList<Group> group;
    ArrayList<Child> child;
    ArrayList<ArrayList<Child>> childList;
    ExpandableListView elistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        group = new ArrayList<Group>();
        child = new ArrayList<Child>();
        childList = new ArrayList<ArrayList<Child>>();
        elistView = (ExpandableListView)findViewById(R.id.exListView);

        group.add(new Group("Elephant"));
        group.add(new Group("Rabbit"));
        group.add(new Group("Cat"));
        group.add(new Group("Dog"));

        child.add(new Child("진수",7));
        child.add(new Child("선",5));
        child.add(new Child("상지",6));
        child.add(new Child("지웅",3));

        childList.add(child);
        childList.add(child);
        childList.add(child);

        elistView.setAdapter(new ExpandableAdapter(this,group,childList));


        elistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getApplicationContext(), "g click = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }
}
