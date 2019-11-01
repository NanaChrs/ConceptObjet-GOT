
package gameplay;

public class Statistics {
    //population
    private static int LannisterAdded;
    private static int TargaryenAdded;
    private static int StarkAdded;
    private static int WildingsAdded;
    private static int WWAdded;
    
    //temps survécu
    private static int endOfLannister;
    private static int endOfTargaryen;
    private static int endOfStark;
    private static int endOfWildings;
    
    //morts au combat
    private static int LannisterDeadInBattle;
    private static int TargaryenDeadInBattle;
    private static int StarkDeadInBattle;
    private static int WildingsDeadInBattle;
    private static int WWDeadInBattle;
    
    //morts en déplacement (mauvais climat ou plus d'énergie)
    private static int LannisterDeadAlone;
    private static int TargaryenDeadAlone;
    private static int StarkDeadAlone;
    private static int WildingsDeadAlone;
    
    //meurtres
    private static int humanKilledByWW;
    
    private static int WWKilledByLannister;
    private static int WWKilledByTargaryen;
    private static int WWKilledByStark;
    private static int WWKilledByWildings;
    
    private static int northernerKilledByLannister;
    private static int northernerKilledByTargaryen;
    private static int southernerKilledByStark;
    private static int southernerKilledByWildings;
    
    //Constructeur
    public Statistics(int maxTurn) {
        endOfLannister = endOfTargaryen = endOfStark = endOfWildings = maxTurn;
    }
    
    //Getters (pour condition victoire)
    public static int LannisterAlive() {
        return LannisterAdded - LannisterDeadInBattle - LannisterDeadAlone;
    }
    
    public static int TargaryenAlive() {
        return TargaryenAdded - TargaryenDeadInBattle - TargaryenDeadAlone;
    }
    
    public static int StarkAlive() {
        return StarkAdded - StarkDeadInBattle - StarkDeadAlone;
    }
    
    public static int WildingsAlive() {
        return WildingsAdded - WildingsDeadInBattle - WildingsDeadAlone;
    }
    
    public static int WWAlive() {
        return WWAdded - WWDeadInBattle;
    }
    
    public static int LannisterKill() {
        return northernerKilledByLannister + WWKilledByLannister;
    }
    
    public static int TargaryenKill() {
        return northernerKilledByTargaryen + WWKilledByTargaryen;
    }
    
    public static int StarkKill() {
        return southernerKilledByStark + WWKilledByStark;
    }
    
    public static int WildingsKill() {
        return southernerKilledByWildings + WWKilledByWildings;
    }
    
    public static int WWKill() {
        return humanKilledByWW;
    }
    
    //Setters
    //population
    public static void LannisterAdded() {
        LannisterAdded++;
    }
    
    public static void TargaryenAdded() {
        TargaryenAdded++;
    }
    
    public static void StarkAdded() {
        StarkAdded++;
    }
    
    public static void WildingsAdded() {
        WildingsAdded++;
    }
    
    public static void WWAdded() {
        WWAdded++;
    }
    
    //temps survécu
    public static void endOfLannister(int turn) {
        endOfLannister = turn;
    }
    
    public static void endOfTargaryen(int turn) {
        endOfTargaryen = turn;
    }
    
    public static void endOfStark(int turn) {
        endOfStark = turn;
    }
    
    public static void endOfWildings(int turn) {
        endOfWildings = turn;
    }
    
    //morts au combat
    public static void LannisterDeadInBattle() {
        LannisterDeadInBattle++;
    }
    
    public static void TargaryenDeadInBattle() {
        TargaryenDeadInBattle++;
    }
    
    public static void StarkDeadInBattle() {
        StarkDeadInBattle++;
    }
    
    public static void WildingDeadInBattle() {
        WildingsDeadInBattle++;
    }
    
    public static void WWDeadInBattle() {
        WWDeadInBattle++;
    }
    
    //morts en déplacement (mauvais climat ou plus d'énergie)
    public static void LannisterDeadAlone() {
        LannisterDeadAlone++;
    }
    
    public static void TargaryenDeadAlone() {
        TargaryenDeadAlone++;
    }
    
    public static void StarkDeadAlone() {
        StarkDeadAlone++;
    }
    
    public static void WildingDeadAlone() {
        WildingsDeadAlone++;
    }
    
    //meurtres
    public static void humanKilledByWW() {
        humanKilledByWW++;
    }
    
    public static void WWKilledByLannister() {
        WWKilledByLannister++;
    }
    
    public static void WWKilledByTargaryen() {
        WWKilledByTargaryen++;
    }
    
    public static void WWKilledByStark() {
        WWKilledByStark++;
    }
    
    public static void WWKilledByWildings() {
        WWKilledByWildings++;
    }
    
    public static void northernerKilledByLannister() {
        northernerKilledByLannister++;
    }
    
    public static void northernerKilledByTargaryen() {
        northernerKilledByTargaryen++;
    }
    
    public static void southernerKilledByStark() {
        southernerKilledByStark++;
    }
    
    public static void southernerKilledByWildings() {
        southernerKilledByWildings++;
    }
    
    //Méthode
    public static void displayStats() throws InterruptedException {
        String stats = "";
        
        stats += "\n\nFAMILLE LANNISTER :\n";
        stats += LannisterAdded+" représentants\n";
        stats += endOfLannister+" tours traversés\n";
        stats += LannisterDeadInBattle+" morts au combat\n";
        stats += LannisterDeadAlone+" morts sur les routes\n";
        stats += northernerKilledByLannister+" Nordiens tués\n";
        stats += WWKilledByLannister+" Marcheurs tués\n";
        
        stats += "\n\nFAMILLE TARGARYEN :\n";
        stats += TargaryenAdded+" représentants\n";
        stats += endOfTargaryen+" tours traversés\n";
        stats += TargaryenDeadInBattle+" morts au combat\n";
        stats += TargaryenDeadAlone+" morts sur les routes\n";
        stats += northernerKilledByTargaryen+" Nordiens tués\n";
        stats += WWKilledByTargaryen+" Marcheurs tués\n";
        
        stats += "\n\nFAMILLE STARK :\n";
        stats += StarkAdded+" représentants\n";
        stats += endOfStark+" tours traversés\n";
        stats += StarkDeadInBattle+" morts au combat\n";
        stats += StarkDeadAlone+" morts sur les routes\n";
        stats += southernerKilledByStark+" Sudien tués\n";
        stats += WWKilledByStark+" Marcheurs tués\n";
        
        stats += "\n\nFAMILLE WILDINGS :\n";
        stats += WildingsAdded+" représentants\n";
        stats += endOfWildings+" tours traversés\n";
        stats += WildingsDeadInBattle+" morts au combat\n";
        stats += WildingsDeadAlone+" morts sur les routes\n";
        stats += southernerKilledByWildings+" Sudien tués\n";
        stats += WWKilledByWildings+" Marcheurs tués\n";
        
        stats += "\n\nMARCHEURS BLANCS :\n";
        stats += WWAdded+" apparus\n";
        stats += WWDeadInBattle+" morts\n";
        stats += humanKilledByWW+" Humains tués\n";
        
        UserInterface.displayConsole(false,stats,1);
    }
}