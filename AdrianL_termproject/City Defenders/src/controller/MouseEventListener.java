package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseEventListener extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {

       // while(e.isControlDown())
        {
            InputEvent event= new InputEvent();
            event.event = e;
            event.type= InputEvent.MOUSE_PRESSED;

            Main.playerInputEventQueue.queue.add(event);

        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        InputEvent event= new InputEvent();
        event.event = e;
        event.type= InputEvent.MOUSE_WHEEL;
        System.out.println("wheel moved");
        Main.playerInputEventQueue.queue.add(event);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //while(e.isControlDown())
        {
            InputEvent event= new InputEvent();
            event.event = e;
            event.type= InputEvent.MOUSE_MOVED;

            Main.playerInputEventQueue.queue.add(event);

        }

    }
}
