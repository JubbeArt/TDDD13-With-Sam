package com.example.einarsandberg.lab2;

import android.widget.BaseExpandableListAdapter;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by einarsandberg on 2015-11-17.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<String> titles;
    private HashMap<String, List<String>> children;

    public ExpandableListAdapter(Context theContext, List<String> theTitles,
                                 HashMap<String, List<String>> theChildren)
    {
        context = theContext;
        titles = theTitles;
        children = theChildren;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        String childData = (String) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView childTextView = (TextView) convertView.findViewById(R.id.listItem);
        childTextView.setText(childData);
        return convertView;

    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent)
    {
        String titleData = (String) getGroup(groupPosition);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        titleTextView.setText(titleData);
        return convertView;
    }
    @Override
    public Object getGroup(int groupPosition)
    {
        return titles.get(groupPosition);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return children.get(titles.get(groupPosition)).get(childPosition);
    }
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
    @Override
    public boolean hasStableIds()
    {
        return false;
    }
    @Override
    public int getGroupCount()
    {
        return titles.size();
    }
    @Override
    public int getChildrenCount(int groupPosition)
    {
        return children.get(titles.get(groupPosition)).size();
    }





}
