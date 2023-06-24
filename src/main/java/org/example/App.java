package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class App {

    static CustomFrame f = null;

    public static void main(String[] args) {
        Player thisPlayer = new Player(3);
        Player otherPlayer = new Player(3);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(thisPlayer, otherPlayer);
            }
        });
        while (f == null) {
            System.out.println("aspettando");
        }
        clientMain(thisPlayer, otherPlayer, f);

    }


    private static void createAndShowGUI(Player thisPlayer, Player otherPlayer) {
        f = new CustomFrame(thisPlayer);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1220, 686);
        f.setResizable(false);
        f.setVisible(true);
    }

    static void clientMain(Player thisPlayer, Player otherPlayer, CustomFrame f) {

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
            /*SPACCHETTAMENTO JSON NELLE COORDINATE*/

        } catch (Exception e) {
            System.out.println("Connettere il server");
        }

        thisPlayer.setY(250);
        thisPlayer.setX(0);
        otherPlayer.setY(500);
        otherPlayer.setX(1220 - 100);

        thisPlayer.setF(f);
        otherPlayer.setF(f);

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
