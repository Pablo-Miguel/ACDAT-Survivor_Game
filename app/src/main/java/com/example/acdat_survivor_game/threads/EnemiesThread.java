package com.example.acdat_survivor_game.threads;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.viewmodels.Sprite;

public class EnemiesThread extends Thread{

    private Sprite enemy, character;
    private Integer x, y;
    private Boolean running;
    private Integer velocity, rnd_vel;
    private SurvivorView survivorView;

    public EnemiesThread(Sprite enemy, Sprite character, SurvivorView survivorView) {
        this.enemy = enemy;
        this.character = character;
        x = 0;
        y = 0;
        velocity = 3;
        running = false;
        this.survivorView = survivorView;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        run_thread();
    }

    private synchronized void run_thread(){
        while (running){

            switch (velocity){
                case 3:
                    rnd_vel = (int) Math.abs(Math.random() * (5-3) + 3);
                    break;
                case 5:
                    rnd_vel = (int) Math.abs(Math.random() * (7-5) + 5);
                    break;
                case 7:
                    rnd_vel = (int) Math.abs(Math.random() * (9-7) + 7);
                    break;
            }

            int difX = (character.getWidth()/2 + character.getX()) - (enemy.getWidth()/2 + enemy.getX());
            int difY = (character.getHeight()/2 + character.getY()) - (enemy.getHeight()/2 + enemy.getY());
            if (difX <= 5 && difX >= -5) x = 0;
            else if (difX < 0) x = -rnd_vel;
            else if(difX > 0) x = rnd_vel;

            if (difY <= 5 && difY >= -5) y = 0;
            else if (difY < 0) y = -rnd_vel;
            else if(difY > 0) y = rnd_vel;

            enemy.setPositionUpdated(x, y);
            enemy.update();

            try {
                sleep(20);
            } catch (InterruptedException e) { }
        }
    }

    public Sprite getEnemy() {
        return enemy;
    }

    public void setEnemy(Sprite enemy) {
        this.enemy = enemy;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }
}
