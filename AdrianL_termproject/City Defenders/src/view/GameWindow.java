package view;

import controller.KeyEventListener;
import controller.Main;
import controller.MouseEventListener;
import model.DefenseTurret;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public static final int MARGIN =15;
    public GameCanvas canvas = new GameCanvas();
    JButton startButton = new JButton("Press Here To StartGame");
    JButton redButton = new JButton("Select Red");
    JButton blueButton = new JButton("Select Blue");


    public void init() {
        setSize(900,800);
        setLocation(300,0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);

        setResizable(false);
        setTitle("City Defenders");

        var contentPane = getContentPane();
        MouseEventListener mouseEventListener = new MouseEventListener();
        KeyEventListener keyEventListener = new KeyEventListener();

        canvas.addKeyListener(keyEventListener);
        canvas.addMouseMotionListener(mouseEventListener);
        canvas.addMouseListener(mouseEventListener);

        contentPane.add(BorderLayout.CENTER,canvas);


        startButton.setFocusable(false);
        blueButton.setVisible(false);
        blueButton.setFocusable(false);
        redButton.setVisible(false);
        redButton.setFocusable(false);

        startButton.addActionListener(e  -> {
           if(!Main.isRunning && !Main.charScreen)
           {
               blueButton.setVisible(true);
               redButton.setVisible(true);
               Main.charScreen = true;
               startButton.setVisible(false);
           }
           else if(!Main.isRunning)
           {
               Main.charScreen=false;
           }
           else
           {
               System.exit(0);
           }
        });

        JPanel buttonPanel = new JPanel();



        buttonPanel.add(BorderLayout.CENTER,redButton);
        redButton.addActionListener(e ->{
            Main.turret = new DefenseTurret(Main.gameWindow.getWidth()/2,Main.gameWindow.getHeight()-250);
            Main.turret.color = Color.red;
            Main.turret.unitMove= 15;
            Main.turret.bulletMove=5;
            Main.turret.maxClipSize=100;

            Main.isRunning = true;
            Main.charScreen = false;
            startButton.setText("Press Here To Quit Game");
            startButton.setVisible(true);
            redButton.setVisible(false);
            blueButton.setVisible(false);

        });


        buttonPanel.add(BorderLayout.CENTER,startButton);
        buttonPanel.add(BorderLayout.CENTER,blueButton);
        blueButton.addActionListener(e ->{
            Main.turret = new DefenseTurret(Main.gameWindow.getWidth()/2,Main.gameWindow.getHeight()-250);
            Main.turret.color = Color.blue;
            Main.turret.unitMove= 5;
            Main.turret.bulletMove=5;
            Main.turret.maxClipSize=200;
            Main.isRunning = true;
            Main.charScreen=false;
            startButton.setText("Press Here to Quit Game");
            redButton.setVisible(false);
            blueButton.setVisible(false);
            startButton.setVisible(true);

        });

        contentPane.add(BorderLayout.SOUTH,buttonPanel);






        canvas.setFocusable(true);
        setVisible(true);
    }

    public int getXBound()
    {
        return getWidth() - 25;
    }
    public int getYBount()
    {
        return getHeight() -25;
    }
}
