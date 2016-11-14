package com.example.jesper.lab3;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Jesper on 2016-11-12.
 */

/** TODO:
* Skapa sökruta
* Leta efter förslag
* Hämta (rätt) suggestions
* */
public class InteractiveSearcher extends LinearLayout {
    private EditText editText;
    private Suggestions suggestions;
    private String[] names;
    private int maxId = 0, currId = 0;

    private LayoutParams matchAndWrap = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private LayoutParams wrapAndWrap = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


    public InteractiveSearcher(Context context){
        super(context);
        editText = new EditText(context);

        setOrientation(VERTICAL);
        setLayoutParams(matchAndWrap);
        addView(editText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Be om suggestions
                if(s.length() > 0) {
                    new WordGetter(InteractiveSearcher.this).execute("http://flask-afteach.rhcloud.com/getnames/" + maxId + "/" + s);
                    maxId++;
                }
            }
        });
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int id){
        maxId = id;
    }
}
