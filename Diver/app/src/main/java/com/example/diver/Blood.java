package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Blood {
    private Bitmap image;
    private int x;
    private int y;

    public Blood(Bitmap res)
    {
        image = res;
    }


    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y, null);
    }

}
