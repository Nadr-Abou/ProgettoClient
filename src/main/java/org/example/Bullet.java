package org.example;

public class Bullet {
    private int x;
    private int y;
    private int id;
    private String s;

    public Bullet(int x, int y, int id, String s) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.s = s;
    }

    public String getS() {
        return s;
    }

    public void setS(String string) {
        this.s = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX(){
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
    
}