package controller.observer;

import controller.InputEvent;
import controller.Main;

public class WarshipObserverHealBuildings implements  Observer {
    @Override
    public void eventReceived() {
        InputEvent event= new InputEvent();
        event.type= InputEvent.HEAL_BUILDINGS;
        Main.playerInputEventQueue.queue.add(event);
    }
}
