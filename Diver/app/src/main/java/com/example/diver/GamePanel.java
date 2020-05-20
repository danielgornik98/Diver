package com.example.diver;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 480;
    public static final int HEIGHT = 856;
    public static final int MOVESPEED = -2;
    public static final int SHARKWIDTH = 100;
    public static final int SHARKHEIGHT = 188;
    public static final int BLOWFISHWIDTH = 65;
    public static final int BLOWFISHHEIGHT = 55;
    public static final int HEALTHWIDTH = 50;
    public static final int HEALTHHEIGHT = 50;
    public static final int OXYGENWIDTH = 29;
    public static final int OXYGENHEIGHT = 50;
    public static final int OXYGENMAXLEVEL = 1600;
    public static final int OXYGENBARWIDTH = 204;
    public static final int OXYGENBARHEIGHT = 62;
    public static final int MENUBUTTONWIDTH = 110;
    public static final int MENUBUTTONHEIGHT = 42;
    public static final int BUTTONWIDTH = 240;
    public static final int BUTTONHEIGHT = 92;

    public static final int centerPositionX = (WIDTH - BUTTONWIDTH)/2;
    public static final int centerPositionY = (HEIGHT - BUTTONHEIGHT)/2;


    private int rand;
    private int counter;
    private int oxygenCounter;
    private boolean startingMenu;
    private boolean ingameMenu;
    private boolean deadMenu;
    private boolean game;
    private boolean exit;
    private boolean restart;

    private float scaleX;
    private float scaleY;
    private float score;


    private MainThread thread;
    private Background bg;
    private Player player;
    private OxygenBar oxygenBar;
    private ArrayList<Oxygen> oxygen;
    private ArrayList<Shark> sharks;
    private ArrayList<BlowFish> blowFishes;
    private ArrayList<Health> healths;
    private Blood blood;
    private Button playAgainButton;
    private Button playAgainButton2;
    private Button exitButton1;
    private Button exitButton2;
    private Button exitButton3;
    private Button returnButton;
    private Button playButton;
    private Button menuButton;
    private Paint paint;

    public GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
        startingMenu = true;
        ingameMenu = false;
        deadMenu = false;
        game = false;
        exit = false;
        restart = false;
        score = 0;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeridle), BitmapFactory.decodeResource(getResources(), R.drawable.playerleft), BitmapFactory.decodeResource(getResources(), R.drawable.playerright), 100, 100, 6);
        sharks = new ArrayList<>();
        blowFishes = new ArrayList<>();
        healths = new ArrayList<>();
        oxygen = new ArrayList<>();
        oxygen.add(new Oxygen(BitmapFactory.decodeResource(getResources(), R.drawable.oxygen), -100, OXYGENWIDTH, OXYGENHEIGHT, 4));
        oxygenBar = new OxygenBar(BitmapFactory.decodeResource(getResources(), R.drawable.oxygenbar),OXYGENBARWIDTH,OXYGENBARHEIGHT,16);
        menuButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.menubutton), WIDTH - MENUBUTTONWIDTH, 0);
        playButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.playbutton), centerPositionX, centerPositionY - BUTTONHEIGHT/2 - BUTTONHEIGHT);
        exitButton1 = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.exitbutton), centerPositionX, centerPositionY + BUTTONHEIGHT/2);
        returnButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.returnbutton), centerPositionX, centerPositionY - BUTTONHEIGHT/2 - BUTTONHEIGHT);
        playAgainButton = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.playagainbutton), centerPositionX, centerPositionY);
        exitButton2 = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.exitbutton),centerPositionX, centerPositionY + BUTTONHEIGHT/2 + BUTTONHEIGHT);
        playAgainButton2 = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.playagainbutton), centerPositionX, centerPositionY - BUTTONHEIGHT/2 - BUTTONHEIGHT);
        exitButton3 = new Button(BitmapFactory.decodeResource(getResources(), R.drawable.exitbutton), centerPositionX, centerPositionY + BUTTONHEIGHT/2);
        blood = new Blood(BitmapFactory.decodeResource(getResources(), R.drawable.blood));

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(24);
        scaleX = getWidth() / (float)WIDTH;
        scaleY = getHeight() / (float)HEIGHT;
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(game)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if(((int)event.getX()>getWidth()-scaleX*MENUBUTTONWIDTH)&&((int)event.getY()<scaleY*MENUBUTTONHEIGHT))
                {
                    ingameMenu = true;
                    game = false;
                }
                else if ((int) event.getX() > getWidth() / 2)
                {
                    player.setRight(true);
                }
                else if ((int) event.getX() <= getWidth() / 2)
                {
                    player.setLeft(true);
                }
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP)
            {
                player.setLeft(false);
                player.setRight(false);
                return true;
            }
        }
        else if(startingMenu)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                //clicking play button
                if((((int)event.getX()>centerPositionX*scaleX)&&((int)event.getX()<(centerPositionX+BUTTONWIDTH)*scaleX))&&(((int)event.getY()>(centerPositionY - BUTTONHEIGHT/2 - BUTTONHEIGHT)*scaleY)&&((int)event.getY()<(centerPositionY - BUTTONHEIGHT/2)*scaleY)))
                {
                    game = true;
                    exit = false;
                    restart = false;
                    startingMenu = false;
                }
                //clicking exit button
                else if((((int)event.getX()>centerPositionX*scaleX)&&((int)event.getX()<(centerPositionX+BUTTONWIDTH)*scaleX))&&(((int)event.getY()>(centerPositionY + BUTTONHEIGHT/2)*scaleY)&&((int)event.getY()<(centerPositionY + BUTTONHEIGHT/2 + BUTTONHEIGHT)*scaleY)))
                {
                    exit = true;
                    game = false;
                    restart = false;
                    startingMenu = false;
                }
                return true;
            }
        }
        else if(ingameMenu)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                //clicking resume button
                if((((int)event.getX()>centerPositionX*scaleX)&&((int)event.getX()<(centerPositionX+BUTTONWIDTH)*scaleX))&&(((int)event.getY()>(centerPositionY - BUTTONHEIGHT/2 - BUTTONHEIGHT)*scaleY)&&((int)event.getY()<(centerPositionY - BUTTONHEIGHT/2)*scaleY)))
                {
                    game = true;
                    exit = false;
                    restart = false;
                    ingameMenu = false;
                }
                //clicking playAgain button
                else if((((int)event.getX()>centerPositionX*scaleX)&&((int)event.getX()<(centerPositionX+BUTTONWIDTH)*scaleX))&&(((int)event.getY()>(centerPositionY*scaleY))&&((int)event.getY()<(centerPositionY + BUTTONHEIGHT)*scaleY)))
                {
                    exit = false;
                    game = false;
                    restart = true;
                    ingameMenu = false;
                }
                // clicking exit button
                else if((((int)event.getX()>centerPositionX*scaleX)&&((int)event.getX()<(centerPositionX+BUTTONWIDTH)*scaleX))&&(((int)event.getY()>(centerPositionY + BUTTONHEIGHT/2 + BUTTONHEIGHT)*scaleY)&&((int)event.getY()<(centerPositionY + BUTTONHEIGHT/2 + 2*BUTTONHEIGHT)*scaleY)))
                {
                    exit = true;
                    game = false;
                    restart = false;
                    ingameMenu = false;
                }
                return true;
            }
        }
        else if(deadMenu)
        {
            //clicking playAgain button
            if((((int)event.getX()>centerPositionX*scaleX)&&((int)event.getX()<(centerPositionX+BUTTONWIDTH)*scaleX))&&(((int)event.getY()>(centerPositionY - BUTTONHEIGHT/2 - BUTTONHEIGHT)*scaleY)&&((int)event.getY()<(centerPositionY - BUTTONHEIGHT/2)*scaleY)))
            {
                game = false;
                exit = false;
                restart = true;
                startingMenu = false;
                deadMenu = false;
            }
            //clicking exit button
            else if((((int)event.getX()>centerPositionX*scaleX)&&((int)event.getX()<(centerPositionX+BUTTONWIDTH)*scaleX))&&(((int)event.getY()>(centerPositionY + BUTTONHEIGHT/2)*scaleY)&&((int)event.getY()<(centerPositionY + BUTTONHEIGHT/2 + BUTTONHEIGHT)*scaleY)))
            {
                exit = true;
                game = false;
                restart = false;
                startingMenu = false;
                deadMenu = false;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void restart()
    {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.playeridle), BitmapFactory.decodeResource(getResources(), R.drawable.playerleft), BitmapFactory.decodeResource(getResources(), R.drawable.playerright), 100, 100, 6);
        sharks = new ArrayList<>();
        blowFishes = new ArrayList<>();
        healths = new ArrayList<>();
        oxygen = new ArrayList<>();
        oxygen.add(new Oxygen(BitmapFactory.decodeResource(getResources(), R.drawable.oxygen), -100, OXYGENWIDTH, OXYGENHEIGHT, 4));
        oxygenBar = new OxygenBar(BitmapFactory.decodeResource(getResources(), R.drawable.oxygenbar),OXYGENBARWIDTH,OXYGENBARHEIGHT,16);
        counter = 0;
        oxygenCounter = 0;
        game = true;
        ingameMenu = false;
        startingMenu = false;
        deadMenu = false;
        score = 0;
    }


    public boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getRect(),b.getRect()))
        {
            return true;
        }
        return false;
    }


    public void update()
    {
        if(game)
        {
            score += 0.01;
            bg.update();
            player.update();
            if (counter != 20) counter++;
            rand = (int) (Math.random() * 10000);
            if ((rand >= 7000) && (counter == 20)) {
                counter = 0;
                rand = (int) (Math.random() * 100);
                if (rand < 58) {
                    rand = (int) (Math.random() * WIDTH - BLOWFISHWIDTH / 2);
                    blowFishes.add(new BlowFish(BitmapFactory.decodeResource(getResources(), R.drawable.blowfish), rand, HEIGHT, BLOWFISHWIDTH, BLOWFISHHEIGHT, 6));
                } else if (rand > 60) {
                    rand = (int) (Math.random() * WIDTH - SHARKWIDTH / 2);
                    sharks.add(new Shark(BitmapFactory.decodeResource(getResources(), R.drawable.shark), rand, HEIGHT, SHARKWIDTH, SHARKHEIGHT, 6));
                } else {
                    rand = (int) (Math.random() * WIDTH - HEALTHWIDTH / 2);
                    healths.add(new Health(BitmapFactory.decodeResource(getResources(), R.drawable.health), rand, HEIGHT, HEALTHWIDTH, HEALTHHEIGHT, 29));
                }
            }

            if (oxygenCounter != 600) {
                oxygenCounter++;

                if (oxygenCounter == 200) {
                    if (oxygen.size() > 0) oxygen.remove(0);
                }
            } else {
                rand = (int) (Math.random() * WIDTH - (OXYGENWIDTH - 1) / 2);
                oxygen.add(new Oxygen(BitmapFactory.decodeResource(getResources(), R.drawable.oxygen), rand, OXYGENWIDTH, OXYGENHEIGHT, 4));
                oxygenCounter = 0;
            }


            for (int i = 0; i < sharks.size(); i++) {

                if(player.isHurt)
                    sharks.get(i).isBlood = true;

                sharks.get(i).update();

                if (sharks.get(i).getY() < -300) {
                    sharks.remove(i);
                }

                if (collision(player, sharks.get(i))) {
                    player.isDead = true;
                    sharks.remove(i);
                }
            }

            for (int i = 0; i < healths.size(); i++) {
                healths.get(i).update();
                if (healths.get(i).getY() < -300) {
                    healths.remove(i);
                }

                if (collision(player, healths.get(i))) {
                    player.isHurt = false;
                    healths.remove(i);
                }
            }

            for (int i = 0; i < blowFishes.size(); i++) {
                blowFishes.get(i).update();
                if (blowFishes.get(i).getY() < -300) {
                    blowFishes.remove(i);
                }

                if (collision(player, blowFishes.get(i))) {
                    if (!player.isHurt) {
                        player.isHurt = true;
                        blowFishes.remove(i);
                    } else {
                        player.isDead = true;
                        blowFishes.remove(i);
                    }

                }
            }

            for (int i = 0; i < oxygen.size(); i++) {
                oxygen.get(i).update();

                if (collision(player, oxygen.get(i))) {
                    player.oxygenLevel = OXYGENMAXLEVEL;
                    oxygen.remove(i);
                }
            }


            //Changing oxygen rectangle width
            oxygenBar.animation.setCurrentFrame(player.oxygenLevel / 100);


            //Checking if oxygen level is 0

            if (player.oxygenLevel == 0) {
                player.isDead = true;
            }


            if(player.isDead)
            {
                game = false;
                deadMenu = true;
            }

        }
        else
        {
            if(exit)
            {
                exit = false;
                System.exit(0);
            }
            if(restart)
            {
                restart = false;
                restart();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            final int savedState = canvas.save();
            final float scaleFactorX = getWidth() / (float)WIDTH;
            final float scaleFactorY = getHeight() / (float)HEIGHT;
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            if(game)
            {
                for (Shark shark : sharks) {
                    shark.draw(canvas);
                }
                for (BlowFish blowFish : blowFishes) {
                    blowFish.draw(canvas);
                }
                for (Health health : healths) {
                    health.draw(canvas);
                }
                for (Oxygen oxygen : oxygen) {
                    oxygen.draw(canvas);
                }
                player.draw(canvas);
                if(player.isHurt)
                    blood.draw(canvas);
                oxygenBar.draw(canvas);
                menuButton.draw(canvas);
                canvas.drawText(String.valueOf((int)score), centerPositionX*scaleFactorX, OXYGENBARHEIGHT/2, paint);
            }
            else if(ingameMenu)
            {
                returnButton.draw(canvas);
                playAgainButton.draw(canvas);
                exitButton2.draw(canvas);
            }
            else if(startingMenu)
            {
                playButton.draw(canvas);
                exitButton1.draw(canvas);
            }
            else if(deadMenu)
            {
                playAgainButton2.draw(canvas);
                exitButton3.draw(canvas);
                canvas.drawText("Your Score: " + String.valueOf((int)score), (int)(centerPositionX+0.2*BUTTONWIDTH), (int)(centerPositionY-2.5*BUTTONHEIGHT), paint);
            }

            canvas.restoreToCount(savedState);
        }
    }
}

