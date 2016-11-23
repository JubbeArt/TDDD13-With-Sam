package com.example.jesper.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Jesper & Samuel on 2016-11-14.
 */

public class Suggestions extends View{

    protected String[] names;
    protected InteractiveSearcher searcher;
    protected int textSize = 25, textHeight, textWidth, width;
    protected Paint textPaint, black;

    protected LinearLayout.LayoutParams zeroSize = new LinearLayout.LayoutParams(0,0);

    public Suggestions(Context context, final InteractiveSearcher searcher){
        super(context);
        this.searcher = searcher;

        Rect bounds = new Rect();
        textPaint = new Paint();

        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.parseColor("#aa0033"));
        textPaint.setAntiAlias(true);
        textPaint.getTextBounds("W", 0, 1, bounds);

        textHeight = bounds.height()+15;
        textWidth = bounds.width();
        width = textWidth * 8;

        black = new Paint();
        black.setColor(Color.parseColor("#000000"));

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                int y = (int) e.getY();
                int wordNr = y / textHeight;

                searcher.setText(names[wordNr]);
                return false;
            }
        });
    }

    public void setSuggestions(JSONArray names){
        this.names = new String[names.length()];
        for(int i = 0; i < names.length(); i++){
            try {
                this.names[i] = (String) names.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (names == null || names.length == 0) {
            setLayoutParams(zeroSize);
            return;
        }
        int height = 15 + textHeight * names.length;

        setLayoutParams(new LinearLayout.LayoutParams(width, height));
        canvas.drawRect(0, 0, width, height, black);

        for (int i = 0; i < names.length; i++) {
            canvas.drawText(names[i], 0, textHeight * (i+1), textPaint);
        }
    }
}
