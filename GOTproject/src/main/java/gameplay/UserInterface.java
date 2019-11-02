package gameplay;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import map.GameBoard;

public class UserInterface {
    protected static final int M_CONSOLE_SIZE = 20;
    protected static String swipe;
    private static final int LATENCY_MESSAGE_IMPORTANT = 1000;
    private static final int LATENCY_MESSAGE_LONG = 1200;
    private static final int LATENCY_MESSAGE_SHORT = 800;
    private static final int LATENCY_MESSAGE_REDUNDANT = 250;
    private static Scanner keyboard = new Scanner(System.in);
    
    //destructeur
    public static void closeReader() {
		keyboard.close();
    }

    //arret sur message
    public static void blockingMessage(String message, String action) throws IOException {
        UserInterface.displayConsole(false,"\n" + message + " - Appuyez sur une touche pour " + action);

        System.in.read();
        keyboard.nextLine();//récupère retour chariot (touche entrée)
    }

    //récupérateur de plus en plus complexe
    public static int userChoice(boolean clean, String message, int nbChoices) {
        displayConsole(clean,message);
        int choice = -1;

        do {
            try {
                System.out.print("\tVotre choix : ");
                choice = keyboard.nextInt();
            } 
            catch (Exception e) {
                keyboard.next();//if incorrect : try again
            }
        } while (choice < 0 || choice > nbChoices);
        keyboard.nextLine();//récupère retour chariot (touche entrée)
        
        return choice;
    }
    
    public static int userChoice(boolean clean, String message, int choixMin, int choixMax) {
        displayConsole(clean,message);
        
        int choice;
        do choice = userChoice(false,"", choixMax); while (choice < choixMin);
        
        return choice;
    }
    
    //afficheur de plus en plus complexe
    public static void displayConsole(boolean clean, String message) {
        if (swipe == null) {//s'initialise au premier appel ~ lancement
            swipe = "";
            for (int i = 0; i < M_CONSOLE_SIZE; ++i) {//concatenates all the line breaks
                swipe += "\n";
            }
        }
        System.out.println((clean? swipe : "") + message);//synchronise les affichages permet d'éliminer les artefacts
    }
    
    public static void displayConsole(boolean clean, String message, int deepness) throws InterruptedException {
        displayConsole(clean, message);
        switch(deepness) {
            case 1:
                TimeUnit.MILLISECONDS.sleep(LATENCY_MESSAGE_IMPORTANT);
                break;
            case 2:
                TimeUnit.MILLISECONDS.sleep(LATENCY_MESSAGE_LONG);
                break;
            case 3:
                TimeUnit.MILLISECONDS.sleep(LATENCY_MESSAGE_SHORT);
                break;
            case 4:
                TimeUnit.MILLISECONDS.sleep(LATENCY_MESSAGE_REDUNDANT);
                break;
        }
    }
    
    public static void displayConsole(String message, GameBoard westeros, int deepness) throws InterruptedException {
        displayConsole(true, "Tour n°" + GameMaster.getTurn() + " \n" + message + "\n" + westeros.displayMap(), deepness);
    }
}
