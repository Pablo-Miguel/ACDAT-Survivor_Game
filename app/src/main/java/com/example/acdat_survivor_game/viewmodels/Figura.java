package com.example.acdat_survivor_game.viewmodels;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Objects;

public abstract class Figura {
    private int id;
    private float pos_X, pos_Y;
    private Paint paint;
    private boolean mover;

    public Figura(int id, float pos_X, float pos_Y, int color) {
        this.id = id;
        this.pos_X = pos_X;
        this.pos_Y = pos_Y;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setColor(color);
    }

    public Figura(int id, float pos_X, float pos_Y, int color, Paint.Style style, boolean mover) {
        this.id = id;
        this.pos_X = pos_X;
        this.pos_Y = pos_Y;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(style);
        this.paint.setColor(color);
        this.paint.setStrokeWidth(10);
        this.mover = mover;
    }

    public Figura(int id, float pos_X, float pos_Y) {
        this.id = id;
        this.pos_X = pos_X;
        this.pos_Y = pos_Y;
        this.paint = null;
    }

    public abstract void onDraw(Canvas canvas);

    public abstract boolean isTouched(int coord_X, int coord_y);

    public abstract boolean isHover(Figura figura);

    public void setPositionUpdated(int coord_X, int coord_y){
        this.setPos_X(this.getPos_X() + coord_X);
        this.setPos_Y(this.getPos_Y() + coord_y);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPos_X() {
        return pos_X;
    }

    public void setPos_X(float pos_X) {
        this.pos_X = pos_X;
    }

    public float getPos_Y() {
        return pos_Y;
    }

    public void setPos_Y(float pos_Y) {
        this.pos_Y = pos_Y;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public boolean isMover() {
        return mover;
    }

    public void setMover(boolean mover) {
        this.mover = mover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Figura)) return false;
        Figura figura = (Figura) o;
        return getId() == figura.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Figura{" +
                "id=" + id +
                ", pos_X=" + pos_X +
                ", pos_Y=" + pos_Y +
                '}';
    }
}
