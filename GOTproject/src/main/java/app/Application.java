package app;

import gameplay.GameMaster;
import static gameplay.UserInterface.displayConsole;
import java.io.IOException;
import java.util.Scanner;


public class Application {
    private static int userChoice(boolean clean, String message, int nbChoices) {
        displayConsole(clean,message);
        
        Scanner keyboard = new Scanner(System.in);
        int choice = 0;

        do {
            try {
                System.out.printf("\tVotre choix : ");
                choice = keyboard.nextInt();
            } 
            catch (Exception e) {
                keyboard.next();//if incorrect : try again
            }
        } while (choice < 0 || choice > nbChoices);
        
        return choice;
    }
    
    private static int userChoice(boolean clean, String message, int choixMin, int choixMax) {
        displayConsole(clean,message);
        
        int choice;
        do choice = userChoice(false,"", choixMax); while (choice < choixMin);
        
        return choice;
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        GameMaster.getInstance().runSimulation(5, 18, 4, 50, 5, 6, 4);
    }
    
    public static void alternativeLauncher() throws IOException, InterruptedException {
        GameMaster notreSuperSimulateur = GameMaster.getInstance();
        
        String menu = "Bienvenue dans notreSuperSimulateur. Veuillez choisir votre scénario :\n";//bienvenue dans petitsMeurtesEnFamilles
        menu += "<1> Les marcheurs blancs? un conte pour enfants!\n";//pas de ww, 4 par faction, map de 15
        menu += "<2> Un nouveau peuple arrive\n";//une génération comme les autres? le film, en vrai?//ww au bout de 6 tours puis tous les 4, 5 par faction, map de 18
        menu += "<3> Notre pire cauchemar...\n";//1 ww tous les 2 tours, 6 par faction, map de 21
        menu += "<4> Battle royale!\n";//1 ww et aucun autre par la suite, 1 par faction, map de 12
        menu += "<5> Scénario personnalisé\n";
        menu += "\n<0> Quitter\n";
        int optionsMenu = 5;
        
        String detail = "\nChoisissez le niveau de détails à afficher :\n";
        detail += "<1> ...\n";//0 : rien sauf stat, 1 à 4 : comme frequency
        int optionsDetail = 4;
        
        String turn = "\nChoisissez le nombre de tour maximum (entre 5 et 50) :";
        int minTurn = 5, maxTurn = 50;
        
        int userChoice;
        do {
            //int displayLevel, int mapSize, int safezoneSize, int maxTurn, int popByFaction, int firstWW, int wwFrequency
            switch(userChoice = userChoice(true,menu,optionsMenu)) {
                case 1:
                    notreSuperSimulateur.runSimulation(userChoice(false,detail,optionsDetail), 15, 4, userChoice(false,turn,minTurn,maxTurn), 4, 41, 0);
                    break;
                case 2:
                    notreSuperSimulateur.runSimulation(userChoice(false,detail,optionsDetail), 18, 4, userChoice(false,turn,minTurn,maxTurn), 5, 6, 4);
                    break;
                case 3:
                    notreSuperSimulateur.runSimulation(userChoice(false,detail,optionsDetail), 21, 5, userChoice(false,turn,minTurn,maxTurn), 6, 2, 2);
                    break;
                case 4:
                    notreSuperSimulateur.runSimulation(userChoice(false,detail,optionsDetail), 9, 2, userChoice(false,turn,minTurn,maxTurn), 1, 1, 41);
                    break;
                case 5:
                    displayConsole(false,"WORK IN PROGRESS");
                    //demander pour chaque paramètre
                    //notreSuperSimulateur.runSimulation(userChoice(detail, optionsDetail), 21, 5, userChoice(turn, minTurn, maxTurn), 6, 2, 2);
                    break;
            }
        } while(userChoice != 0);
        
        displayConsole(false,"Au revoir, et merci d'avoir joué à notreSuperSimulateur!");//meci d'avoir joué à petitsMeurtesEnFamilles
    }
}