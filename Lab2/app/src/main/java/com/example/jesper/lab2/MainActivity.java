package com.example.jesper.lab2;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    MyAdapter listAdapter;
    ExpandableListView expListView;

    List<String> groups;
    ArrayList<List<String>> children;

    Set<String> paths;

    int currentGroup = -1, currentChild = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = (ExpandableListView) findViewById(R.id.expandable);

        initData();

        listAdapter = new MyAdapter(this, groups, children);
        expListView.setAdapter(listAdapter);

        final EditText input = (EditText) findViewById(R.id.inp);


        final  TextWatcher textListener = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String in = s.toString();

                // Found exact match
                if(paths.contains(in)) {

                   String[] split = in.split("/");

                    // Find first right node
                    for(int i = 0; i< groups.size(); i++) {
                       for (int j = 0; j < children.get(i).size(); j++) {
                           if(groups.get(i).equals(split[1]) && children.get(i).get(j).equals(split[2])) {
                               currentGroup = i;
                               currentChild = j;
                               expListView.expandGroup(i);
                               input.setBackgroundColor(Color.WHITE);
                               return;
                           }
                       }
                   }
               } else { // Might be an ok path
                   for(String path: paths) {
                       if(path.startsWith(in)) { // Substring = ok path
                           input.setBackgroundColor(Color.WHITE);
                           return;
                       }
                   }

                    // No substring found = bad path
                   expListView.setItemChecked(-1, true);
                   input.setBackgroundColor(Color.RED);
               }

            }
        };
        input.addTextChangedListener(textListener);

        expListView.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View child, int groupPos, int childPos, long id) {
                        // Ta bort textWatcher temporärt (så vi inte kollar saker två gånger)
                        input.removeTextChangedListener(textListener);
                        input.setText("/" + groups.get(groupPos) + "/" + children.get(groupPos).get(childPos) );
                        input.setBackgroundColor(Color.WHITE);
                        input.addTextChangedListener(textListener);

                        setCurrentMarked(groupPos, childPos);

                        return false;
                    }
                }
        );

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                // Collapse all other groups
                for(int i = 0; i < groups.size(); i++) {
                    if(i != groupPosition)
                        expListView.collapseGroup(i);
                }

                // Remove all selected itmes
                for(int i = 0; i < expListView.getChildCount(); i++)
                    expListView.setItemChecked(i, false);

                // Add the selected item if the group is right
                if(currentGroup == groupPosition)
                    setCurrentMarked(currentGroup, currentChild);


            }
        });
    }

    private void setCurrentMarked(int groupPos, int childPos) {
        if(groupPos == -1)
            return;

        currentChild = childPos;
        currentGroup = groupPos;

        int index = expListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPos, childPos));
        expListView.setItemChecked(index, true);

    }

    private void initData() {

        String[] groupsArray = {"apa",	"banan", "apa"};
        String[][] childrenArray = {
                {"harambe", "nicke", "king", "kong"},
                {"gul", "integul", "omogen"},
                {"harambe", "bing", "kong"}
        };

        groups = Arrays.asList(groupsArray);
        children = new ArrayList<>();

        for(String[] childArray : childrenArray)
            children.add(Arrays.asList(childArray));

        paths = new HashSet<>();

        for(int i = 0; i < groups.size(); i++){
            for(int j = 0; j < children.get(i).size(); j++) {
                paths.add("/" + groups.get(i) + "/" + children.get(i).get(j));
            }
        }
    }

}
