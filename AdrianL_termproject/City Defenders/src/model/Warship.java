package model;

import controller.InputEvent;
import controller.Main;
import controller.observer.Observer;
import controller.observer.Subject;
import controller.observer.WarshipObserverHealBuildings;

import java.awt.*;
import java.util.ArrayList;

public class Warship extends GameObject implements Subject {
    int lastSpawnLocation = 10;
    Color color= Color.pink;


    ArrayList<Observer> listeners = new ArrayList<>();



    public Warship()
    {
        super(10,30);
        attachListener(new WarshipObserverHealBuildings());
    }

    @Override
    public Rectangle getCollisionRadius() {
        return new Rectangle((int)location.x,(int)location.y,100,100);
    }

    @Override
    public void update() {
        location.x++;

      if((int)location.x - lastSpawnLocation == 50 && location.x +200 < Main.gameWindow.canvas.getWidth() - 15)
      {
          lastSpawnLocation=(int) location.x;
          InputEvent dropBombs= new InputEvent();
          dropBombs.type= InputEvent.BOMB_CREATE;
          dropBombs.location.x = (int)location.x + 50;
          dropBombs.location.y = (int)location.y +100;
          Main.playerInputEventQueue.queue.add(dropBombs);
         // Main.gameData.enemyObjects.add(BombFactory.getBomb(BombFactory.DEFAULTBOMB,(int)location.x + 100,(int)location.y +100)
          //);
      }

      else if( (location.x ) > Main.gameWindow.getWidth()) {
          super.done=true;
          notifyEvent();
      }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        //g.fillArc((int)location.x,(int)location.y,100,100,90,360);
        g.fill3DRect((int)location.x,(int)location.y,100,100,true);
        g.setColor(Color.black);

        g.drawArc((int)location.x,(int)location.y,100,100,180,360);

    }


    @Override
    public void attachListener(Observer o) {
        listeners.add(o);

    }

    @Override
    public void detachListener(Observer o) {
        listeners.remove(o);

    }

    @Override
    public void notifyEvent() {
        for(var o: listeners)
            o.eventReceived();

    }


}
