package model;

import java.awt.*;

public class GameText extends GameObject {
    public String text;
    Color color;
    public static Font font;

    public GameText(String text, Color color,Font font, int x,int y)
    {
        super(x,y);
        this.text=text;
        this.color = color;
        GameText.font = font;
    }
    @Override
    public Rectangle getCollisionRadius() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text,(int)location.x,(int)location.y);

    }
}
