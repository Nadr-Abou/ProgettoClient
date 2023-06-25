package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Player thisPlayer;
    Player otherPlayer;
    CustomFrame f;
    PrintWriter out;
    BufferedReader in;

    public Client(Player thisPlayer, Player otherPlayer, CustomFrame f) {
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.f = f;
        clientMain();
    }

    public Client() {}

    public void clientMain() {

        String hostName = "127.0.0.1";
        int portNumber = 1234;
        Socket clientSocket = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
        } catch (Exception e) {
            System.out.println("cannot reach server " + e);
        }



        try {
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
            String paramIniziali = in.readLine();
            /*SPACCHETTAMENTO JSON NELLE COORDINATE*/

        } catch (Exception e) {
            System.out.println("Connettere il server");
        }

        otherPlayer.setY(250);
        otherPlayer.setX(0);
        thisPlayer.setY(500);
        thisPlayer.setX(1220 - 100);

        thisPlayer.setF(f);
        otherPlayer.setF(f);

        f.repaint();


        if (thisPlayer.getX() < 500) {
            f.setLeftPlayer(thisPlayer);
            f.setRightPlayer(otherPlayer);
        } else {
            f.setLeftPlayer(otherPlayer);
            f.setRightPlayer(thisPlayer);
        }



        while (true) {

        }


    }
}
