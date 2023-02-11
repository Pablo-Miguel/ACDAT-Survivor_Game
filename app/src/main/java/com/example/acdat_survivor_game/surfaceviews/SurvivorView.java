package com.example.acdat_survivor_game.surfaceviews;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.threads.BulletThread;
import com.example.acdat_survivor_game.threads.CharacterThread;
import com.example.acdat_survivor_game.threads.EnemiesThread;
import com.example.acdat_survivor_game.threads.GameLoopThread;
import com.example.acdat_survivor_game.viewmodels.Bullet;
import com.example.acdat_survivor_game.viewmodels.Sprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class SurvivorView extends SurfaceView implements SurfaceHolder.Callback {
    Random rnd = new Random();
    private GameLoopThread gameLoopThread;
    private CharacterThread characterThread;
    private Sprite character;
    private LinkedList<Bullet> bullets;
    private LinkedList<BulletThread> bulletsThreads;
    private ArrayList<Sprite> enemies;
    private ArrayList<EnemiesThread> enemiesThread;

    public SurvivorView(Context context) {
        super(context);

        this.bullets = new LinkedList<Bullet>();
        this.bulletsThreads = new LinkedList<BulletThread>();
        this.enemies = new ArrayList<Sprite>();
        this.enemiesThread = new ArrayList<EnemiesThread>();

        getHolder().addCallback(this);
        setBackgroundResource(R.drawable.bg_lvl_1);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        int[] character_moves = {3, 1, 0, 2};
        character = new Sprite(
                this.getWidth()/2,
                this.getHeight()/2,
                this,
                getResources(),
                R.drawable.isaac_v2,
                1200,
                1067,
                character_moves
        );

        character_moves = new int[]{2, 3, 0, 1};
        enemies.add(new Sprite(
                rnd.nextInt(this.getWidth() - 1000),
                rnd.nextInt(this.getHeight() - 1000),
                this,
                getResources(),
                R.drawable.zombie1,
                1000,
                1000,
                character_moves
        ));

        gameLoopThread = new GameLoopThread(this);
        gameLoopThread.setRunning(true);
        gameLoopThread.start();

        characterThread = new CharacterThread(character);
        characterThread.setRunning(true);
        characterThread.start();

        for (Sprite enemy : enemies) {
            enemiesThread.add(new EnemiesThread(enemy, character));
            enemiesThread.get(enemiesThread.size() - 1).setRunning(true);
            enemiesThread.get(enemiesThread.size() - 1).start();
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        destroy();
    }

    private void destroy(){
        boolean retry = true;
        gameLoopThread.setRunning(false);

        while (retry){
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).onDraw(canvas);
        }

        for (Sprite enemy : enemies) {
            enemy.onDraw(canvas);
        }

        character.onDraw(canvas);

    }

    public void setPositionUpdated(int x, int y) {
        characterThread.setPositionUpdated(x, y);
    }

    public void shootVertical(int dy) {
        bulletsThreads.add(new BulletThread());
        bullets.add(Bullet.getBullet(
                character.getWidth()/2 + character.getX() - 30,
                character.getHeight()/2 + character.getY() - 30,
                0,
                dy,
                getResources(),
                this,
                bulletsThreads.getLast()
        ));
        bulletsThreads.getLast().setBullet(bullets.getLast());
        bulletsThreads.getLast().setRunning(true);
        bulletsThreads.getLast().start();
    }

    public void shootHorizontal(int dx) {
        bulletsThreads.add(new BulletThread());
        bullets.add(Bullet.getBullet(
                character.getWidth()/2 + character.getX() - 30,
                character.getHeight()/2 + character.getY() - 30,
                dx,
                0,
                getResources(),
                this,
                bulletsThreads.getLast()
        ));
        bulletsThreads.getLast().setBullet(bullets.getLast());
        bulletsThreads.getLast().setRunning(true);
        bulletsThreads.getLast().start();
    }

    public LinkedList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(LinkedList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public LinkedList<BulletThread> getBulletsThreads() {
        return bulletsThreads;
    }

    public void setBulletsThreads(LinkedList<BulletThread> bulletsThreads) {
        this.bulletsThreads = bulletsThreads;
    }
}
