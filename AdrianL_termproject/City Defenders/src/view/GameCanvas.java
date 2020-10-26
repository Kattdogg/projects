package view;

import controller.Main;
import model.DefenseTurret;
import model.GameText;
import model.MousePointer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameCanvas extends JPanel {
    public int width;
    public int height;
    Image doubleBufferImage;
    Graphics2D offScreenGraphics;
    Graphics onScreenGraphics;
    public Font font = new Font("Arial",Font.BOLD
            , 60);


    public GameCanvas()
    {


        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

// Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);
        Main.gameData.fixedObjects.add(new MousePointer());

// Set the blank cursor to the JFrame.
    }

    public void showStartScreen() {

        Main.gameData.fixedObjects.add(new GameText("City Defenders",Color.white,font,getWidth()/2-150,getHeight()/2));
        Main.gameData.fixedObjects.add(new GameText("High Score: "+Main.getHighScore(),Color.orange,font,getWidth()/2 -160,getHeight()/2+100));

        while(!Main.charScreen)
        {
            render();
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException i)
            {
                i.printStackTrace();
            }
        }

    }

    public void render() {
        width = getWidth();
        height = getHeight();

        doubleBufferImage = createImage(width,height);

        if(doubleBufferImage == null) System.exit(1);

        offScreenGraphics = (Graphics2D) doubleBufferImage.getGraphics();

        if(offScreenGraphics == null) System.exit(1);

        offScreenGraphics.setBackground(Color.BLACK);
        offScreenGraphics.clearRect(0,0,width,height);

        //render game data
        for(var obj: Main.gameData.fixedObjects) obj.render(offScreenGraphics);
        for(var obj: Main.gameData.friendObjects) obj.render(offScreenGraphics);
        for(var obj: Main.gameData.enemyObjects) obj.render(offScreenGraphics);

        // active rendering
        onScreenGraphics = getGraphics();

        if(onScreenGraphics != null)
        {
            onScreenGraphics.drawImage(doubleBufferImage, 0,0,null);
        }

        getToolkit().sync();

        if(onScreenGraphics != null) onScreenGraphics.dispose();

    }

    public void showTurretScreen() {
        Main.gameData.clear();
        //playerInputEventQueue.processInputEvents();
        Font font = new Font("Arial",Font.BOLD,30);
        DefenseTurret red= new DefenseTurret(250,500);
        red.color=Color.red;

        GameText redText = new GameText("Faster, smaller clip",Color.red,font,100, 250);
        GameText selectText = new GameText("Select you turret",Color.white,this.font,300, 100);
        GameText blueText = new GameText("Slower, larger clip",Color.blue,font,500, 250);

        DefenseTurret blue = new DefenseTurret(550,500);
        blue.color=Color.blue;

        Main.gameData.friendObjects.add(red);
        Main.gameData.friendObjects.add(blue);
        Main.gameData.fixedObjects.add(redText);
        Main.gameData.fixedObjects.add(blueText);
        Main.gameData.fixedObjects.add(selectText);
        while(Main.charScreen)
        {
            long startTime = System.currentTimeMillis();


            render();

            long endTime = System.currentTimeMillis();

            long timeSpent = endTime - startTime;

            long sleepTime= 1000/Main.FPS - timeSpent;

            if(timeSpent >= 0)
            {
                try {
                    Thread.sleep(Math.abs(sleepTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void showPlayAgainScreen() {
        Main.gameData.clear();
    }
}
