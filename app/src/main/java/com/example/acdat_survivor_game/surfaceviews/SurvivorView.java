package com.example.acdat_survivor_game.surfaceviews;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.SurvivorActivity;
import com.example.acdat_survivor_game.threads.BulletThread;
import com.example.acdat_survivor_game.threads.CharacterThread;
import com.example.acdat_survivor_game.threads.CharacterCollideThread;
import com.example.acdat_survivor_game.threads.EnemiesDifficultyThread;
import com.example.acdat_survivor_game.threads.EnemiesHPsThread;
import com.example.acdat_survivor_game.threads.EnemiesThread;
import com.example.acdat_survivor_game.threads.GameLoopThread;
import com.example.acdat_survivor_game.threads.GenerateItemsThread;
import com.example.acdat_survivor_game.threads.RemoveItemsThread;
import com.example.acdat_survivor_game.threads.SpawnEnemiesThread;
import com.example.acdat_survivor_game.viewmodels.Bullet;
import com.example.acdat_survivor_game.viewmodels.Image;
import com.example.acdat_survivor_game.viewmodels.Sprite;
import com.example.acdat_survivor_game.viewmodels.TempSprite;
import com.example.acdat_survivor_game.viewmodels.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SurvivorView extends SurfaceView implements SurfaceHolder.Callback {
    Random rnd = new Random();
    private MediaPlayer mediaPlayer;
    private GameLoopThread gameLoopThread;
    private CharacterThread characterThread;
    private CharacterCollideThread collitionThread;
    private Sprite character;
    private LinkedList<Bullet> bullets;
    private LinkedList<BulletThread> bulletsThreads;
    private ArrayList<Sprite> enemies;
    private ArrayList<EnemiesThread> enemiesThread;
    private EnemiesHPsThread enemiesHPsThread;
    private Image hp_3, hp_2, hp_1;
    private List<TempSprite> temps;
    private SpawnEnemiesThread spawnEnemiesThread;
    private ArrayList<Image> coins, hearts;
    private Integer coins_cont;
    private GenerateItemsThread generateItemsThread;
    private RemoveItemsThread removeItemsThread;
    private EnemiesDifficultyThread enemiesDifficultyThread;
    private Image coin;
    private Text text;
    private SurvivorActivity activity;
    private Integer level, cont = 0;
    private Context context;
    private Boolean canShoot = true;

    public SurvivorView(Context context, SurvivorActivity activity) {
        super(context);

        this.context = context;
        this.activity = activity;
        this.bullets = new LinkedList<Bullet>();
        this.bulletsThreads = new LinkedList<BulletThread>();
        this.enemies = new ArrayList<Sprite>();
        this.enemiesThread = new ArrayList<EnemiesThread>();
        this.temps = new ArrayList<TempSprite>();
        this.coins = new ArrayList<Image>();
        this.hearts = new ArrayList<Image>();
        this.coins_cont = 0;
        this.level = 1;

        getHolder().addCallback(this);
        setBackgroundResource(R.drawable.bg_lvl_1);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        int[] character_moves = {3, 1, 0, 2};
        character = new Sprite(
                getWidth()/2,
                getHeight()/2,
                this,
                getResources(),
                R.drawable.isaac_v2,
                1200,
                1067,
                character_moves,
                3
        );

        character_moves = new int[]{2, 3, 0, 1};
        enemies.add(new Sprite(
                rnd.nextInt(getWidth() - 1000),
                rnd.nextInt(getHeight() - 1000),
                this,
                getResources(),
                R.drawable.zombie1,
                675,
                900,
                character_moves,
                3
        ));

        character_moves = new int[]{2, 3, 0, 1};
        enemies.add(new Sprite(
                rnd.nextInt(this.getWidth() - 1000),
                rnd.nextInt(this.getHeight() - 1000),
                this,
                getResources(),
                R.drawable.zombie1,
                675,
                900,
                character_moves,
                3
        ));

        character_moves = new int[]{2, 3, 0, 1};
        enemies.add(new Sprite(
                rnd.nextInt(this.getWidth() - 1000),
                rnd.nextInt(this.getHeight() - 1000),
                this,
                getResources(),
                R.drawable.zombie1,
                675,
                900,
                character_moves,
                3
        ));

        hp_1 = new Image(50, 50, getResources(), R.drawable.hp_1, 300, 85);
        hp_2 = new Image(50, 50, getResources(), R.drawable.hp_2, 300, 85);
        hp_3 = new Image(50, 50, getResources(), R.drawable.hp_3, 300, 85);

        coin = new Image(getWidth() - 500, 50, getResources(), R.drawable.coin, 116, 85);
        text = new Text(getWidth() - 350, 115, "X " + coins_cont);

        gameLoopThread = new GameLoopThread(this);
        gameLoopThread.setRunning(true);
        gameLoopThread.start();

        characterThread = new CharacterThread(character);
        characterThread.setRunning(true);
        characterThread.start();

        for (Sprite enemy : enemies) {
            enemiesThread.add(new EnemiesThread(enemy, character, this));
            enemiesThread.get(enemiesThread.size() - 1).setRunning(true);
            enemiesThread.get(enemiesThread.size() - 1).start();
        }

        collitionThread = new CharacterCollideThread(character, enemies, this);
        collitionThread.setRunning(true);
        collitionThread.start();

        enemiesHPsThread = new EnemiesHPsThread(this);
        enemiesHPsThread.setRunning(true);
        enemiesHPsThread.start();

        spawnEnemiesThread = new SpawnEnemiesThread(this, character);
        spawnEnemiesThread.setRunning(true);
        spawnEnemiesThread.start();

        generateItemsThread = new GenerateItemsThread(this);
        generateItemsThread.setRunning(true);
        generateItemsThread.start();

        removeItemsThread = new RemoveItemsThread(this, character);
        removeItemsThread.setRunning(true);
        removeItemsThread.start();

        enemiesDifficultyThread = new EnemiesDifficultyThread(this);
        enemiesDifficultyThread.setRunning(true);
        enemiesDifficultyThread.start();

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

        retry = true;
        characterThread.setRunning(false);

        while (retry){
            try {
                characterThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        for (EnemiesThread enemy : enemiesThread) {
            retry = true;
            enemy.setRunning(false);

            while (retry){
                try {
                    enemy.join();
                    retry = false;
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }

        retry = true;
        collitionThread.setRunning(false);

        while (retry){
            try {
                collitionThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        retry = true;
        enemiesHPsThread.setRunning(false);

        while (retry){
            try {
                enemiesHPsThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        retry = true;
        spawnEnemiesThread.setRunning(false);

        while (retry){
            try {
                spawnEnemiesThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        retry = true;
        generateItemsThread.setRunning(false);

        while (retry){
            try {
                generateItemsThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        retry = true;
        removeItemsThread.setRunning(false);

        while (retry){
            try {
                removeItemsThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

        retry = true;
        enemiesDifficultyThread.setRunning(false);

        while (retry){
            try {
                enemiesDifficultyThread.join();
                retry = false;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = temps.size() - 1; i >= 0; i--){
            temps.get(i).onDraw(canvas);
        }

        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).onDraw(canvas);
        }

        for (int i = 0; i < hearts.size(); i++) {
            hearts.get(i).onDraw(canvas);
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).onDraw(canvas);
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).onDraw(canvas);
        }

        character.onDraw(canvas);

        switch(character.getHP()){
            case 1:
                hp_1.onDraw(canvas);
                break;
            case 2:
                hp_2.onDraw(canvas);
                break;
            case 3:
                hp_3.onDraw(canvas);
                break;
        }

        coin.onDraw(canvas);
        text.onDraw(canvas);

        if(coins_cont > 10) {
            setBackgroundResource(R.drawable.bg_lvl_3);
            level = 3;
        } else if(coins_cont > 5){
            setBackgroundResource(R.drawable.bg_lvl_2);
            level = 2;
        }

        if(character.isDead()){
            mediaPlayer = MediaPlayer.create(this.getContext(), R.raw.dead_isaac);
            mediaPlayer.start();
            activity.loseActivity(coins_cont);
            destroy();
        }

        cont += 1;
        if (cont >= 5000)
            cont = 0;

        if(cont % 30 == 0){
            canShoot = true;
        }

    }

    public void setPositionUpdated(int x, int y) {
        characterThread.setPositionUpdated(x, y);
    }

    public void shootVertical(int dy) {
        if(canShoot){
            mediaPlayer = MediaPlayer.create(this.getContext(), R.raw.shoot_isaac);
            mediaPlayer.start();
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
            canShoot = false;
        }
    }

    public void shootHorizontal(int dx) {
        if(canShoot){
            mediaPlayer = MediaPlayer.create(this.getContext(), R.raw.shoot_isaac);
            mediaPlayer.start();
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
            canShoot = false;
        }
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

    public ArrayList<Sprite> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Sprite> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<EnemiesThread> getEnemiesThread() {
        return enemiesThread;
    }

    public void setEnemiesThread(ArrayList<EnemiesThread> enemiesThread) {
        this.enemiesThread = enemiesThread;
    }

    public List<TempSprite> getTemps() {
        return temps;
    }

    public void setTemps(List<TempSprite> temps) {
        this.temps = temps;
    }

    public ArrayList<Image> getCoins() {
        return coins;
    }

    public void setCoins(ArrayList<Image> coins) {
        this.coins = coins;
    }

    public ArrayList<Image> getHearts() {
        return hearts;
    }

    public void setHearts(ArrayList<Image> hearts) {
        this.hearts = hearts;
    }

    public void setCoins_cont(Integer coins_cont) {
        this.coins_cont = coins_cont;
    }

    public void addCoins_cont(int sum) {
        this.coins_cont += sum;
        text.setText("X " + this.coins_cont);

    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}
