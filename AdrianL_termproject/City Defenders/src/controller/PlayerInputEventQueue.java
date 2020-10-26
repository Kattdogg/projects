package controller;

import model.*;
import model.Bullet.Bullet;
import view.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PlayerInputEventQueue {
    public LinkedList<InputEvent> queue = new LinkedList<>();



    void spawnBomb()
    {
        long currentTime = System.currentTimeMillis();
        long timeElapsed= (currentTime - Main.lastSpawnTime)/1000;
        if(timeElapsed > Main.diffTime)
        {
            Main.gameData.enemyObjects.add(BombFactory.getBomb(BombFactory.getRandID(),
                    Main.rndGen.nextInt(Main.gameWindow.canvas.getWidth()-GameWindow.MARGIN-Bomb.MAXSIZE),30));

            Main.lastSpawnTime = currentTime;

        }

    }

    public void processInputEvents() {
        while(!queue.isEmpty())
        {

            InputEvent inputEvent;

            try{
                 inputEvent = queue.removeFirst();

            }catch (NoSuchElementException ex)
            {
                return;
            }


            switch (inputEvent.type)
            {
                case InputEvent.KEY_PRESSED:
                {
                    KeyEvent keyEvent = (KeyEvent) inputEvent.event;
                    DefenseTurret turret =(DefenseTurret) Main.gameData.fixedObjects.get(Main.TURRET_INDEX);

                    switch (keyEvent.getKeyCode())
                    {
                        case KeyEvent.VK_LEFT:
                        {
                            if (turret.location.x <= GameWindow.MARGIN)
                                break;
                            else turret.location.x-=turret.unitMove;
                            break;
                        }
                        case KeyEvent.VK_RIGHT:
                        {
                            if (turret.location.x >= Main.gameWindow.canvas.getWidth()-GameWindow.MARGIN - turret.BASE_SIZE )
                                break;
                            else turret.location.x+=turret.unitMove;
                            break;

                        }
                        case KeyEvent.VK_ENTER:
                        {
                            if(Main.turret.bulletCount <= 0 || Main.turret.bulletCount <= 10)
                                Main.turret.bulletCount=0;
                            else
                                Main.turret.bulletCount-=10;
                            break;
                        }


                    }

                    break;
                }


                case InputEvent.MOUSE_MOVED:
                {
                    MousePointer mousePointer = (MousePointer) Main.gameData.fixedObjects.get(0);
                    MouseEvent mouseEvent = (MouseEvent) inputEvent.event;

                    mousePointer.location.x = mouseEvent.getX();
                    mousePointer.location.y = mouseEvent.getY();
                    break;
                }
                case InputEvent.MOUSE_PRESSED:
                {
                    MouseEvent mouseEvent = (MouseEvent) inputEvent.event;

                    if(Main.turret.bulletCount < Main.turret.maxClipSize)
                    {
                        Bullet b = new Bullet(mouseEvent.getX(),mouseEvent.getY());
                        Main.gameData.friendObjects.add(b);
                        Main.turret.bulletCount++;
                    }
                    else
                    {
                        Main.turret.bulletCount=Main.turret.maxClipSize;
                    }

                    break;
                }
                case InputEvent.HEAL_TURRET:
                {
                    if(Main.turret.hitCount<=10 )Main.turret.hitCount = 0;
                    else Main.turret.hitCount -=10;
                }
                case InputEvent.HEAL_BUILDINGS:
                {
                    for(var b: Main.gameData.friendObjects)
                    {
                        if(b.hitCount <= 10) b.hitCount =0;
                        else b.hitCount-=10;
                    }
                    break;
                }

                case InputEvent.BOMB_CREATE:
                {

                    Main.gameData.enemyObjects.add(BombFactory.getBomb(BombFactory.DEFAULTBOMB,(int)inputEvent.location.x,(int)inputEvent.location.y));
                    break;

                }
                case InputEvent.MULTIPLEBOMB:
                {
                    Main.gameData.enemyObjects.add(BombFactory.getBomb(BombFactory.DEFAULTBOMB,
                            (int)inputEvent.location.x+40,(int)inputEvent.location.y));
                    Main.gameData.enemyObjects.add(BombFactory.getBomb(BombFactory.DEFAULTBOMB,
                            (int)inputEvent.location.x-40,(int)inputEvent.location.y));
                    break;
                }
                case InputEvent.WARSHIP_CREATE:
                {
                    Main.gameData.enemyObjects.add(new Warship());
                  //  Main.warShipTime = System.currentTimeMillis();
                    break;
                }
                case InputEvent.DEATHSHIP_CREATE:
                {
                    Main.gameData.enemyObjects.add(new Deathship());
                    break;
                }
                case InputEvent.BUILDING_REVIVE:
                {
                    for(var b: Main.gameData.friendObjects) {
                        try {
                            Building building = (Building) b;
                            if (b != null) {
                                if (b.hitCount > 0) {
                                    Main.gameData.removedObjects.add(b);
                                    b.done = true;
                                    Main.gameData.friendObjects.add(new Building((int) b.location.x, (int) b.location.y));
                                    break;
                                }
                            }

                        } catch (Exception ex) {
                            continue;
                        }
                    }



                }
            }
        }
    }
}
