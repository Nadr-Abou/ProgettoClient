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
    PrintWriter out;
    BufferedReader in;
    static P thisP = null;
    static P otherP = null;
    static Gson g;

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
        Socket echoSocket = null;

        try {
            echoSocket = new Socket(hostName, portNumber);
        } catch (IOException e) {
            System.out.println("cannot reach server " + e);
        }

        PrintWriter out = null;

        try {
            out = new PrintWriter(echoSocket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("YOU MUST CONNECT THE SERVER!!");
        }

        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("cannot allocate bufferedreader");
        }

        try {
            String paramIniziali = in.readLine();
            System.out.println(paramIniziali);
            g = new Gson();
            thisP = g.fromJson(paramIniziali, P.class);
            System.out.println(thisP);
            paramIniziali = in.readLine();
            otherP = g.fromJson(paramIniziali, P.class);
            System.out.println(otherP);
            /*SPACCHETTAMENTO JSON NELLE COORDINATE*/
            //String thisPlayerJson = g.fromJson();
        } catch (Exception e) {
            System.out.println("Si è verificato un errore per via del messaggio ricevuto oppure il server non è connesso");
        }

        //Coordinate dei giocatori prese dal server
        thisPlayer.setY(thisP.getY());
        thisPlayer.setX(thisP.getX());
        otherPlayer.setY(otherP.getY());
        otherPlayer.setX(otherP.getX());

        thisPlayer.setF(f);
        otherPlayer.setF(f);

        System.out.println("This player: {x: "+thisPlayer.getX()+" y: "+thisPlayer.getY()+"}");
        System.out.println("Other player: {x: "+otherPlayer.getX()+" y: "+otherPlayer.getY()+"}");

        f.repaint();

        if (thisPlayer.getX() < 500) {
            f.setLeftPlayer(thisPlayer);
            f.setRightPlayer(otherPlayer);
        } else {
            f.setLeftPlayer(otherPlayer);
            f.setLeftPlayer(thisPlayer);
        }

        Movimento movements = new Movimento(thisPlayer, otherPlayer, f, out);
        Thread thread = new Thread(movements);
        thread.start();

        while (true) {

        }
    }
}
