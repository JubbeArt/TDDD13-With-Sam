package tddc73.lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import tddc73.lab2.ExpandableListAdapter;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    EditText editText;
    boolean listen = true;
    int listSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        //get the edittext
        editText = (EditText) findViewById(R.id.editText);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                searchForText(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                editText.setText("/" + listDataHeader.get(groupPosition) + "/" + listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                int index = expListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                expListView.setItemChecked(index, true);

                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (listen) {
                    for (int i = 0; i < listDataHeader.size(); i++) {
                        if (groupPosition != i)
                            expListView.collapseGroup(i);
                    }
                    editText.setText("/" + listDataHeader.get(groupPosition));
                }
            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                if(listen) {
                    editText.setText("/");
                }
            }
        });
    }

    private void searchForText(Editable s) {
        String[] parts = s.toString().split("/");
        int parts_length = parts.length;
        int number_of_slashes = s.toString().length() - s.toString().replace("/", "").length();
        boolean search_bool = false;
        boolean search_bool_key = false;
        boolean starts_with = false;
        int group_position = -1;
        int child_position = -1;

        for(int i = 0; i < parts_length; i++)
            Log.d("Parts", "part" + i + " : " + parts[i]);

        Log.d("Parts", "length: " + parts_length);
        Log.d("Slashes", "length: " + number_of_slashes);
        for(Map.Entry<String, List<String>> entry : listDataChild.entrySet()) {
            String key = entry.getKey();

            if(number_of_slashes > 1 && key.equals(parts[1])) {
                Log.d("search_key", "search_bool_key true, parts[1]: " + parts[1]);
                search_bool_key = true;
            }

            if(parts_length > 2) {
                for(String val : entry.getValue()) {
                    if (key.equals(parts[1]) && val.startsWith(parts[2]) && parts[2].length() > 0) {
                        starts_with = true;
                        Log.d("parts", "parts[2].length: " + parts[2].length());
                        Log.d("key", "key equals, val startswith: " + parts[2]);
                        Log.d("val", "val = " + val);
                        if(val.equals(parts[2])) {
                            Log.d("val", "val equals: " + parts[2]);
                            search_bool = true;
                            group_position = listDataHeader.indexOf(parts[1]);
                            child_position = listDataChild.get(key).indexOf(val);
                        }
                    }
                }
            }
            else if(parts_length > 1) {
                if(key.startsWith(parts[1]) && parts_length == 2 && number_of_slashes < parts_length) {
                    starts_with = true;
                    if(key.equals(parts[1])) {
                        search_bool = true;
                        group_position = listDataHeader.indexOf(parts[1]);
                    }
                }
                else if(number_of_slashes == parts_length && key.equals(parts[1]))
                    starts_with = true;
            }
        }

        Log.d("starts_with", "starts_with: " + starts_with);
        if(search_bool && search_bool_key) {
            editText.setBackgroundColor(Color.WHITE);
            listen = false;
            for(int i = 0; i < listDataHeader.size(); i++) {
                expListView.collapseGroup(i);
            }

            expListView.expandGroup(group_position);
            int index = expListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(group_position, child_position));
            expListView.setItemChecked(index, true);
            listen = true;
        }
        else if(starts_with || parts_length == 0 || (search_bool_key && number_of_slashes <= 1)) {
            editText.setBackgroundColor(Color.WHITE);
        }
        else {
            for(int i = 0; i < listSize; i++) {
                expListView.setItemChecked(i, false);
            }

            editText.setBackgroundColor(Color.RED);
        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("light");
        listDataHeader.add("medium");
        listDataHeader.add("dark");

        // Adding child data
        List<String> light = new ArrayList<String>();
        light.add("green");
        light.add("yellow");
        light.add("red");
        light.add("blue");

        List<String> medium = new ArrayList<String>();
        medium.add("green");
        medium.add("yellow");
        medium.add("red");
        medium.add("red");
        medium.add("blue");

        List<String> dark = new ArrayList<String>();
        dark.add("green");
        dark.add("yellow");
        dark.add("red");
        dark.add("blue");

        listDataChild.put(listDataHeader.get(0), light); // Header, Child data
        listDataChild.put(listDataHeader.get(1), medium);
        listDataChild.put(listDataHeader.get(2), dark);

        listSize = light.size() + medium.size() + dark.size();
    }
}