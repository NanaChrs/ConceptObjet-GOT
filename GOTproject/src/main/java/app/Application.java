package app;

import gameplay.GameMaster;
import gameplay.UserInterface;

import java.io.IOException;


public class Application {
	
    public static void main(String[] args) throws IOException, InterruptedException {
    	GameMaster.getInstance().runSimulation("test equilibrage", 50, 15, 4, 6, 4, 8);
    	/*
        String mode1 = "Un nouveau peuple arrive (mode classique)";
        String mode2 = "Les marcheurs blancs? Un conte pour enfants! (aucun marcheur blanc)";
        String mode3 = "Battle royale! (un représentant par faction)";
        
        String menu = "Bienvenue dans Sangsational Simulator. Veuillez choisir votre scénario :\n<1> "+mode1+"\n<2> "+mode2+"\n<3> "+mode3+"\n\n<0> Quitter\n";
        int optionsMenu = 3;
        
        int userChoice;
        while((userChoice = UserInterface.userChoice(true,menu,optionsMenu)) != 0) {
            //mode, maxTurn, mapSize, safezoneSize, popByFaction, firstWW, wwFrequency
            switch(userChoice) {
                case 1:
                	GameMaster.getInstance().runSimulation(mode1, 15, 12, 3, 4, 3, 4);
                    break;
                case 2:
                	GameMaster.getInstance().runSimulation(mode2, 14, 12, 4, 5, 20, 0);
                    break;
                case 3:
                	GameMaster.getInstance().runSimulation(mode3, 13, 9, 2, 1, 1, 20);
                    break;
            }
        }*/
        UserInterface.closeReader();
        
        UserInterface.displayConsole(false,"\n\nAu revoir, et merci d'avoir joué à Sangsational Simulator!");
    }
}