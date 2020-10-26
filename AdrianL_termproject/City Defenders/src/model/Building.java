package model;

import java.awt.*;

public class Building extends GameObject {
    public  int size = 100;
    public static int SIZE =100;
    private Color color;
    Rectangle rectangle;


    public Building(int x, int y)
    {
        super(x,y);
        color=Color.GREEN;
        size = SIZE;
        rectangle= new Rectangle((int)location.x,(int)location.y,size,size);


    }
    @Override
    public Rectangle getCollisionRadius() {
        return new Rectangle((int)location.x,(int)location.y,size,size);
    }

    @Override
    public void update() {
        if(hitCount <50)
            color= Color.green;
        else if(hitCount >= 50 && hitCount < 75)
            color = Color.orange;
        else if(hitCount >= 75 && hitCount <=100)
            color = Color.red;
        else
        {
            size = 120;
            color = Color.yellow;

            super.done=true;
        }


    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);

        g.draw(rectangle);
      //  System.out.println(location.y);
        g.setFont(new Font("Arial",Font.BOLD,30));
        g.drawString(Integer.toString(hitCount),(int)location.x+10,(int)location.y+size-2);

    }
}
