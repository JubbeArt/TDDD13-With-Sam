package com.example.jesper.lab1b;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

public class MainActivity extends AppCompatActivity {
    private LinearLayout.LayoutParams matchAndWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams hardAndWrap = new LinearLayout.LayoutParams(350, ViewGroup.LayoutParams.WRAP_CONTENT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int password = TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD;
        int mail = TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        int text = TYPE_CLASS_TEXT;
        layout.addView(createLayout("Namn", createEditText(text)));
        layout.addView(createLayout("Lösenord", createEditText(password)));
        layout.addView(createLayout("E-post", createEditText(mail)));

        SeekBar alder = new SeekBar(this);
        alder.setLayoutParams(matchAndWrap);
        layout.addView(createLayout("Ålder", alder));

        setContentView(layout);
    }

    protected TextView createTextField(String text) {
        TextView textView  = new TextView(this);
        textView.setText(text);
        textView.setLayoutParams(hardAndWrap);
        return textView;
    }

    protected LinearLayout createLayout(String text, View view){
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(matchAndWrap);
        layout.addView(createTextField(text));

        layout.addView(view);
        return layout;
    }

    protected EditText createEditText(int type) {
        EditText editText = new EditText(this);
        editText.setLayoutParams(matchAndWrap);
        editText.setInputType(type);
        return editText;
    }
}