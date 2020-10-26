package model.Bullet;

import controller.Main;
import model.DefenseTurret;
import model.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bullet extends GameObject {
    public static final int UNIT_MOVE = 5/*pixels*/;
    public static final int INT_MISSLE_SIZE = 5;
    public static final int MAX_MISSLE_SIZE= 30;
    public static final int STATE_SHOOTING=0;
    public  static final int STATE_EXPLODING =1;
    public static final int  STATE_DONE=2;
    BulletAnimStrategy animStrategy;

    int size = INT_MISSLE_SIZE;

    Point2D.Float target; //mouse press location
    Color color;
    int state;

    @Override
    public void render(Graphics2D g2) {

        g2.setColor(color);
        //g2.setStroke(new BasicStroke(1));

        g2.fillOval((int)super.location.x - size/2,(int)super.location.y-size/2,size,size);
    }

    @Override
    public void update() {
        updateState();

        animStrategy.animate();  // Strategy Design Pattern
    }

    private void updateState() {
        if (state == STATE_SHOOTING)
        {
            if (target.distance(location) <= 3.0 || hitCount > 0)
            {
                state = STATE_DONE;
                animStrategy = new BulletAnimExploding(this);
            }
        }
        else if( state == STATE_EXPLODING)
        {
            if (size >= MAX_MISSLE_SIZE)
                state = STATE_DONE;
        }
        else if(state == STATE_DONE)
        {
            super.done = true;
        }

    }

    @Override
    public Rectangle getCollisionRadius() {
        return new Rectangle((int)location.x,(int)location.y,size,size);
    }

    public Bullet(int tx, int ty)
    {

        DefenseTurret shooter = (DefenseTurret) Main.gameData.fixedObjects.get(Main.TURRET_INDEX);
        super.location.x = shooter.barrel.x2;
        super.location.y = shooter.barrel.y2;
        target = new Point2D.Float(tx,ty);
        color = Main.turret.color;
        state= STATE_SHOOTING;
        animStrategy = new BulletAnimShooting(this);

    }
}
