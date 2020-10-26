package model;

import controller.Main;

import java.awt.*;

public class BombFactory {

    public static final int DEFAULTBOMB=0;
    public static final int REVIVEBOMB=1;
    public static final int MULTIPLYBOMB=2;
    public static final int TARGETBOMB =3 ;
    public static final int SECRETBOMB = 4;
    private static final int TURRETBOMB = 5;
    public static int types =6;



     public static Bomb getBomb(int ID,int x,int y)
     {
         Bomb b= new Bomb(x,y);
         switch (ID)
         {
             case 0:  //default Bomb
             {

                break;
             }
             case REVIVEBOMB: // revive bomb
             {
                 if(Main.rndGen.nextInt(100)<20)
                 {
                     b.color = Color.cyan;
                     b.type=Bomb.REVIVE_BOMB;
                 }
                 break;
             }
             case MULTIPLYBOMB: //multiply bomb
             {

                     b.color = Color.red;
                     b.type = Bomb.MULTIPLY_BOMB;

                     if (b.location.x < 100) b.location.x =100;
                     else if( Main.gameWindow.canvas.getWidth()- b.location.x < 100) b. location.x = Main.gameWindow.canvas.getWidth() - 120;


                 break;
             }
             case TURRETBOMB:
             {
                 b.color = Color.magenta;
                 b.type= TURRETBOMB;
                 b.location.x = Main.turret.location.x;
                 if(Main.score > 1500) b.unitMove=5;
                 else b.unitMove=3;
                 break;
             }
             case TARGETBOMB:
             {
                 b.color = Color.blue;
                 b.type = TARGETBOMB;
                 int target =0;
                 if(Main.gameData.friendObjects.isEmpty())
                     target= (int)Main.turret.location.x;
                 else
                    target = Main.rndGen.nextInt(Main.gameData.friendObjects.size());

                 b.location.x = Main.gameData.friendObjects.get(target).location.x;
                 if(Main.score > 2000) b.unitMove = 5;
                else b.unitMove= 3;
                 break;
             }

         }
         return b;
     }

     public static int getRandID()
     {
         //if(Main.score > 1000) types = 5;
         return Main.rndGen.nextInt(types);
     }
}
