package com.example.jesper.lab2;

/**
 * Created by Jesper on 2016-11-04.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groups;
     private ArrayList<List<String>> children;

    public MyAdapter(Context context, List<String> groups, ArrayList<List<String>> children) {
        this.context = context;
        this.groups = groups;
        this.children = children;
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        return children.get(groupPos).get(childPos);
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return childPos;
    }

    @Override
    public View getChildView(int groupPos, final int childPos, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPos, childPos);

        if (convertView == null) {
            LayoutInflater dicks4Lyfe = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = dicks4Lyfe.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPos) {
        return children.get(groupPos).size();
    }

    @Override
    public Object getGroup(int groupPos) {
        return groups.get(groupPos);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPos);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int childPos) {
        return true;
    }
}