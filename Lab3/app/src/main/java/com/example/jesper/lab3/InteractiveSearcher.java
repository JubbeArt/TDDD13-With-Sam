package com.example.jesper.lab3;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;

import static android.text.InputType.TYPE_CLASS_TEXT;

/**
 * Created by Jesper & Samuel on 2016-11-12.
 */

public class InteractiveSearcher extends LinearLayout {
    private EditText editText;
    private Suggestions suggestions;
    private int maxId = 0, currId = -1;

    private LayoutParams matchAndWrap = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

    public InteractiveSearcher(Context context){
        super(context);
        editText = new EditText(context);
        editText.setMaxLines(1);
        editText.setInputType(TYPE_CLASS_TEXT);

        setOrientation(VERTICAL);
        setLayoutParams(matchAndWrap);
        addView(editText);

        suggestions = new Suggestions(context, this);
        addView(suggestions);
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                //Be om suggestions
                if(s.length() > 0) {
                    new WordGetter(InteractiveSearcher.this).execute(
                            "http://flask-afteach.rhcloud.com/getnames/" + maxId + "/" + s
                    );
                    maxId++;
                } else
                    setSuggestions(new JSONArray());
            }
        });
    }

    public void setSuggestions(JSONArray names) {
        suggestions.setSuggestions(names);
    }

    public int getCurrId() {
        return currId;
    }

    public void setCurrId(int id) {
        currId = id;
    }

    public void setText(String word) {
        editText.setText(word);
        editText.setSelection(word.length());
    }

}
