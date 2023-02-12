package com.example.acdat_survivor_game.threads;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.viewmodels.Sprite;

import java.util.Random;

public class SpawnEnemiesThread extends Thread {
    Random rnd = new Random();
    private SurvivorView survivorView;
    private Boolean running;
    private int[] character_moves;
    private Sprite temp_sprite, character;
    private MediaPlayer mediaPlayer;

    public SpawnEnemiesThread(SurvivorView survivorView, Sprite character) {
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

            if(survivorView.getEnemies().size() < 3){
                mediaPlayer = MediaPlayer.create(survivorView.getContext(), R.raw.appear_zombi);
                mediaPlayer.start();
                character_moves = new int[]{2, 3, 0, 1};
                temp_sprite = new Sprite(
                        rnd.nextInt(survivorView.getWidth() - 1000),
                        rnd.nextInt(survivorView.getHeight() - 1000),
                        survivorView,
                        survivorView.getResources(),
                        R.drawable.zombie1,
                        675,
                        900,
                        character_moves,
                        3
                );
                survivorView.getEnemies().add(temp_sprite);
                survivorView.getEnemiesThread().add(new EnemiesThread(temp_sprite, character, survivorView));
                survivorView.getEnemiesThread().get(survivorView.getEnemiesThread().size() - 1).setRunning(true);
                survivorView.getEnemiesThread().get(survivorView.getEnemiesThread().size() - 1).start();
            }

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
