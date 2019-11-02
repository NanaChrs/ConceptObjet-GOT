package app;

import gameplay.GameMaster;
import gameplay.UserInterface;

import java.io.IOException;


public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
    	launcher();
    }
    
    public static void launcher() throws IOException, InterruptedException {
        String menu = "Bienvenue dans notreSuperSimulateur. Veuillez choisir votre scénario :\n";//bienvenue dans petitsMeurtesEnFamilles
        menu += "<1> Un nouveau peuple arrive (mode classique)\n";//une génération comme les autres? le film, en vrai?//ww au bout de 6 tours puis tous les 4, 5 par faction, map de 18
        menu += "<2> Les marcheurs blancs? Un conte pour enfants! (aucun marcheur blanc)\n";//pas de ww, 4 par faction, map de 15
        menu += "<3> Battle royale! (un représentant par faction)\n";//1 ww et aucun autre par la suite, 1 par faction, map de 12
        //menu += "<4> Notre pire cauchemar...\n";//1 ww tous les 2 tours, 6 par faction, map de 21
        //menu += "<5> Scénario personnalisé\n";
        menu += "\n<0> Quitter\n";
        int optionsMenu = 3;
        
        int userChoice;
        while((userChoice = UserInterface.userChoice(true,menu,optionsMenu)) != 0) {
            //mapSize, safezoneSize, maxTurn, popByFaction, firstWW, wwFrequency
            switch(userChoice) {
                case 1:
                	GameMaster.getInstance().runSimulation(12, 3, 15, 4, 3, 4);
                    break;
                case 2:
                	GameMaster.getInstance().runSimulation(12, 4, 14, 5, 41, 0);
                    break;
                case 3:
                	GameMaster.getInstance().runSimulation(9, 2, 13, 1, 1, 41);
                    break;
                /*case 4:
                    GameMaster.getInstance().runSimulation(21, 5, 30, 6, 2, 2);
                    break;
                case 5:
                    //demander pour chaque paramètre
        
			        String turn = "\nChoisissez le nombre de tour maximum (entre 5 et 50) :";
			        int minTurn = 5, maxTurn = 50;
                    //GameMaster.getInstance().runSimulation();
                    break;*/
            }
        }
        UserInterface.closeReader();
        
        UserInterface.displayConsole(false,"\n\nAu revoir, et merci d'avoir joué à notreSuperSimulateur!");//meci d'avoir joué à petitsMeurtesEnFamilles
    }
}