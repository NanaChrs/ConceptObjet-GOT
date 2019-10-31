
package gameplay;

public class Statistics {
    //      PARTIE 1 : FAIT
    //temps survécu
    private static int endOfLannister;
    private static int endOfTargaryen;
    private static int endOfStark;
    private static int endOfWildings;
    
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
    
    //morts en déplacement (mauvais climat ou plus d'énergie)
    private static int LannisterDeadOnMove;
    private static int TargaryenDeadOnMove;
    private static int StarkDeadOnMove;
    private static int WildingsDeadOnMove;
    
    
    //      PARTIE 2 : A FAIRE
    //moyenne des dégats et niveaux
    private static int averageDamagesFromWW;
    private static int averageDamagesFromLannister;
    private static int averageDamagesFromTargaryen;
    private static int averageDamagesFromStark;
    private static int averageDamagesFromWildings;
    
    private static int averageLevelOfLannister;
    private static int averageLevelOfTargaryen;
    private static int averageLevelOfStark;
    private static int averageLevelOfWildings;
    
    public Statistics(int maxTurn) {
        endOfLannister = endOfTargaryen = endOfStark = endOfWildings = maxTurn;
    }
    
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
    
    public static void LannisterDeadOnMove() {
        LannisterDeadOnMove++;
    }
    
    public static void TargaryenDeadOnMove() {
        TargaryenDeadOnMove++;
    }
    
    public static void StarkDeadOnMove() {
        StarkDeadOnMove++;
    }
    
    public static void WildingDeadOnMove() {
        WildingsDeadOnMove++;
    }
    
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
    
    public static void displayStats() throws InterruptedException {
        String stats = "";
        
        stats += "\n\nFAMILLE LANNISTER :\n";
        stats += "Temps survécu : "+endOfLannister+"\n";
        stats += "Morts en déplacement : "+LannisterDeadOnMove+"\n";
        stats += "Nordiens tués : "+northernerKilledByLannister+"\n";
        stats += "Marcheurs tués : "+WWKilledByLannister+"\n";
        
        stats += "\n\nFAMILLE TARGARYEN :\n";
        stats += "Temps survécu : "+endOfTargaryen+"\n";
        stats += "Morts en déplacement : "+TargaryenDeadOnMove+"\n";
        stats += "Nordiens tués : "+northernerKilledByTargaryen+"\n";
        stats += "WW tués : "+WWKilledByTargaryen+"\n";
        
        stats += "\n\nFAMILLE STARK :\n";
        stats += "Temps survécu : "+endOfStark+"\n";
        stats += "Morts en déplacement : "+StarkDeadOnMove+"\n";
        stats += "Sudien tués : "+southernerKilledByStark+"\n";
        stats += "WW tués : "+WWKilledByStark+"\n";
        
        stats += "\n\nFAMILLE WILDINGS :\n";
        stats += "Temps survécu : "+endOfWildings+"\n";
        stats += "Morts en déplacement : "+WildingsDeadOnMove+"\n";
        stats += "Sudien tués : "+southernerKilledByWildings+"\n";
        stats += "WW tués : "+WWKilledByWildings+"\n";
        
        stats += "\n\nMARCHEURS BLANCS :\n";
        stats += "Humains tués : "+humanKilledByWW+"\n";
        
        UserInterface.displayConsole(stats,1);
    }
}