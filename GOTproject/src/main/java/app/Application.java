package app;

import gameplay.GameMaster;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;


public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        /*
        //prégénération de map ?
        char[] display = ("12345\n67").toCharArray();
        System.out.println(display);
        display[5] = '0';
        System.out.println(display);
        */
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