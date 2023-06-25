package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CustomFrame extends JFrame implements Runnable {
    private Thread thread;
    Player leftPlayer;
    Player rightPlayer;
    int bulletX1 = -10;
    int bulletY1 = -10;
    int bulletX2 = -10;
    int bulletY2 = -10;


    public CustomFrame(Player thisPlayer) throws HeadlessException {
        this.addKeyListener(thisPlayer);
        this.getContentPane().setBackground(Color.cyan);
        Thread th = new Thread(this);
        th.start();
    }

    public void setLeftPlayer(Player leftPlayer) {
        this.leftPlayer = leftPlayer;
    }

    public void setRightPlayer(Player rightPlayer) {
        this.rightPlayer = rightPlayer;
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (leftPlayer == null || rightPlayer == null) {
            blockDrawImage(g);
            return;
        }

        int w = this.getWidth();
        int h = this.getHeight();

        for (int i = 1; i <= leftPlayer.getHeart(); i++) {
            heartDrawImage(g, 60 * i);
        }
        for (int i = 1; i <= rightPlayer.getHeart(); i++) {
            heartDrawImage(g, (w - 50) - (60 * i));
        }

        g.setColor(Color.yellow);
        g.drawLine(0, 87, this.getWidth(), 87);
        g.drawLine(0, 88, this.getWidth(), 88);

        rightTankImage(g);
        leftTankImage(g);

        drawBullet(g);
        drawBullet2(g);
    }

    private void blockDrawImage(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("initialIMG.png");
        BufferedImage img = null;

        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private void heartDrawImage(Graphics g, int x) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("Heart.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, x, 35, this.getWidth() / 24, this.getHeight() / 16, null);
    }

    private void rightTankImage(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("rightTank.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, rightPlayer.getX(), rightPlayer.getY(), 100, 100, null);
    }

    private void leftTankImage(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("LeftTank.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, leftPlayer.getX(), leftPlayer.getY(), 100, 100, null);
    }

    private void drawBullet(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("Bullet.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, bulletX1, bulletY1, 40, 40, null);
    }

    private void drawBullet2(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("Bullet.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, bulletX2, bulletY2, 40, 40, null);
    }

    public void fire(int x, int y) {
        if (bulletX1 < 0 && bulletY1 < 0) { //controlla se il proiettile1 è inattivo
            bulletX1 = x;
            bulletY1 = y;
        } else {

            bulletX2 = x;
            bulletY2 = y;
        }
    }


    @Override
    public void run() {

        while (true) {

            if (bulletX1 > 0 && bulletY1 > 0) { //controlla se un qualunque proiettile sia dentro l'area di gioco se si allora lo sposta a destra
                bulletX1-=40;
                repaint(bulletX1,bulletY1,80,40);
            }
            if (bulletX2 > 0 && bulletY2 > 0) {
                bulletX2-=40;
                repaint(bulletX2,bulletY2,80,40);
            }

            if (bulletX1 > 1230) {
                bulletX1= -10;
                bulletY1= -10;
            }
            if (bulletX2 > 1230) {
                bulletX2= -10;
                bulletY2= -10;
            }

            try{
                thread.sleep(500);
            }catch (Exception e){

            }
        }
    }
}