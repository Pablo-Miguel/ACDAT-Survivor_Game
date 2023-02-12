package com.example.acdat_survivor_game.viewmodels;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.acdat_survivor_game.surfaceviews.SurvivorView;

import java.util.Objects;

public class TempSprite {
    private Integer id;
    private float x;
    private float y;
    private Bitmap bmp;
    private SurvivorView survivorView;
    private int life = 30;

    public TempSprite(Integer id, SurvivorView survivorView, float x, float y, Resources resources, Integer resource) {
        this.id = id;
        this.survivorView = survivorView;
        bmp = BitmapFactory.decodeResource(resources, resource);
        this.x = Math.min(Math.max(x - bmp.getWidth() / 2, 0), survivorView.getWidth() - bmp.getWidth());
        this.y = Math.min(Math.max(y - bmp.getHeight() / 2, 0), survivorView.getHeight() - bmp.getHeight());
    }

    public void onDraw(Canvas canvas){

        if(--life < 1){
            survivorView.getTemps().remove(this);
        }

        canvas.drawBitmap(bmp, x, y, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TempSprite)) return false;
        TempSprite that = (TempSprite) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
