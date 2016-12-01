package com.example.jesper.lab3;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout lin = new LinearLayout(this);
        lin.setOrientation(LinearLayout.VERTICAL);

        InteractiveSearcher is = new InteractiveSearcher(this);
        lin.addView(is);

        Button b = new Button(this);
        lin.addView(b);

        setContentView(lin);
    }
}
