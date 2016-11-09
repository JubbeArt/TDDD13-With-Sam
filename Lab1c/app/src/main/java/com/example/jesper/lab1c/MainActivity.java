package com.example.jesper.lab1c;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout.LayoutParams wrapAndWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private LinearLayout.LayoutParams matchAndWrap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        createQuestion(layout, "Hur trivs du på LiU?", new String[] {"Bra", "Mycket Bra", "Jättebra"});
        createQuestion(layout, "Läser du på LiTH?", new String[] {"Ja", "Nej"});

        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams imageParams = wrapAndWrap;
        imageParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageParams);
        imageView.setBackgroundResource(R.drawable.logga);
        layout.addView(imageView);

        createQuestion(layout, "Är detta LiUs logotyp?", new String[] {"Ja", "Nej"});
        Button button = new Button(this);
        button.setLayoutParams(matchAndWrap);
        button.setText("Skicka in");
        layout.addView(button);

        setContentView(layout);
    }

    protected void createQuestion(LinearLayout layout, String text, String[] answer){
        TextView textView = new TextView(this);
        textView.setLayoutParams(matchAndWrap);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        layout.addView(textView);

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setLayoutParams(matchAndWrap);
        for(String ans: answer){
            RadioButton rButton = new RadioButton(this);
            rButton.setLayoutParams(wrapAndWrap);
            rButton.setText(ans);
            radioGroup.addView(rButton);
        }
        layout.addView(radioGroup);
    }
}
