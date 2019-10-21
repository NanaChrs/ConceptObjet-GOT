package gameplay;

public class UserInterface {
    protected static final int M_CONSOLE_SIZE = 50;
    
    /** 
     * Cleans the console by passing lines
     * @return console  sends the reference to chain the calls
     */ 
    public static void cleanUI() {
        String swipe = "";
        for (int i = 0; i < M_CONSOLE_SIZE; i++) {//concatenates all the line breaks
            swipe += "\n";
        }
        System.out.print(swipe);//shows all of a sudden
    }
}
