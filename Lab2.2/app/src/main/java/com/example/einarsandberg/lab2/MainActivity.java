package com.example.einarsandberg.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.EditText;
import android.text.TextWatcher;
import android.graphics.Color;
import android.util.Log;
public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity"; // log tag
    List<String> titles;
    HashMap<String, List<String>> children;
    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    EditText searchField;
    int prevExpandedPosition;
    int prevSelectedChild;
    int prevSelectedGroup;

    static final int POSSIBLE_CHILD = -1;
    static final int NO_POSSIBLE_CHILD = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        prevExpandedPosition = -1;
        prevSelectedChild = -1;
        prevSelectedGroup = -1;
        setContentView(R.layout.activity_main);

        searchField = (EditText) findViewById(R.id.search);

        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        initList();

        listView.setSelector(R.drawable.item_selector);

        listAdapter = new ExpandableListAdapter(this, titles, children);

        listView.setAdapter(listAdapter);
        listView.setFocusableInTouchMode(true);
        listView.setItemsCanFocus(true);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                searchField.setText("/" + titles.get(groupPosition) + "/");
                return true;
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                searchField.setText("/" + titles.get(groupPosition) + "/" + listAdapter.getChild(groupPosition, childPosition));
                return true;
            }
        });


        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

               // prevSelectedGroup = groupPosition;
                listView.clearChoices();
            }
        });

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {
                listView.clearChoices();
                prevSelectedChild = -1;
            }

        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if (!(s.toString().equals("")) && s.charAt(0) == '/')
                {
                    String groupName = getGroupName(s.toString());
                    String childName = getChildName(s.toString());
                    int childPosition;
                    int groupPosition = getGroupPosition(groupName);
                    // if group matches or can match
                    if (!groupName.equals(""))
                    {

                        if (groupPosition != -1) {
                            listView.expandGroup(groupPosition);
                            searchField.setBackgroundColor(Color.parseColor("#ffffff"));
                            childPosition = getChildPosition(groupName, childName);
                            // there is a match
                            if (childPosition > POSSIBLE_CHILD) {

                                searchField.setBackgroundColor(Color.parseColor("#ffffff"));
                                prevSelectedChild = childPosition;
                                prevSelectedGroup = groupPosition;
                                int i = listView.getFlatListPosition(
                                        ExpandableListView.getPackedPositionForChild(prevSelectedGroup, prevSelectedChild));
                                listView.setItemChecked(i, true);
                            }
                            // there is a possible match
                            else if (childPosition == POSSIBLE_CHILD) {
                                searchField.setBackgroundColor(Color.parseColor("#ffffff"));
                                if (prevSelectedChild != -1)
                                {

                                    int i = listView.getFlatListPosition(
                                            ExpandableListView.getPackedPositionForChild(prevSelectedGroup, prevSelectedChild));
                                    listView.setItemChecked(i, true);
                                }

                            }
                            // no possible match
                            else if (childPosition == NO_POSSIBLE_CHILD) {
                                searchField.setBackgroundColor(Color.RED);
                                listView.collapseGroup(groupPosition);
                            }
                        } else // groupPosition == -1, no possible group
                        {
                            searchField.setBackgroundColor(Color.RED);
                            listView.clearChoices();
                        }
                    }
                    else
                    {
                        searchField.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }

                else if (s.toString().equals(""))
                {
                    searchField.setBackgroundColor(Color.RED);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    private void initList()
    {
        titles = new ArrayList<String>();
        children = new HashMap<String, List<String>>();

        // add some titles
        titles.add("Kebab");
        titles.add("Pizza");
        titles.add("Falafel");
        titles.add("Hamburgare");

        List <String> kebabChildren = new ArrayList<String>();
        List <String> pizzaChildren = new ArrayList<String>();
        List <String> falafelChildren = new ArrayList<String>();
        List <String> hamburgerChildren = new ArrayList<String>();

        //add some children
        kebabChildren.add("Kebabrulle");
        kebabChildren.add("Kebabtallrik");
        kebabChildren.add("Kebabrulle");
        pizzaChildren.add("Margarita");
        pizzaChildren.add("Vesuvio");
        pizzaChildren.add("Hawaii");
        pizzaChildren.add("Ciao Ciao");
        falafelChildren.add("Falafelrulle");
        falafelChildren.add("Falafeltallrik");
        hamburgerChildren.add("Baconburgare");
        hamburgerChildren.add("Cheeseburgare");

        children.put(titles.get(0), kebabChildren);
        children.put(titles.get(1), pizzaChildren);
        children.put(titles.get(2), falafelChildren);
        children.put(titles.get(3), hamburgerChildren);

    }

    private String getGroupName(String s)
    {

        if (s.charAt(0) == '/') {
            //remove first '/'
            s = s.substring(1, s.length());

            if (s.contains("/")) {
                int i = s.indexOf("/");
                s = s.substring(0, i);

            }
        }
        return s;
    }

    private String getChildName(String s)
    {
        if (s.charAt(0) == '/')
        {
            s = s.substring(1, s.length());

            if (s.contains("/"))
            {
                int i = s.indexOf("/");
                s = s.substring(i+1, s.length());
            }
            // no child
            else
            {
                return "";
            }
        }
        return s;
    }

    private int getGroupPosition(String group)
    {
        int groupPosition = -1;
        if (!group.equals(""))
        {
            for (int i = 0; i < titles.size(); i++)
            {
                if (titles.get(i).startsWith(group))
                {
                    groupPosition = i;
                    break;
                }
            }
        }
        return groupPosition;
    }

    private int getChildPosition(String group, String child)
    {
        int childPosition = -2; // -1 will be returned if no possibility of match
        // check if there is a child in the text field, and get its position if so
        if (child.equals(""))
            childPosition = POSSIBLE_CHILD;

        else
        {
            if (children.get(group) != null)
            {
                for (int i = 0; i < children.get(group).size(); i++)
                {
                    // match
                    if (children.get(group).get(i).equals(child))
                    {
                        childPosition = i;
                        break;
                    }
                    else if (children.get(group).get(i).startsWith(child))
                    {
                        childPosition = POSSIBLE_CHILD;
                    }
                }

            }
            else
            {
                childPosition = NO_POSSIBLE_CHILD;
            }
        }
        return childPosition;
    }
}
