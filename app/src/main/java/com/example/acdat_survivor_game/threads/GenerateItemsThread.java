package com.example.acdat_survivor_game.threads;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.viewmodels.Image;
import com.example.acdat_survivor_game.viewmodels.Sprite;

import java.util.Random;

public class GenerateItemsThread extends Thread {

    Random rnd = new Random();
    private SurvivorView survivorView;
    private Boolean running;

    public GenerateItemsThread(SurvivorView survivorView) {
        this.survivorView = survivorView;
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
        run_thread();
    }

    private synchronized void run_thread(){
        while (running){

            if(rnd.nextInt(10) <= 5 && survivorView.getCoins().size() < 3){
                survivorView.getCoins().add(new Image(
                    rnd.nextInt(survivorView.getWidth() - 69),
                    rnd.nextInt(survivorView.getHeight() - 50),
                    survivorView.getResources(),
                    R.drawable.coin,
                        69,
                        50
                ));
            }

            if(rnd.nextInt(10) <= 5 && survivorView.getHearts().size() < 3){
                survivorView.getHearts().add(new Image(
                        rnd.nextInt(survivorView.getWidth() - 69),
                        rnd.nextInt(survivorView.getHeight() - 50),
                        survivorView.getResources(),
                        R.drawable.heart,
                        69,
                        50
                ));
            }

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
