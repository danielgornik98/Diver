package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
    private Bitmap image;
    private int x;
    private int y;
    private int dy;

    public Background(Bitmap res)
    {
        image = res;
        dy=GamePanel.MOVESPEED;
    }

    public void update()
    {
        y+=dy;
        if(y<-GamePanel.HEIGHT)
        {
            y=0;
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
        if(y<0)
        {
            canvas.drawBitmap(image, x, y+GamePanel.HEIGHT, null);

        }
    }

    public void setVector(int dy)
    {
        this.dy = dy;
    }
}
