package com.example.acdat_survivor_game.threads;

import android.media.MediaPlayer;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.viewmodels.Sprite;


public class RemoveItemsThread extends Thread {
    MediaPlayer mediaPlayer;
    private SurvivorView survivorView;
    private Sprite character;
    private Boolean running;

    public RemoveItemsThread(SurvivorView survivorView, Sprite character) {
        this.survivorView = survivorView;
        this.character = character;
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

            for(int i = 0; i < survivorView.getCoins().size(); i++){
                if(survivorView.getCoins().get(i).isHover(character)){
                    mediaPlayer = MediaPlayer.create(survivorView.getContext(), R.raw.coin);
                    mediaPlayer.start();
                    survivorView.getCoins().remove(survivorView.getCoins().get(i));
                    survivorView.addCoins_cont(1);
                }
            }

            for(int i = 0; i < survivorView.getHearts().size(); i++){
                if(survivorView.getHearts().get(i).isHover(character) && character.getHP() < character.getMaxHP()){
                    mediaPlayer = MediaPlayer.create(survivorView.getContext(), R.raw.heart);
                    mediaPlayer.start();
                    survivorView.getHearts().remove(survivorView.getHearts().get(i));
                    character.giveHP(1);
                }
            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
