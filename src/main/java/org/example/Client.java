package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    static Player thisPlayer;
    static Player otherPlayer;
    static CustomFrame f;
    static PrintWriter out;
    static BufferedReader in;
    static P thisP = null;
    static P otherP = null;
    static Gson g = new Gson();
    static boolean enterPressed = false;
    static boolean recived = false;


    public Client(Player thisPlayer, Player otherPlayer, CustomFrame f) {
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.f = f;
        clientMain();
    }

    public Client() {
    }

    static void clientMain() {
        String hostName = "127.0.0.1";
        int portNumber = 1234;
        Socket clientSocket = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("cannot reach server " + e);
        }

        try {
            assert clientSocket != null;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("YOU MUST CONNECT THE SERVER!!");
        }

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("cannot allocate bufferedreader");
        }

        try {
            /*SPACCHETTAMENTO JSON NELLE COORDINATE*/
            String paramIniziali = in.readLine();
            thisP = g.fromJson(paramIniziali, P.class);

            paramIniziali = in.readLine();
            otherP = g.fromJson(paramIniziali, P.class);

        } catch (Exception e) {
            System.out.println("Si è verificato un errore per via del messaggio ricevuto oppure il server non è connesso");
        }

        //Coordinate dei giocatori prese dal server
        setCoordinatesFromP();

        thisPlayer.setF(f);
        otherPlayer.setF(f);

        if (thisPlayer.getX() < 500) {
            f.setLeftPlayer(thisPlayer);
            f.setRightPlayer(otherPlayer);
        } else {
            f.setLeftPlayer(otherPlayer);
            f.setRightPlayer(thisPlayer);
        }

        f.repaint();

        while (true) {
            String s;
            P myPlayer = null;
            Bullet b = null;
            try {
                if ((s = in.readLine()) != null) {
                    System.out.println(s);
                    if (s.equals("This player")) {
                        s = in.readLine();
                        myPlayer = g.fromJson(s, P.class);
                        thisP.setNHeart(myPlayer.getNHeart());
                        thisPlayer.setHeart(thisP.getNHeart());
                        f.repaint(0,0,1220,85);
                    } else if (s.equals("Other player")) {
                        s = in.readLine();
                        myPlayer = g.fromJson(s, P.class);
                        otherP = myPlayer;
                        otherPlayer.setY(otherP.getY());
                        otherPlayer.setHeart(otherP.getNHeart());
                        f.repaint(otherPlayer.getX(), otherPlayer.getY(), 100, 180);
                        f.repaint(otherPlayer.getX(), otherPlayer.getY() - 80, 101, 180);
                    } else if (s.equals("Bullet")) {
                        s = in.readLine();
                        b = g.fromJson(s, Bullet.class);
                        System.out.println("bullet nadr :" + b.getX() + " e anche " + (f.getWidth() - b.getX()));
                        b.setX(f.getWidth() - b.getX());
                        System.out.println("Other bullet: " + s);
                        if (b.getS().equals("This")) {
                            for (Bullet bullet : CustomFrame.bullets) {
                                if (bullet.getS().equals("Other")) {
                                    if (b.getId() == bullet.getId()) {
                                        b.setS("Other");
                                        bullet = b;
                                        f.fireOpposite(bullet.getX(), bullet.getY());
                                        System.out.println(g.toJson(bullet));
                                        break;
                                    }
                                }
                            }
                        }
                        System.out.println("chiamato una volta spero");
                        //f.repaint();
                    }
                    System.out.println(s);
                    try {
                        if (g.fromJson(s, Command.class).command.equals("exit")) {
                            f.setConnected(false);
                            f.repaint();
                            break;
                        }
                        else if (g.fromJson(s, Command.class).command.equals("restart")) {
                            System.out.println("restart");
                            recived=true;
                            do {
                                System.out.println("dentro while : " + enterPressed);
                                if (enterPressed) {
                                    enterPressed = false;
                                    restart(true);
                                    System.out.println("restart");
                                    break;
                                }
                            } while (true);
                        } else if (g.fromJson(s, Command.class).command.equals("ready")) {
                            System.out.println("readyMetodo");
                            restart(false);
                        }
                    } catch (Exception e) {}
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void setCoordinatesFromP() {
        thisPlayer.setY(thisP.getY());
        thisPlayer.setX(thisP.getX());
        thisPlayer.setHeart(thisP.getNHeart());
        otherPlayer.setY(otherP.getY());
        otherPlayer.setX(otherP.getX());
        otherPlayer.setHeart(otherP.getNHeart());
        System.out.println("This player: {x: " + thisPlayer.getX() + " y: " + thisPlayer.getY() + "}");
        System.out.println("Other player: {x: " + otherPlayer.getX() + " y: " + otherPlayer.getY() + "}");
    }

    public static void sendPlayerData() {
        thisP.setX(thisPlayer.getX());
        thisP.setY(thisPlayer.getY());
        thisP.setNHeart(thisPlayer.getHeart());
        otherP.setX(otherPlayer.getX());
        otherP.setY(otherPlayer.getY());
        otherP.setNHeart(otherPlayer.getHeart());
        System.out.println("Method: sendPlayerData() to\n" + "This player: " + g.toJson(thisP));
        out.println("This player");
        out.println(g.toJson(thisP));
        System.out.println("Method: sendPlayerData() to\n" + "Other player: " + g.toJson(otherP));
        out.println("Other player");
        out.println(g.toJson(otherP));
    }

    public static void sendBulletData(Bullet b) {
        out.println("Bullet");
        out.println(g.toJson(b));
    }


    public static void sendRestart() {
        out.println(g.toJson(new Command("restart")));
        System.out.println("send");
    }

    public static void restart(boolean recived) {
        System.out.println("restartMetodo1");
        thisPlayer.setX(0);
        thisPlayer.setY(360);
        thisPlayer.setHeart(3);
        otherPlayer.setX(1120);
        otherPlayer.setY(360);
        otherPlayer.setHeart(3);
        f.increaseWins = false;
        f.gameEnded = false;
        if (recived) {
            System.out.println("ready");
            out.println(g.toJson(new Command("ready")));
        }
        Client.recived = false;
        System.out.println("restartMetodo");
        f.repaint();

    }
}

/*

 */