package com.example.diver;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player extends GameObject {

    private Bitmap spritesheetidle;
    private Bitmap spritesheetleft;
    private Bitmap spritesheetright;
    private Bitmap[] imageIdle;
    private Bitmap[] imageLeft;
    private Bitmap[] imageRight;
    private boolean animIdle;
    private boolean animLeft;
    private boolean animRight;
    private int score;
    private double dxa;
    private boolean left;
    private boolean right;
    private Animation animation = new Animation();
    private long startTime;
    public boolean isHurt;
    public boolean isDead;
    public boolean isPlaying;
    public int oxygenLevel;

    public Player(Bitmap idle, Bitmap left, Bitmap right, int w, int h, int numFrames)
    {
        x = GamePanel.WIDTH/2;
        y = 50;
        dx = 0;
        score = 0;
        height = h;
        width = w;
        isHurt = false;
        isDead = false;
        isPlaying = true;
        oxygenLevel = 1600;

        imageIdle = new Bitmap[numFrames];
        imageLeft = new Bitmap[numFrames+1];
        imageRight = new Bitmap[numFrames+1];
        spritesheetidle = idle;
        spritesheetleft = left;
        spritesheetright = right;

        for(int i=0; i<imageIdle.length; i++)
        {
            imageIdle[i] = Bitmap.createBitmap(spritesheetidle, i*width, 0, width, height);
        }

        for(int i=0; i<imageLeft.length; i++)
        {
            imageLeft[i] = Bitmap.createBitmap(spritesheetleft, i*width, 0, width, height);
        }

        for(int i=0; i<imageRight.length; i++)
        {
            imageRight[i] = Bitmap.createBitmap(spritesheetright, i*width, 0, width, height);
        }
        animation.setFrames(imageIdle);
        animation.setDelay(100);
        startTime = System.nanoTime();
    }

    public void setLeft(boolean l)
    {
        left = l;
    }

    public void setRight(boolean r)
    {
        right = r;
    }

    public void update()
    {
        long elapsed = (System.nanoTime() - startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }

        animation.update();

        if(left)
        {
            dx = (int)(dxa-=.4);
            if(!animLeft) {
                animation.setFrames(imageLeft);
                animation.setDelay(100);
            }
            animLeft = true;
            animIdle = false;
            animRight = false;

        }
        else if(right)
        {
            dx = (int)(dxa+=.4);
            if(!animRight)
            {
                animation.setFrames(imageRight);
                animation.setDelay(100);
            }
            animRight = true;
            animLeft = false;
            animIdle = false;

        }
        else
        {
            dxa=0;
            dx=(int)dxa;
            if(!animIdle)
            {
                animation.setFrames(imageIdle);
                animation.setDelay(100);
            }
            animIdle = true;
            animLeft = false;
            animRight = false;

        }

        if(dx>4)
        {
            dx = 4;
        }
        if(dx<-4)
        {
            dx = -4;
        }
        if((x+dx*2>=-20)&&(x+width+dx*2<=GamePanel.WIDTH+20))
        {

            x += dx * 2;
        }
        dx = 0;

        // lowering oxygenLevel
        oxygenLevel--;

    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public int getScore()
    {
        return score;
    }

    public void resetDXA()
    {
        dxa = 0;
    }

    public void resetScore()
    {
        score = 0;
    }

}
