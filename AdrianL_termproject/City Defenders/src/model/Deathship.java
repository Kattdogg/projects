package model;

import controller.InputEvent;
import controller.Main;
import controller.observer.DeathShipObserverHealTurret;

import java.awt.*;

public class Deathship extends Warship {
    boolean hasReachedEnd = false;
    long spawnTime;
    long lastDropTime=0;
    float targetLocation;

    public Deathship() {
        attachListener(new DeathShipObserverHealTurret());

        location.x = Main.gameWindow.canvas.getWidth()/2;

        location.y=30;
        color = Color.YELLOW;

        targetLocation = Main.gameData.friendObjects.get(Main.rndGen.nextInt(Main.gameData.friendObjects.size())).location.x;
        lastDropTime = System.currentTimeMillis();
        spawnTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if(location.x < targetLocation) location.x++;
        else if(location.x > targetLocation)location.x--;
        if((int)targetLocation == (int)location.x)
        {
            if((System.currentTimeMillis() - spawnTime)/1000 >= 25) {
                super.done = true;
                notifyEvent();
            }
            else if((System.currentTimeMillis() - lastDropTime)/1000 >= 1)
            {
                lastDropTime= System.currentTimeMillis();
                InputEvent dropBombs= new InputEvent();

                dropBombs.type= InputEvent.BOMB_CREATE;
                dropBombs.location.x = (int)location.x + 50;
                dropBombs.location.y = (int)location.y +100;
                Main.playerInputEventQueue.queue.add(dropBombs);

            }

        }



    }
}
