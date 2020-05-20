package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class OxygenBar extends GameObject {

    private Bitmap spritesheet;
    private Bitmap[] imageOxygenBar;
    public Animation animation = new Animation();

    public OxygenBar(Bitmap sheet, int w, int h, int numFrames)
    {
        x = -(int)(3/204*(float)w);
        y = -(int)(0.2*(float)h);
        width = w;
        height = h;

        imageOxygenBar = new Bitmap[numFrames];
        spritesheet = sheet;

        for(int i=0; i<imageOxygenBar.length; i++)
        {
            imageOxygenBar[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(imageOxygenBar);
        animation.setCurrentFrame(imageOxygenBar.length-1);

    }


    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}