package app;

import gameplay.GameMaster;
import java.io.IOException;


public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        /*
        //unicode?
        System.out.println("💀");
        System.out.println("\u2654");
        
        System.out.println("✰☆★👌👍🙏☞☛👉❸➂❤♡💛🎔💕💋💍📷✉🖂")
        */
        
        GameMaster nomDuJeu = GameMaster.getInstance();
        
        nomDuJeu.initialize();
        nomDuJeu.run();
        nomDuJeu.displayEnd();
    }
}