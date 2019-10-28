package app;

import gameplay.GameMaster;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        /*char[] display = ("12345\n67").toCharArray();
        System.out.println(display);
        
        display[5] = '0';
        System.out.println(display);*/
        
        GameMaster nomDuJeu = GameMaster.getInstance();
        
        nomDuJeu.initialize();
        nomDuJeu.run();
        nomDuJeu.displayEnd();
    }
}