package controller.observer;

import controller.InputEvent;
import controller.Main;

public class DeathShipObserverHealTurret implements Observer {
    @Override
    public void eventReceived() {
      //  System.out.println("event raised");
        InputEvent event = new InputEvent();
        event.type= InputEvent.HEAL_TURRET;
        Main.playerInputEventQueue.queue.add(event);
    }
}
