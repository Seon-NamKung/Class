package com.example.mskir.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mskir on 2017-04-13.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private ArrayList<Group> group;
    private ArrayList<ArrayList<Child>> childlist;
    private LayoutInflater inflater;
    private RestAdapter.ViewHolder viewHolder;

    public ExpandableAdapter(Context c, ArrayList<Group> group,ArrayList<ArrayList<Child>> childlist){
        super();
        this.inflater = LayoutInflater.from(c);
        this.group = group;
        this.childlist = childlist;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childlist.get(groupPosition).size();
    }

    @Override
    public Group getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Child getChild(int groupPosition, int childPosition) {
        return childlist.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null){
            viewHolder = new RestAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_item,parent,false);
            viewHolder.title = (TextView)v.findViewById(R.id.tvname);
            viewHolder.tel = (TextView)v.findViewById(R.id.call);
            v.setTag(viewHolder);
            ///여기서 끝남 http://arabiannight.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9CAndroid-ExpandableListView-%EB%A7%8C%EB%93%A4%EA%B8%B0
        }else{
            viewHolder = (RestAdapter.ViewHolder)v.getTag();
        }

        viewHolder.title.setText(getGroup(groupPosition).getName());
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v==null){
            viewHolder = new RestAdapter.ViewHolder();
            v = inflater.inflate(R.layout.list_item,null);
            viewHolder.title = (TextView)v.findViewById(R.id.tvname);
            v.setTag(viewHolder);
        }else{
            viewHolder = (RestAdapter.ViewHolder)v.getTag();
        }

        viewHolder.title.setText(getChild(groupPosition,childPosition).getName());
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
