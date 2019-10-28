package gameplay;

import static gameplay.GameMaster.getTurn;
import java.util.concurrent.TimeUnit;
import map.GameBoard;

public class UserInterface {
    protected static final int M_CONSOLE_SIZE = 30;
    protected static String swipe = "";
    private static final int LATENCY_TURN = 1;
    private static final int LATENCY_MESSAGE = 800;
    private static final int LATENCY_MOVE = 300;
    
    public static void generateSwipe() {
        for (int i = 0; i < M_CONSOLE_SIZE; ++i) {//concatenates all the line breaks
            swipe += "\n";
        }
    }
    
    /** 
     * Cleans the console by passing lines
     * @return console  sends the reference to chain the calls
     */ 
    public static void cleanConsole() throws InterruptedException {
        System.out.print(swipe);//shows all of a sudden
    }
    
    public static void displayConsole(String message, GameBoard westeros, int deepness) throws InterruptedException {
        cleanConsole();
        System.out.println("Tour n°" + getTurn() + "\n" + message);
        westeros.displayMap();
        switch(deepness) {
            case 1:
                TimeUnit.SECONDS.sleep(LATENCY_TURN);
                break;
            case 2:
                TimeUnit.MILLISECONDS.sleep(LATENCY_MESSAGE);
                break;
            case 3:
                TimeUnit.MILLISECONDS.sleep(LATENCY_MOVE);
                break;
        }
    }
}
