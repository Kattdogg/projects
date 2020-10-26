package model;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class GameObject {
    public Point2D.Float location;
    public boolean done = false;
    public int hitCount=0;
    public int MAX_HIT_COUNT=1;
    public int health = 0;

    public GameObject(float x, float y)
    {
        location = new Point2D.Float(x,y);
    }

    public GameObject()
    {
        this(0,0);
    }

    public boolean collidesWith(GameObject obj)
    {

        return getCollisionRadius().intersects(obj.getCollisionRadius());
    }

    public abstract Rectangle getCollisionRadius();
    public abstract void update();
    public  abstract void render(Graphics2D g);

}
