package org.example;

import java.io.PrintWriter;

public class Movimento extends Thread{
    private Player thisPlayer;
    private Player otherPlayer;
    private CustomFrame f;

    public Movimento(Player thisPlayer, Player otherPlayer, CustomFrame f) {
        this.thisPlayer = thisPlayer;
        this.otherPlayer = otherPlayer;
        this.f = f;
    }
    @Override
    public void run() {
        /*double thisPosizioneY = thisPlayer.getY();
        double otherPosizioneY = otherPlayer.getY();
        while(true){
            if(thisPosizioneY != thisPlayer.getY()){
                thisPosizioneY = thisPlayer.getY();
                f.repaint();
            }
            if(otherPosizioneY != otherPlayer.getY()){
                f.repaint();
            }
        }*/
    }
}
