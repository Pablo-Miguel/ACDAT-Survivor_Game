package com.example.acdat_survivor_game.viewmodels;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.threads.BulletThread;

import java.util.LinkedList;

public class Bullet {
    private Integer x, y;
    private int xSpeed, ySpeed;
    private static LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    private Bitmap bmp;
    private SurvivorView survivorView;
    private BulletThread bulletThread;
    private MediaPlayer mediaPlayer;

    public Bullet(Integer x, Integer y, Integer xSpeed, Integer ySpeed, Resources resources, SurvivorView survivorView, BulletThread bulletThread) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        bmp = BitmapFactory.decodeResource(resources, R.drawable.tear);
        bmp = bmp.createScaledBitmap(bmp, 60, 60, true);
        this.survivorView = survivorView;
        this.bulletThread = bulletThread;
    }

    public void update(){
        if(x > survivorView.getWidth() - bmp.getWidth() - xSpeed || x + xSpeed < 0){
            reciclar();
        }

        if(y > survivorView.getHeight() - bmp.getHeight() - ySpeed || y + ySpeed < 0){
            reciclar();
        }

        for (int i = 0; i < survivorView.getEnemies().size(); i++) {
            if(survivorView.getEnemies().get(i).isCollidingBullet(this)){
                reciclar();
                survivorView.getEnemies().get(i).removeHP(1);
                mediaPlayer = MediaPlayer.create(survivorView.getContext(), R.raw.hit_zombie);
                mediaPlayer.start();
            }
        }

        x = x + xSpeed;
        y = y + ySpeed;
    }

    public void onDraw(Canvas canvas){
        canvas.drawBitmap(bmp, x, y, null);
    }

    public static Bullet getBullet(int x, int y, Integer xSpeed, Integer ySpeed, Resources resources, SurvivorView survivorView, BulletThread bulletThread) {

        if (bullets.isEmpty())
            return new  Bullet(x, y, xSpeed, ySpeed, resources, survivorView, bulletThread);

        Bullet bullet = bullets.removeFirst();
        bullet.x = x;
        bullet.y = y;
        bullet.xSpeed = xSpeed;
        bullet.ySpeed = ySpeed;
        bullet.survivorView = survivorView;
        bullet.bulletThread = bulletThread;
        return bullet;
    }

    public void reciclar() {
        survivorView.getBullets().remove(this);
        bulletThread.setRunning(false);
        survivorView.getBulletsThreads().remove(bulletThread);
        bullets.add(this);
    }

    public static void borrarRecicladas() {
        bullets.clear();
    }

    public static LinkedList<Bullet> getBullets() {
        return bullets;
    }

    public static void setBullets(LinkedList<Bullet> bullets) {
        Bullet.bullets = bullets;
    }

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


    public double getWidth() {
        return bmp.getWidth();
    }

    public double getHeight() {
        return bmp.getHeight();
    }
}
