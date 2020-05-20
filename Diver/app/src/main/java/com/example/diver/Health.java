package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Health extends GameObject {

    private Bitmap spritesheet;
    private Bitmap[] imageHealth;
    private Animation animation = new Animation();

    public Health(Bitmap sheet, int xpos, int ypos, int w, int h, int numFrames)
    {
        x = xpos;
        y = ypos;
        width = w;
        height = h;
        dy = 0;

        imageHealth = new Bitmap[numFrames];
        spritesheet = sheet;

        for(int i=0; i<imageHealth.length; i++)
        {
            imageHealth[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(imageHealth);
        animation.setDelay(50);

    }

    public void update()
    {
        animation.update();

        y -= 3;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
}
