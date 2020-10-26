package model;

import java.awt.*;

public class MousePointer extends GameObject {

    public final int SIZE =25;




    @Override
    public Rectangle getCollisionRadius() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {

        g.setColor(Color.red);




        g.drawLine((int)location.x  - SIZE, (int)location.y,
                (int)location.x + SIZE, (int)location.y);
        g.drawLine((int)location.x,(int)location.y - SIZE,
                (int)location.x,(int)location.y + SIZE);

    }


}
