package controller;

import java.awt.geom.Point2D;
import java.util.EventObject;

public class InputEvent {

    public static final int MOUSE_PRESSED=0;
    public static final int MOUSE_MOVED=1;
    public static final int KEY_PRESSED=2;
    public static final int BOMB_CREATE = 3;
    public static final int BUILDING_REVIVE = 4;
    public static final int MULTIPLEBOMB = 5;
    public static final int MOUSE_WHEEL = 6;
    public static final int WARSHIP_CREATE=7;
    public static final int DEATHSHIP_CREATE = 8;
    public static final int HEAL_BUILDINGS = 9;
    public static final int HEAL_TURRET=10;


    public EventObject event;
    public int type;
    public int bombType;
    public Point2D.Float location= new Point2D.Float();

}
