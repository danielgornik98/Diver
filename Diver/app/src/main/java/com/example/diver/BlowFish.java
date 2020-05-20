package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BlowFish extends GameObject {

    private Bitmap spritesheet;
    private Bitmap[] imageBlowFish;
    private Animation animation = new Animation();

    public BlowFish(Bitmap sheet, int xpos, int ypos, int w, int h, int numFrames)
    {
        x = xpos;
        y = ypos;
        width = w;
        height = h;
        dy = 0;

        imageBlowFish = new Bitmap[numFrames];
        spritesheet = sheet;

        for(int i=0; i<imageBlowFish.length; i++)
        {
            imageBlowFish[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(imageBlowFish);
        animation.setDelay(200);

    }

    public void update()
    {
        animation.update();

        y -= 5;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}
