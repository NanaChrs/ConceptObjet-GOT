package app;

import gameplay.GameMaster;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        GameMaster nomDuJeu = GameMaster.getInstance();
        
        nomDuJeu.initialize();
        nomDuJeu.run();
        nomDuJeu.displayEnd();
    }
}