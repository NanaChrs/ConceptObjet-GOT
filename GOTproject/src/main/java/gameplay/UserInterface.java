package gameplay;

import static gameplay.GameMaster.getTurn;
import java.util.concurrent.TimeUnit;
import map.GameBoard;

public class UserInterface {
    protected static final int M_CONSOLE_SIZE = 20;
    protected static String swipe;
    private static final int LATENCY_MESSAGE_IMPORTANT = 1000;
    private static final int LATENCY_MESSAGE_LONG = 1200;
    private static final int LATENCY_MESSAGE_SHORT = 800;
    private static final int LATENCY_MESSAGE_REDUNDANT = 250;
    
    public static void displayConsole(String message, GameBoard westeros, int deepness) throws InterruptedException {
        if (swipe == null) {//s'initialise au premier appel ~ lancement
            swipe = "";
            for (int i = 0; i < M_CONSOLE_SIZE; ++i) {//concatenates all the line breaks
                swipe += "\n";
            }
        }
        System.out.print(swipe + "Tour n°" + getTurn() + " \n" + message + "\n" + westeros.displayMap());//synchronise les affichages permet d'éliminer les artefacts
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
}
