package app;

import gameplay.GameMaster;
import java.io.IOException;


public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        //GameMaster notreSuperSimulateur = GameMaster.getInstance(9, 2);
        //notreSuperSimulateur.runSimulation(4,20,2,4,6);
        
        GameMaster.getInstance(21,4).runSimulation(1,40,6,4,6);//scénario alternatif
    }
}