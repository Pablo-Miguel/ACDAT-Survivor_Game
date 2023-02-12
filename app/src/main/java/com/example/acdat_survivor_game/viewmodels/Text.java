package com.example.acdat_survivor_game.viewmodels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class Text extends Figure {
    private String text;
    private Paint paint;

    public Text(Integer x, Integer y, String text) {
        super(x, y);
        this.text = text;
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        this.paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        this.paint.setTextSize(70);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(text, getX(), getY(), paint);
    }
}
