package com.example.acdat_survivor_game.threads;

import com.example.acdat_survivor_game.surfaceviews.SurvivorView;
import com.example.acdat_survivor_game.viewmodels.Sprite;

public class EnemiesDifficultyThread extends Thread {
    private SurvivorView survivorView;
    private Boolean running;
    private Boolean changed;
    private Integer prev_level;

    public EnemiesDifficultyThread(SurvivorView survivorView) {
        this.survivorView = survivorView;
        running = false;
        changed = true;
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
            if(changed){
                prev_level = survivorView.getLevel();
                for(int i = 0; i < survivorView.getEnemies().size(); i++){
                    switch (survivorView.getLevel()){
                        case 1:
                            survivorView.getEnemies().get(i).setHP(3);
                            break;
                        case 2:
                            survivorView.getEnemies().get(i).setHP(5);
                            break;
                        case 3:
                            survivorView.getEnemies().get(i).setHP(7);
                            break;
                    }
                }

                changed = false;
            } else {
                if(prev_level != survivorView.getLevel()) changed = true;
            }

            for(int i = 0; i < survivorView.getEnemiesThread().size(); i++){
                switch (survivorView.getLevel()){
                    case 1:
                        survivorView.getEnemiesThread().get(i).setVelocity(3);
                        break;
                    case 2:
                        survivorView.getEnemiesThread().get(i).setVelocity(5);
                        break;
                    case 3:
                        survivorView.getEnemiesThread().get(i).setVelocity(7);
                        break;
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
