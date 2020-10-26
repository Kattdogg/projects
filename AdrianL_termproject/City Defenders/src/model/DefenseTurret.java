package model;

import controller.Main;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DefenseTurret extends GameObject {
    public final int BASE_SIZE = 30; //pixels
    public final int BARREL_LENGTH = 25;
    public  int unitMove = 10; //pixels moved by arrow key
    public int bulletMove=0;
    public int bulletCount = 0;
    public int maxClipSize;
    public Rectangle2D.Float base;
    public Ellipse2D.Float base2;
    public Shape shape;
    public Line2D.Float barrel;
    public Line2D.Float barrel2;
    public Color color;
    public Image image;


    public DefenseTurret(int x, int y) {
        super(x, y);
        base = new Rectangle2D.Float(x - BASE_SIZE / 2, y - BASE_SIZE,
                BASE_SIZE, BASE_SIZE);
        base2 = new Ellipse2D.Float(x,y,BASE_SIZE,BASE_SIZE);

        barrel = new Line2D.Float(x + BASE_SIZE / 2, y, x + BASE_SIZE / 2, y - BARREL_LENGTH);



    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(7));
        g2.draw(barrel);
        g2.setColor(color);
        g2.draw(base2);


      //  g2.fill3DRect(((int) location.x), ((int) location.y), BASE_SIZE, BASE_SIZE, true);


    }

    @Override
    public void update() {
        MousePointer mp = (MousePointer) Main.gameData.fixedObjects.get(Main.MOUSE_INDEX);
        float tx = mp.location.x;
        float ty = mp.location.y;

        double rad = Math.atan2(ty - super.location.y, tx - super.location.x + BASE_SIZE / 2);

        float barrel_y = (float) (BARREL_LENGTH * Math.sin(rad));
        float barrel_x = (float) (BARREL_LENGTH * Math.cos(rad));


        barrel.x1 = super.location.x + BASE_SIZE / 2;
        barrel.y1 = super.location.y;

        barrel.x2 = super.location.x + BASE_SIZE / 2 + barrel_x;
        barrel.y2 = super.location.y + barrel_y;


        base2.x = location.x ;//- BASE_SIZE / 2;
        base2.y = location.y ;//- BASE_SIZE / 2;

        updateStatus();
    }
    void updateStatus()
    {


    }

    @Override
    public Rectangle getCollisionRadius() {
        return new Rectangle((int) location.x, (int) location.y, BASE_SIZE, BASE_SIZE);
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public int getMaxClipSize() {
        return maxClipSize;
    }

    public int getHealth() {
        return hitCount;
    }
}
