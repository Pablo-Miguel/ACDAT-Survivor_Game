package com.example.acdat_survivor_game.viewmodels;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;

import com.example.acdat_survivor_game.R;
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
    private int[] DIRECTION_TO_ANIMATION_MAP;
    private Integer hp, max_hp;
    private int w_margin = 150;
    private int h_margin = 50;

    public Sprite(int x, int y, SurvivorView survivorView, Resources resources, int resource, int w, int h, int[] DIRECTION_TO_ANIMATION_MAP, int hp) {
        this.survivorView = survivorView;
        bmp = BitmapFactory.decodeResource(resources, resource);
        bmp = bmp.createScaledBitmap(bmp, w, h, true);
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.x = x;
        this.y = y;
        xSpeed = 5;
        ySpeed = 5;
        this.DIRECTION_TO_ANIMATION_MAP = DIRECTION_TO_ANIMATION_MAP;
        this.max_hp = hp;
        this.hp = hp;
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

    public void update(){

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
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        if(xSpeed == 0 && ySpeed == 0){
            srcX = 1 * width;
            srcY = 0 * height;
        }

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean isHover(Sprite sprite) {

        double centroX = (width / 2) + x;
        double centroY = (height / 2) + y;

        double centroXR = (sprite.getWidth() / 2) + sprite.getX();
        double centroYR = (sprite.getHeight() / 2) + sprite.getY();

        double distanciaPuntos = Math.sqrt(Math.pow(centroXR - centroX, 2) + Math.pow(centroYR - centroY, 2));

        if(distanciaPuntos < (width / 2.8)){
            return true;
        }

        return false;
    }

    public boolean isCollidingBullet(Bullet bullet) {

        double centroX = (width / 2) + x;
        double centroY = (height / 2) + y;

        double centroXR = (bullet.getWidth() / 2) + bullet.getX();
        double centroYR = (bullet.getHeight() / 2) + bullet.getY();

        double distanciaPuntos = Math.sqrt(Math.pow(centroXR - centroX, 2) + Math.pow(centroYR - centroY, 2));

        if(distanciaPuntos < (width/2)){
            return true;
        }

        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Boolean isDead() {
        if(hp <= 0){
            return true;
        }
        return false;
    }

    public Integer getHP(){
        return this.hp;
    }

    public void setHP(Integer hp){
        this.max_hp = hp;
        this.hp = hp;
    }

    public Integer getMaxHP(){
        return this.max_hp;
    }

    public void giveHP(Integer num_lives) {
        if(this.hp + num_lives <= max_hp){
            this.hp += num_lives;
        }
    }

    public void removeHP(Integer num_lives) {
        this.hp -= num_lives;
    }
}
