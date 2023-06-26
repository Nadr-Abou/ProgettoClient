package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomFrame extends JFrame implements Runnable {
    private Thread thread;
    Player leftPlayer;
    Player rightPlayer;

    List<Bullet> bullets = new ArrayList<>();
    /*int bulletX1 = -10;
    int bulletY1 = -10;
    int bulletX2 = -10;
    int bulletY2 = -10;*/


    public CustomFrame(Player thisPlayer) throws HeadlessException {
        this.addKeyListener(thisPlayer);
        this.getContentPane().setBackground(Color.cyan);
        bullets.add(new Bullet(-10,-10));
        bullets.add(new Bullet(-10,-10));
        bullets.add(new Bullet(-10,-10));
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

        for(Bullet bullet : bullets ){
            drawBullet(g,bullet.getX(),bullet.getY());
        }

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

    private void drawBullet(Graphics g, int x, int y) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("Bullet.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, x, y, 40, 40, null);
    }


    public void fire(int x, int y) {
        for(int i=0; i < bullets.size(); i++){
            if(bullets.get(i).getX() < 0){
                bullets.get(i).setX(x);
                bullets.get(i).setY(y);
                break;
            }
        }
        //bullets.add(new Bullet(-10,-10));
        //fire(x,y);
    }


    @Override
    public void run() {

        while (true) {


            for(Bullet b : bullets){
                System.out.println(b.getX());
                if(b.getX() >= 0 && b.getX() < 1230){
                    b.setX( b.getX() + 40);
                }
                repaint(b.getX(),b.getY(),40, 40);
            }

            for(Bullet b : bullets){
                if(b.getX() > 1230){
                    b.setX(-10);
                    b.setY(-10);
                }
            }

            try{
                thread.sleep(500);
            }catch (Exception e){

            }
        }
    }
}