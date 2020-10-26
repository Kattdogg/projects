package model;

import controller.InputEvent;
import controller.Main;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bomb extends GameObject {
    public static final int BOMB_FALLING =0;
    public static final int BOMB_EXPLODING = 1;
    public static final int DONE=2;

    public  int unitMove=3;
    public  int size= 35;

    public static final int MAXSIZE=50;

    public int type=-1;

    //Bomb types
    public static final int MULTIPLY_BOMB =0;
    public static final int REVIVE_BOMB = 1;



    Image image;
    public Shape shape;
    public  int state= BOMB_FALLING;
    public Color color;


    public Bomb(float x, float y)
    {
        super(x,y);

        unitMove=Main.rndGen.nextInt(2)+1;
        color = Color.gray;
    }



    @Override
    public Rectangle getCollisionRadius() {
        return new Rectangle((int) location.x, (int) location.y, size, size);
    }

    @Override
    public void update() {
        switch (state)
        {
            case BOMB_FALLING:
            {

                    location.y += unitMove;
                    if (hitCount >= MAX_HIT_COUNT || location.y >= Main.gameWindow.canvas.getHeight() - 50)
                        state = BOMB_EXPLODING;
                break;

            }
            case BOMB_EXPLODING:
            {
                color = Color.white;
                size++;
                if(size >= MAXSIZE) {
                    state = DONE;

                }
                else {
                    color=Color.yellow;
                }

                break;
            }
            case DONE:
            {
                if(type == REVIVE_BOMB && Main.turret.collidesWith(this)==false && location.y + size < 612)
                {
                    InputEvent inputEvent= new InputEvent();
                    inputEvent.type = InputEvent.BUILDING_REVIVE;
                    Main.playerInputEventQueue.queue.add(inputEvent);
                }
                else if(type == MULTIPLY_BOMB)
                {
                    InputEvent event = new InputEvent();
                    event.type = InputEvent.MULTIPLEBOMB;
                    event.location= new Point2D.Float(location.x,location.y);
                    Main.playerInputEventQueue.queue.add(event);
                    if(Main.score > 2500)
                    {
                        InputEvent event2 = new InputEvent();
                        event2.type = InputEvent.MULTIPLEBOMB;
                        event2.location= new Point2D.Float(location.x,location.y-15);
                        Main.playerInputEventQueue.queue.add(event2);
                    }
                }

                if(Main.turret.collidesWith(this)==false && location.y + size < 612)
                    Main.score+=10;
                super.done=true;
                break;
            }
        }

    }



    @Override
    public void render(Graphics2D g) {

        g.setColor(color);
        g.fillOval((int)location.x,(int)location.y,size,size);
        g.setColor(Color.white);
        g.fillOval((int)location.x,(int)location.y,size/2,size/2);
        g.fillOval((int)location.x+size/2,(int)location.y,size/2,size/2);

    }

}
