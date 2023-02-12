package com.example.acdat_survivor_game.viewmodels;

import android.graphics.Canvas;

public abstract class Figure {
    private Integer x, y;

    public Figure(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public abstract void onDraw(Canvas canvas);

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
