package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Shark extends GameObject {

    private Bitmap spritesheet;
    private Bitmap[] imageShark;
    private Animation animation = new Animation();
    public boolean isBlood;

    public Shark(Bitmap sheet, int xpos, int ypos, int w, int h, int numFrames)
    {
        x = xpos;
        y = ypos;
        width = w;
        height = h;
        dy = 0;
        isBlood = false;

        imageShark = new Bitmap[numFrames];
        spritesheet = sheet;

        for(int i=0; i<imageShark.length; i++)
        {
            imageShark[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(imageShark);
        animation.setDelay(100);

    }

    public void update()
    {
        animation.update();

        if(!isBlood)
            y -= 10;
        else
            y -= 18;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

}
