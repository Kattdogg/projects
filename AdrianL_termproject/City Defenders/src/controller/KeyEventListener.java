package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyEventListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        InputEvent event = new InputEvent();
        event.event =e;
        event.type = InputEvent.KEY_PRESSED;

        Main.playerInputEventQueue.queue.add(event);

    }
}
