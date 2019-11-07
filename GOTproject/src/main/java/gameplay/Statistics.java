
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
    //nouvelle partie
    public static void initialize(int maxTurn) {
    	LannisterAdded = TargaryenAdded = StarkAdded = WildingsAdded = WWAdded = 0;
        endOfLannister = endOfTargaryen = endOfStark = endOfWildings = maxTurn;
    	LannisterDeadInBattle = TargaryenDeadInBattle = StarkDeadInBattle = WildingsDeadInBattle = WWDeadInBattle = 0;
    	LannisterDeadAlone = TargaryenDeadAlone = StarkDeadAlone = WildingsDeadAlone = 0;
    	humanKilledByWW = 0;
    	northernerKilledByLannister = northernerKilledByTargaryen = southernerKilledByStark = southernerKilledByWildings = 0;
    }
    
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
        stats += LannisterAdded+" représentant(s)\n";
        if (LannisterAlive() == 0) {
        	stats += endOfLannister+"/"+GameMaster.getTurn()+" tour(s) traversé(s)\n";
        }
        else {
        	stats += "Famille survivante\n";
        }
        stats += LannisterDeadInBattle+" mort(s) au combat\n";
        stats += LannisterDeadAlone+" mort(s) sur les routes\n";
        stats += northernerKilledByLannister+" personne(s) du Nord tuée(s)\n";
        stats += WWKilledByLannister+" Marcheur(s) tué(s)\n";
        
        stats += "\n\nFAMILLE TARGARYEN :\n";
        stats += TargaryenAdded+" représentant(s)\n";
        if (TargaryenAlive() == 0) {
        	stats += endOfTargaryen+"/"+GameMaster.getTurn()+" tour(s) traversé(s)\n";
        }
        else {
        	stats += "Famille survivante\n";
        }
        stats += TargaryenDeadInBattle+" mort(s) au combat\n";
        stats += TargaryenDeadAlone+" mort(s) sur les routes\n";
        stats += northernerKilledByTargaryen+" personne(s) du Nord tuée(s)\n";
        stats += WWKilledByTargaryen+" Marcheur(s) tué(s)\n";
        
        stats += "\n\nFAMILLE STARK :\n";
        stats += StarkAdded+" représentant(s)\n";
        if (StarkAlive() == 0) {
        	stats += endOfStark+"/"+GameMaster.getTurn()+" tour(s) traversé(s)\n";
        }
        else {
        	stats += "Famille survivante\n";
        }
        stats += StarkDeadInBattle+" mort(s) au combat\n";
        stats += StarkDeadAlone+" mort(s) sur les routes\n";
        stats += southernerKilledByStark+" personne(s) du Sud tuée(s)\n";
        stats += WWKilledByStark+" Marcheur(s) tué(s)\n";
        
        stats += "\n\nFAMILLE WILDINGS :\n";
        stats += WildingsAdded+" représentant(s)\n";
        if (WildingsAlive() == 0) {
        	stats += endOfWildings+"/"+GameMaster.getTurn()+" tour(s) traversé(s)\n";
        }
        else {
        	stats += "Famille survivante\n";
        }
        stats += WildingsDeadInBattle+" mort(s) au combat\n";
        stats += WildingsDeadAlone+" mort(s) sur les routes\n";
        stats += southernerKilledByWildings+" personne(s) du Sud tuée(s)\n";
        stats += WWKilledByWildings+" Marcheur(s) tué(s)\n";
        
        stats += "\n\nMARCHEURS BLANCS :\n";
        stats += WWAdded+" apparu(s)\n";
        stats += WWDeadInBattle+" mort(s)\n";
        stats += humanKilledByWW+" Humain(s) tué(s)\n";
        
        UserInterface.displayConsole(false,stats,1);
    }
}