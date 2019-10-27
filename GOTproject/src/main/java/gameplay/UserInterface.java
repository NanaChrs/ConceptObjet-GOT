package gameplay;

import java.util.concurrent.TimeUnit;
import map.GameBoard;

public class UserInterface {
    protected static final int M_CONSOLE_SIZE = 30;
    protected static String swipe = "";
    private static final int LATENCY_TURN = 2;
    private static final int LATENCY_MESSAGE = 800;
    private static final int LATENCY_MOVE = 500;
    private static final int LATENCY_STEP = 120;
    
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
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.print(swipe);//shows all of a sudden
    }
    
    public static void displayConsole(String message, GameBoard westeros, int deepness) throws InterruptedException {
        cleanConsole();
        System.out.println(message);
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
            case 4:
                TimeUnit.MILLISECONDS.sleep(LATENCY_STEP);
                break;
        }
    }
}
