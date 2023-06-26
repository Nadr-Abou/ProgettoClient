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

    public Client(Player thisPlayer, Player otherPlayer, CustomFrame f) {
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.f = f;
        clientMain();
    }

    public Client() {}

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
            try {
                if ((s = in.readLine()) != null) {
                    if (s.equals("exit")) {
                        break;
                    }
                    System.out.println("Dall'altro client: "+s);
                    P myPlayer = g.fromJson(s, P.class);
                    if (myPlayer.getX() == 0) {
                        otherP = myPlayer;
                        otherPlayer.setY(otherP.getY());
                        otherPlayer.setHeart(otherP.getNHeart());
                    } else {
                        thisP.setNHeart(myPlayer.getNHeart());
                        thisPlayer.setHeart(thisP.getNHeart());
                    }
                    f.repaint();
                    System.out.println("Questo giocatore: "+thisPlayer);
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
        System.out.println("This player: {x: "+thisPlayer.getX()+" y: "+thisPlayer.getY()+"}");
        System.out.println("Other player: {x: "+otherPlayer.getX()+" y: "+otherPlayer.getY()+"}");
    }

    public static void sendPlayerData() {
        thisP.setX(thisPlayer.getX());
        thisP.setY(thisPlayer.getY());
        thisP.setNHeart(thisPlayer.getHeart());
        otherP.setX(otherPlayer.getX());
        otherP.setY(otherPlayer.getY());
        otherP.setNHeart(otherPlayer.getHeart());
        System.out.println("Method: sendPlayerData() to\n"+"This player: "+g.toJson(thisP));
        out.println(g.toJson(thisP));
        System.out.println("Method: sendPlayerData() to\n"+"Other player: "+g.toJson(otherP));
        out.println(g.toJson(otherP));
    }
}