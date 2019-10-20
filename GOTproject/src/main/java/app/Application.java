package app;

import java.io.IOException;
import java.util.ArrayList;

import character.Character;
import character.Human;
import character.Lannister;
import character.Stark;
import character.Targaryen;
import character.Wildings;
import gameplay.FileManager;
import map.Westeros;

public class Application {

    public static void main(String[] args) throws IOException {
        FileManager.writeToLogFile("Un combat a commencé");
        FileManager.writeToLogFile("Daenerys est morte");


        Character cersei = new Lannister("Cersei");
        Human jaime = new Lannister("Jaime");
        Human arya = new Stark("Arya");
        Human sansa = new Stark("Sansa");
        Human jon = new Targaryen("Jon");
        Human dany = new Targaryen("Daenerys");
        Human gilly = new Wildings("Gilly");
        Human tor = new Wildings("Tormund");

        ArrayList<Character> humans = new ArrayList();
        humans.add(cersei);
        humans.add(jaime);
        humans.add(arya);
        humans.add(sansa);
        humans.add(jon);
        humans.add(dany);
        humans.add(gilly);
        humans.add(tor);

        Westeros westeros = Westeros.getInstance(humans);

        westeros.mapDisplay();

        //run
    }


    /*
    run {
            faire :
                    génère les 4 familles

                    affiche map

                    demande a user nombre de tour max

                    faire :
                            affiche compteur de tour

                            met factions (y compris marcheurs blancs) dans tableau
                            tant que tableau non vide :
                                    fait jouer familles tirée aléatoirement du tableau
                                    retire faction du tableau

                            si nb tour % x == 0 :
                                    ajoute marcheur blanc à faction correspondante

                            affiche map

                            vérifie conditions de fin (famille/faction gagnante, paix ou toutes les factions mortes)
                    tant que !fin et que nb tour++ < tour max

                    demander à user nouvelle partie ou quitter
            tant que !quitter
    }*/
}
