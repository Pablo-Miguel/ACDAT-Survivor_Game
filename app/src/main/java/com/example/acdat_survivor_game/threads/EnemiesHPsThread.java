package com.example.acdat_survivor_game.threads;

import android.media.MediaPlayer;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.viewmodels.TempSprite;

public class EnemiesHPsThread extends Thread {
    private SurvivorView survivorView;
    private Boolean running;
    private Integer cont = 0;
    private MediaPlayer mediaPlayer;

    public EnemiesHPsThread(SurvivorView survivorView) {
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

            for(int i = 0; i < survivorView.getEnemiesThread().size(); i++){
                if(survivorView.getEnemiesThread().get(i).getEnemy().isDead()){
                    mediaPlayer = MediaPlayer.create(survivorView.getContext(), R.raw.dead_zombi);
                    mediaPlayer.start();
                    survivorView.getEnemiesThread().get(i).setRunning(false);
                    survivorView.getEnemies().remove(survivorView.getEnemiesThread().get(i).getEnemy());
                    survivorView.getEnemiesThread().remove(survivorView.getEnemiesThread().get(i).getEnemy());
                    survivorView.getTemps().add(new TempSprite(
                            cont++,
                            survivorView,
                            survivorView.getEnemiesThread().get(i).getEnemy().getX(),
                            survivorView.getEnemiesThread().get(i).getEnemy().getY(),
                            survivorView.getResources(),
                            R.drawable.blood1
                    ));
                    survivorView.getEnemiesThread().remove(survivorView.getEnemiesThread().get(i));
                }
            }

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
