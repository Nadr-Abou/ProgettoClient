package org.example;

import java.io.PrintWriter;

public class Movimento extends Thread{
    private Player thisPlayer;
    private Player otherPlayer;
    private CustomFrame f;
    private PrintWriter out;

    public Movimento(Player thisPlayer, Player otherPlayer, CustomFrame f, PrintWriter out) {
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.f = f;
        this.out = out;
    }
    @Override
    public void run() {
        double thisPosizioneY = thisPlayer.getY();
        double otherPosizioneY = otherPlayer.getY();
        while(true){
            if(thisPosizioneY != thisPlayer.getY()){
                thisPosizioneY = thisPlayer.getY();
                out.println(thisPosizioneY);
                f.repaint();
            }
            if(otherPosizioneY != otherPlayer.getY()){
                f.repaint();
            }
        }
    }
}
