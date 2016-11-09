package com.example.jesper.lab2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.ExpandableListView;

import java.lang.reflect.Array;
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

               if(paths.contains(in)) {
                   //Öppna gruppen
                   //Markera barnet/elementet
                   //expListView.expandGroup();
                   String[] split = in.split("/");
                   for(int i = 0; i<groups.size(); i++) {
                       for (int j = 0; j < children.get(i).size(); j++) {
                         //  System.out.println(groups.get(i) + " || " + children.get(i).get(j));
                           //System.out.println(split[0] + " :: " + split[1] + " :: " + split[2]);
                           if(groups.get(i).equals(split[1]) && children.get(i).get(j).equals(split[2])) {
                               //TODO: Markera barnet
                                expListView.expandGroup(i);
                               int index = expListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(i, j));
                               System.out.println(index + expListView.get);
                               expListView.setItemChecked(index, true);
                               return;
                           }
                       }
                   }

                  // System.out.println(groups.indexOf("B"));
               //    expListView.setSelectedChild(groups.indexOf(split[0]), children.get(groups.indexOf(split[0])).indexOf(split[1]), true);


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
                        input.addTextChangedListener(textListener);

                        selectItem(groupPos, childPos, child);

                        return false;
                    }
                }
        );

    }

    private void deselectAll() {
        //Gå igenom alla child och
        //view.setBackgroundResourse(R.color.colorPrimary)
        for(int i = 0; i < expListView.getChildCount(); i++){
            expListView.getChildAt(i).setBackgroundResource(0);

        }
    }

    private void selectItem(int groupPos, int childPos, View view) {
        deselectAll();
        expListView.setSelectedChild(groupPos, childPos, false);
        view.setBackgroundResource(R.color.colorPrimaryDark);
    }
    private void initData() {

        String[] groupsArray = {"B", "B", "C"};
        String[][] childrenArray = {
                {"a", "b", "c"},
                {"c"},
                {"d"}
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

    protected boolean validInput(String in) {

        return false;
    }
}
