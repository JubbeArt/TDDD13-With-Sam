package com.example.jesper.lab1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //setContentView(R.layout.activity_main);
        LinearLayout layout = new LinearLayout(this);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setOrientation(LinearLayout.VERTICAL);

        //Objects
       LinearLayout.LayoutParams matchAndRap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button button = new Button(this);
        button.setText("KNAPP");
        button.setLayoutParams(matchAndRap);
        layout.addView(button);

        EditText editText = new EditText(this);
        editText.setText("Textfält");
        editText.setLayoutParams(matchAndRap);
        layout.addView(editText);

        RatingBar ratingBar = new RatingBar(this);
        ratingBar.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ratingBar.setNumStars(5);


        layout.addView(ratingBar);

        EditText textField = new EditText(this);
        textField.setText("Flerradigt \n asdv");
        textField.setLayoutParams(matchAndRap);
        layout.addView(textField);

        setContentView(layout);
    }
}
