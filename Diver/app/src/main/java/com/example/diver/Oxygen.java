package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Oxygen extends GameObject {

    private Bitmap spritesheet;
    private Bitmap[] imageOxygen;
    private Animation animation = new Animation();

    public Oxygen(Bitmap sheet, int xpos, int w, int h, int numFrames)
    {
        x = xpos;
        y = 70;
        width = w;
        height = h;
        dy = 0;

        imageOxygen = new Bitmap[numFrames];
        spritesheet = sheet;

        for(int i=0; i<imageOxygen.length; i++)
        {
            imageOxygen[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(imageOxygen);
        animation.setDelay(300);

    }

    public void update()
    {
        animation.update();
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}

