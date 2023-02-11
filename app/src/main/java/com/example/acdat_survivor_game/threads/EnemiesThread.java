package com.example.acdat_survivor_game.threads;

import android.util.Log;

import com.example.acdat_survivor_game.viewmodels.Sprite;

import java.util.logging.Logger;

public class EnemiesThread extends Thread{

    private Sprite enemy, character;
    private Integer x, y;
    private Boolean running;

    public EnemiesThread(Sprite enemy, Sprite character) {
        this.enemy = enemy;
        this.character = character;
        x = 0;
        y = 0;
        running = false;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running){
            int difX = (character.getWidth()/2 + character.getX()) - (enemy.getWidth()/2 + enemy.getX());
            int difY = (character.getHeight()/2 + character.getY()) - (enemy.getHeight()/2 + enemy.getY());
            if (difX <= 1 && difX >= -1) x = 0;
            else if (difX < 0) x = -3;
            else if(difX > 0) x = 3;

            if (difY <= 1 && difY >= -1) y = 0;
            else if (difY < 0) y = -3;
            else if(difY > 0) y = 3;

            enemy.setPositionUpdated(x, y);
            enemy.update();

            try {
                sleep(20);
            } catch (InterruptedException e) { }
        }
    }

}
