package com.example.acdat_survivor_game.viewmodels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.example.acdat_survivor_game.surfaceviews.SurvivorView;

public class Sprite {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private int x;
    private int y;
    private int xSpeed, ySpeed;
    private SurvivorView survivorView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private int[] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};
    private Paint p;

    public Sprite(SurvivorView survivorView, Bitmap bmp) {
        this.survivorView = survivorView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        x = (survivorView.getWidth() / 2) - width;
        y = (survivorView.getHeight() / 2) - height;
        xSpeed = 5;
        ySpeed = 5;
        p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public void setPositionUpdated(int x, int y) {
        this.xSpeed = x;
        this.ySpeed = y;
    }

    private void update(){
        int w_margin = 150;
        int h_margin = 50;
        if(x > survivorView.getWidth() - width - xSpeed + w_margin || x + xSpeed < 0 - w_margin){
            xSpeed = 0;
        }

        if(y > survivorView.getHeight() - height - ySpeed + h_margin || y + ySpeed < 0 - h_margin){
            ySpeed = 0;
        }

        x = x + xSpeed;
        y = y + ySpeed;

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas){
        update();

        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        if(xSpeed == 0 && ySpeed == 0){
            srcX = 1 * width;
            srcY = 0 * height;
        }

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
        canvas.drawText("NitroSlinger", x + 135, y + 50, p);
    }

}
