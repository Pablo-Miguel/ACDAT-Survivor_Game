package com.example.acdat_survivor_game.threads;

import com.example.acdat_survivor_game.viewmodels.Bullet;

public class BulletThread extends Thread{

    private Bullet bullet;
    private Boolean running;

    public BulletThread() {
        running = false;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
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

            bullet.update();

            try {
                sleep(5);
            } catch (InterruptedException e) { }
        }
    }

}
