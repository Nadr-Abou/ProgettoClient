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
import java.util.Date;
import java.util.List;

public class CustomFrame extends JFrame implements Runnable {
    private Thread thread;
    Player leftPlayer;
    Player rightPlayer;
    boolean Connected = true;
    Date d = new Date();
    static List<Bullet> bullets = new ArrayList<>();
    static List<Bullet> otherBullets = new ArrayList<>();

    public CustomFrame(Player thisPlayer) throws HeadlessException {
        this.addKeyListener(thisPlayer);
        this.getContentPane().setBackground(Color.cyan);
        bullets.add(new Bullet(-10,-10, 1, "This"));
        bullets.add(new Bullet(-10,-10, 2, "This"));
        bullets.add(new Bullet(-10,-10, 3, "This"));
        bullets.add(new Bullet(getWidth()+10,getHeight()+10, 1, "Other"));
        bullets.add(new Bullet(getWidth()+10,getHeight()+10, 2, "Other"));
        bullets.add(new Bullet(getWidth()+10,getHeight()+10, 3, "Other"));
        /*otherBullets.add(new Bullet(getWidth()+10,0));
        otherBullets.add(new Bullet(getWidth()+10,0));
        otherBullets.add(new Bullet(getWidth()+10,0));*/
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
            drawBullet(g, bullet.getX(), bullet.getY());
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

        //Doppia bufferizzazione JFRAME
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
        for(Bullet b : bullets){
            if(b.getX() < 0 && b.getS().equals("This")){
                b.setX(x+140);
                b.setY(y+25);
                break;
            }
        }
    }

    public void fireOpposite(int x, int y) {
        for(Bullet b : bullets){
            if(b.getX() > 1220 && b.getS().equals("Other")){
                b.setX(x-140);
                b.setY(y-25);
                break;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            for(Bullet b : bullets){
                if(b.getX() >= 0 && b.getX() < 1220){
                    if (b.getS().equals("This")) {
                        b.setX(b.getX() + 80);
                        Client.sendBulletData(b);
                        repaint(b.getX() - 80, b.getY(), 40, 40);
                        repaint(b.getX(), b.getY(), 40, 40);
                    } else if (b.getS().equals("Other")) {
                        System.out.println("Other x: "+b.getX());
                        b.setX(b.getX() - 80);
                        repaint(b.getX() - 80, b.getY(), 40, 40);
                        repaint(b.getX(), b.getY(), 40, 40);
                    }
                }
                if(b.getX() > 1220){
                    if (b.getS().equals("This")) {
                        b.setX(-10);
                        b.setY(-10);
                    }
                } else if (b.getX() < 0) {
                    if (b.getS().equals("Other")) {
                        b.setX(getWidth()+10);
                        b.setY(getHeight()+10);
                    }
                }
                try{
                    if( b.getX() >= rightPlayer.getX() && ((b.getY() >= rightPlayer.getY()) && (b.getY() <= (rightPlayer.getY()+100)) )){
                        int decHeart = rightPlayer.getHeart() - 1;
                        rightPlayer.setHeart( decHeart );
                        Client.otherPlayer.setHeart(decHeart);
                        Client.sendPlayerData();
                        b.setX(-10);
                        b.setY(-10);
                        if(rightPlayer.getHeart() == 0){}
                        repaint();
                    }
                }catch (Exception e){
                    System.out.println("No right player at the moment...");
                }
            }
            /*for(Bullet b : otherBullets){
                if(b.getX() >= 0 && b.getX() < 1280){
                    b.setX( b.getX() + 80);
                    repaint(b.getX()-80,b.getY(),40, 40);
                    repaint(b.getX(),b.getY(),40, 40);
                }
                if(b.getX() > 1220){
                    b.setX(-10);
                    b.setY(-10);
                    otherBullets.remove(b);
                }
            }*/
            try{
                thread.sleep(750);
            }catch (Exception e){}
        }
    }
}