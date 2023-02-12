package com.example.acdat_survivor_game.threads;

import com.example.acdat_survivor_game.viewmodels.Sprite;

public class CharacterThread extends Thread{

    private Sprite sprite;
    private Integer x, y;
    private Boolean running;

    public CharacterThread(Sprite sprite) {
        this.sprite = sprite;
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

    public void setPositionUpdated(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        run_thread();
    }

    private synchronized void run_thread(){
        while (running){

            sprite.setPositionUpdated(x, y);
            sprite.update();

            try {
                sleep(40);
            } catch (InterruptedException e) { }
        }
    }
}
