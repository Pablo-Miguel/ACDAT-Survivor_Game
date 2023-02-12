package com.example.acdat_survivor_game.viewmodels;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Image extends Figure {

    private Bitmap bmp;

    public Image(Integer x, Integer y, Resources resources, int img_ref, int w, int h) {
        super(x, y);
        bmp = BitmapFactory.decodeResource(resources, img_ref);
        bmp = bmp.createScaledBitmap(bmp, w, h, true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, getX(), getY(), null);
    }

    public boolean isHover(Sprite sprite) {

        double centroX = (bmp.getWidth() / 2) + getX();
        double centroY = (bmp.getHeight() / 2) + getY();

        double centroXR = (sprite.getWidth() / 2) + sprite.getX();
        double centroYR = (sprite.getHeight() / 2) + sprite.getY();

        double distanciaPuntos = Math.sqrt(Math.pow(centroXR - centroX, 2) + Math.pow(centroYR - centroY, 2));

        if(distanciaPuntos < bmp.getHeight()){
            return true;
        }

        return false;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
