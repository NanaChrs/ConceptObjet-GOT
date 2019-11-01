package app;

import gameplay.GameMaster;
import gameplay.UserInterface;

import static gameplay.UserInterface.displayConsole;
import java.io.IOException;
import java.util.Scanner;


public class Application {

    
    public static void main(String[] args) throws IOException, InterruptedException {
    	//(int mapSize, int safezoneSize, int maxTurn, int popByFaction, int firstWW, int wwFrequency)
        //GameMaster.getInstance().runSimulation(12, 3, 13, 4, 3, 4);
    	launcher();
    }
    
    public static void launcher() throws IOException, InterruptedException {
        GameMaster notreSuperSimulateur = GameMaster.getInstance();
        
        String menu = "Bienvenue dans notreSuperSimulateur. Veuillez choisir votre scénario :\n";//bienvenue dans petitsMeurtesEnFamilles
        menu += "<1> Les marcheurs blancs? Un conte pour enfants! (aucun marcheur blanc)\n";//pas de ww, 4 par faction, map de 15
        menu += "<2> Un nouveau peuple arrive (mode classique)\n";//une génération comme les autres? le film, en vrai?//ww au bout de 6 tours puis tous les 4, 5 par faction, map de 18
        //menu += "<3> Notre pire cauchemar...\n";//1 ww tous les 2 tours, 6 par faction, map de 21
        menu += "<3> Battle royale! (un représentant par faction)\n";//1 ww et aucun autre par la suite, 1 par faction, map de 12
        //menu += "<5> Scénario personnalisé\n";
        menu += "\n<0> Quitter\n";
        int optionsMenu = 3;
        
        /*String detail = "\nChoisissez le niveau de détails à afficher :\n";
        detail += "<1> ...\n";//0 : rien sauf stat, 1 à 4 : comme frequency
        int optionsDetail = 4;
        
        String turn = "\nChoisissez le nombre de tour maximum (entre 5 et 50) :";
        int minTurn = 5, maxTurn = 50;*/
        
        int userChoice;
        do {
            //int displayLevel, int mapSize, int safezoneSize, int maxTurn, int popByFaction, int firstWW, int wwFrequency
            switch(userChoice = UserInterface.userChoice(true,menu,optionsMenu)) {
                case 1:
                    notreSuperSimulateur.runSimulation(12, 4, 15, 5, 41, 0);
                    break;
                case 2:
                    notreSuperSimulateur.runSimulation(12, 3, 13, 4, 3, 4);
                    break;
                /*case 3:
                    notreSuperSimulateur.runSimulation(UserInterface.userChoice(false,detail,optionsDetail), 21, 5, UserInterface.userChoice(false,turn,minTurn,maxTurn), 6, 2, 2);
                    break;*/
                case 3:
                    notreSuperSimulateur.runSimulation(9, 2, 13, 1, 1, 41);
                    break;
                /*case 5:
                    displayConsole(false,"WORK IN PROGRESS");
                    //demander pour chaque paramètre
                    //notreSuperSimulateur.runSimulation(userChoice(detail, optionsDetail), 21, 5, userChoice(turn, minTurn, maxTurn), 6, 2, 2);
                    break;*/
            }
        } while(userChoice != 0);
        
        displayConsole(false,"Au revoir, et merci d'avoir joué à notreSuperSimulateur!");//meci d'avoir joué à petitsMeurtesEnFamilles
    }
}