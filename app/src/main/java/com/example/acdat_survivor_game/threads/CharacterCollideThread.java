package com.example.acdat_survivor_game.threads;

import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.viewmodels.Sprite;

import java.util.ArrayList;

public class CharacterCollideThread extends Thread {

    private Sprite character;
    private ArrayList<Sprite> enemies;
    private Boolean running;
    private SurvivorView survivorView;
    private MediaPlayer mediaPlayer;

    public CharacterCollideThread(Sprite character, ArrayList<Sprite> enemies, SurvivorView survivorView) {
        this.character = character;
        this.enemies = enemies;
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
        while (running && !character.isDead()){

            for (int i = 0; i < enemies.size(); i++) {
                if(character.isHover(enemies.get(i))){
                    mediaPlayer = MediaPlayer.create(survivorView.getContext(), R.raw.hurt_isaac);
                    mediaPlayer.start();
                    character.removeHP(1);
                    enemies.get(i).removeHP(1);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
