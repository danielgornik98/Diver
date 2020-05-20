package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Button {

    private Bitmap image;
    private int x;
    private int y;
    private int width;
    private int height;


    public Button(Bitmap res, int xpos, int ypos)
    {
        image = res;
        x = xpos;
        y = ypos;
    }


    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }
}
