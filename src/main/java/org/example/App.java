package org.example;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class App {

    static CustomFrame f = null;

    public static void main(String[] args) {
        //istanza dei due giocatori
        Player thisPlayer = new Player();
        Player otherPlayer = new Player();
        //lancio interfaccia
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(thisPlayer, otherPlayer);
            }
        });
        //aspetta la creazione dell'interfaccia
        while (f == null) {
            System.out.println("Waiting for another player...");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Interrupted exception");
            }
        }
        //Connessione
        new Client(thisPlayer, otherPlayer, f);
    }

    private static void createAndShowGUI(Player thisPlayer, Player otherPlayer) {
        f = new CustomFrame(thisPlayer);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1220, 720);
        f.setResizable(false);
        f.setVisible(true);
    }
}