package com.example.acdat_survivor_game.surfaceviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.acdat_survivor_game.R;
import com.example.acdat_survivor_game.threads.CharacterThread;
import com.example.acdat_survivor_game.threads.GameLoopThread;
import com.example.acdat_survivor_game.viewmodels.Sprite;

public class SurvivorView extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoopThread gameLoopThread;
    private CharacterThread characterThread;
    private Sprite sprite;
    private Bitmap bmp;

    public SurvivorView(Context context) {
        super(context);

        getHolder().addCallback(this);
        setBackgroundResource(R.drawable.bg_lvl_1);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.isaac_v2);
        bmp = bmp.createScaledBitmap(bmp, 1200, 1067, true);
        sprite = new Sprite(this, this.bmp);

        gameLoopThread = new GameLoopThread(this);
        gameLoopThread.setRunning(true);
        gameLoopThread.start();

        characterThread = new CharacterThread(sprite);
        characterThread.setRunning(true);
        characterThread.start();
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

        sprite.onDraw(canvas);

    }

    public void setPositionUpdated(int x, int y) {
        characterThread.setPositionUpdated(x, y);
    }
}
