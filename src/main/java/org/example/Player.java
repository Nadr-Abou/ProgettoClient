package org.example;

import java.awt.event.*;

public class Player implements KeyListener {
    private int heart;
    private int x;
    private int y;
    private CustomFrame f;

    public Player(int heart) {
        this.heart = heart;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(y);
        switch (e.getKeyCode()) {
            case 38: //tasto freccia su
                if (this.y > 100)
                    this.y -= 5;
                break;

            case 40: //tasto freccia giù
                if (this.y < (this.f.getHeight() - 100))
                    y += 5;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void setF(CustomFrame f) {
        this.f = f;
    }
}
