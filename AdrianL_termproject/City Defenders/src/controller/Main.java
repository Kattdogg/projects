package controller;

import model.*;
import view.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Main {

    public static Random rndGen= new Random();
    public static final int TURRET_INDEX=1;
    public static final int MOUSE_INDEX=0;
    public static int score =0;
    public static double diffTime = 5;
    public static int highScore;

    public static GameWindow gameWindow;
    public static GameData gameData;
    public static PlayerInputEventQueue playerInputEventQueue;
    public static long lastSpawnTime;
    public static int FPS = 60;
    public static boolean isRunning = false;
    public static boolean charScreen=false;
    public static DefenseTurret turret;
    //public static long warShipTime;
    private static int warshipScore = 100;
    private static int deathShipScore= 500;


    public static void main(String[] args)
    {
        highScore= getHighScore();
        gameData = new GameData();
        playerInputEventQueue = new PlayerInputEventQueue();
        gameWindow = new GameWindow();
        gameWindow.init();



        gameWindow.canvas.showStartScreen();
        gameWindow.canvas.showTurretScreen();
        initGame();
        gameLoop();
        gameWindow.canvas.showPlayAgainScreen();
    }

    static void initGame()
    {
        gameData.clear();

        gameData.fixedObjects.add(new MousePointer());

        gameData.fixedObjects.add(turret);
        GameText.font =new Font("Arial",Font.BOLD
                , 20);

        GameText scoreText = new GameText("",Color.yellow,GameText.font,700 ,20){

            @Override
            public void update() {
                text= "Score: "+getScore();
                super.update();
            }

        };

        GameText bulletText = new GameText("",Color.cyan,GameText.font,300 ,20){

            @Override
            public void update() {
                text= "Clip: "+turret.getBulletCount() + " / "+ turret.getMaxClipSize();
                super.update();
            }

        };
        GameText healthText = new GameText("",Color.pink,GameText.font,100 ,20){

            @Override
            public void update() {
                text= "HC: "+turret.getHealth()+" / "+75;
                super.update();
            }

        };
        placeBuildings();
        gameData.fixedObjects.add(scoreText);
        gameData.fixedObjects.add(bulletText);
        gameData.fixedObjects.add(healthText);



    }

    static void gameLoop()
    {
        lastSpawnTime = System.currentTimeMillis();

        while(isRunning)
        {
            incDifficulty();
            playerInputEventQueue.spawnBomb();
            spawnWarship();
            spawnDeathship();


            long startTime = System.currentTimeMillis();


            playerInputEventQueue.processInputEvents();
            processCollisions();
            gameData.update();
            gameWindow.canvas.render();

            long endTime = System.currentTimeMillis();
            long timeSpent = endTime - startTime;

            long sleepTime= 1000/FPS - timeSpent;

            if(timeSpent >= 0)
            {
                try {
                    Thread.sleep(Math.abs(sleepTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isGameOver();

        }

    }

    private static void isGameOver()
    {

        if(Main.turret.hitCount > 75 || gameData.friendObjects.isEmpty())
        {
            int option= JOptionPane.showOptionDialog(null,"You have lost! Score: "+ getScore()+"\n Play Again?"
                    ,"Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null);
            isRunning = false;
            boolean exit=false;
            highScore= getHighScore();
            if(score > highScore)
            {
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new File("hs.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pw.write(Integer.toString(score));
                pw.close();
           }
            if(option == JOptionPane.YES_OPTION)
            {

                gameData = new GameData();
                playerInputEventQueue = new PlayerInputEventQueue();
                gameWindow.dispose();
                gameWindow = new GameWindow();
                gameWindow.init();
                score=0;
                diffTime=5;
                warshipScore=100;
                deathShipScore=500;
                gameWindow.canvas.showStartScreen();
                gameWindow.canvas.showTurretScreen();
                initGame();
                gameLoop();

            }
            else
            {
                exit = true;

            }


            if(exit) System.exit(0);
        }

    }

    public static int getHighScore() {
        String sScore="0";
        int hScore=0;

        try {
            FileReader reader= new FileReader("hs.txt");
            BufferedReader bufferedReader= new BufferedReader(reader);
            sScore= bufferedReader.readLine();
            bufferedReader.close();
        } catch (Exception e)
        {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new File("hs.txt"));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            pw.write("0");
            pw.close();
        }



        return Integer.parseInt(sScore);
    }

    static void placeBuildings()
    {
       for(int i = gameWindow.canvas.getWidth() - GameWindow.MARGIN - Building.SIZE;
           i > GameWindow.MARGIN; i-=Building.SIZE + 50)
       {
        Building b= new Building(i,gameWindow.canvas.getHeight() - Building.SIZE- GameWindow.MARGIN);
        gameData.friendObjects.add(b);
       }


    }

    public static int getScore()
    {
        return  score;
    }
    public static void incDifficulty()
    {
      if( score <= 100) diffTime=4;
      else if(score >100 && score <=200)diffTime=3;
      else if(score > 200 && score <=300) diffTime=2;
      else if (score < 300 && score <=400) diffTime=1;
      else if(score >= 1000) diffTime=0.5;

    }


    public static void spawnWarship()
    {
        if(getScore() >= warshipScore)
        {
            warshipScore+=200;
            InputEvent warshipEvent = new InputEvent();
            warshipEvent.type = InputEvent.WARSHIP_CREATE;
            playerInputEventQueue.queue.add(warshipEvent);
        }
    }
    public static void spawnDeathship()
    {
        if(getScore() >= deathShipScore)
        {
            deathShipScore+=500;
            InputEvent warshipEvent = new InputEvent();
            warshipEvent.type = InputEvent.DEATHSHIP_CREATE;
            playerInputEventQueue.queue.add(warshipEvent);
        }
    }


    private static void processCollisions() {

        for(var enemy: gameData.enemyObjects)
        {
            if(turret.collidesWith(enemy))
            {
                turret.hitCount++;
                enemy.hitCount++;
            }
        }


        for(var friend: gameData.friendObjects)
        {
            for(var enemy: gameData.enemyObjects) {

                if (friend.collidesWith(enemy)) {


                    friend.hitCount++;
                    enemy.hitCount++;

                }


            }
        }

    }

}
