
package character;

import java.io.IOException;
import java.util.ArrayList;

import gameplay.Statistics;
import static gameplay.UserInterface.displayConsole;
import map.Box;
import map.Direction;
import map.GameBoard;
import map.SafeZone;

public abstract class Character {
    //Attributs - Instance définie par :
	
    //  sa position sur la map et la possibilité de s'y mouvoir
    protected GameBoard westeros;//agrégation
    protected Box currentBox;//agrégation
    protected final static int MAX_RANGE = (int)(GameBoard.getSize() * 3/5);//portée maximum pour déplacement
    
    //  sa vie et les dégâts qu'il fait
    protected final static int DEFAULT_STAT_VALUE = 100;
    protected int maxLife;//100% de la vie qui augmente de niveau en niveau
    protected int life; //% de vie actuelle
    protected int maxPower;//Maximum de puissance en augmentant de niveau
    protected int power; //puissance qui augmente de niveau en niveau jusque MaxPower
    
    //  sa chance
    private final static int THRESHOLD_MAX = 100; //Nombre de faces du dé
    private final static int DEFAULT_THRESHOLD_VALUE = THRESHOLD_MAX/2;
    private final int criticalSuccessThreshold; //palier de succès critique
    private final int failureThreshold; //palier d'échec
    
    //Constructeur & destructeur - naissance et mort de l'instance
    public Character(int maxLife, int maxPower, int power, int criticalSuccessThreshold, int failureThreshold) {
        this.life = this.maxLife    = maxLife > 0   ? maxLife   : DEFAULT_STAT_VALUE;
        this.maxPower               = maxPower > 0  ? maxPower  : DEFAULT_STAT_VALUE;
        
        this.power = power < 0 ? 0 : (power > this.maxPower ? DEFAULT_STAT_VALUE/4 : power);
        
        this.criticalSuccessThreshold   = criticalSuccessThreshold < 0  ? DEFAULT_THRESHOLD_VALUE   : (criticalSuccessThreshold > THRESHOLD_MAX ? DEFAULT_THRESHOLD_VALUE : criticalSuccessThreshold);
        this.failureThreshold           = failureThreshold < 0          ? DEFAULT_THRESHOLD_VALUE   : (failureThreshold         > THRESHOLD_MAX ? DEFAULT_THRESHOLD_VALUE : failureThreshold);
    }
    
    public void death(DamageSource cause, Character character) throws InterruptedException {
        // Supprime le personnage mort de la map
    	westeros.getMap()[currentBox.getX()][currentBox.getY()].removeCharacter();
        displayConsole((this instanceof Human? ((Human)this).getFullName() : "Le marcheur blanc") + " meurt", westeros, 3);

        // Dans tous les cas, l'instance meurt pour une raison (combat ou épuisement des statistiques)
        if (this instanceof Lannister) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.LannisterDeadAlone();
            }
            else {   
                Statistics.LannisterDeadInBattle();
            }
        }
        else if (this instanceof Targaryen) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.TargaryenDeadAlone();
            }
            else {
                Statistics.TargaryenDeadInBattle();
            }
        }
        else if (this instanceof Stark) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.StarkDeadAlone();
            }
            else {   
                Statistics.StarkDeadInBattle();
            }
        }
        else if (this instanceof Wilding) {
            if (cause.equals(DamageSource.Nature)) {
                Statistics.WildingDeadAlone();
            }
            else {   
                Statistics.WildingDeadInBattle();
            }
        }
        else {
            //White Walker ne peut mourir que par combat donc cherche qui l'a tué
        	Statistics.WWDeadInBattle();
            
            if (character instanceof Lannister) {
                Statistics.WWKilledByLannister();
            }
            else if (character instanceof Targaryen) {
                Statistics.WWKilledByTargaryen();
            }
            else if (character instanceof Stark) {
                Statistics.WWKilledByStark();
            }
            else /*if (this instanceof Wilding)*/ {
                Statistics.WWKilledByWildings();
            }
        }
        
        if (this instanceof Human) {
        	// Si dernier humain d'une famille meurt, supprime la safezone
            this.westeros.getSafeZone(this.getClass().getSimpleName()).removeEmptyFaction();
            
            // Si mort au combat, cherche le responsable
            if (cause.equals(DamageSource.Battle)) {
                if (character instanceof Lannister) {
                    Statistics.northernerKilledByLannister();
                }
                else if (character instanceof Targaryen) {
                    Statistics.northernerKilledByTargaryen();
                }
                else if (character instanceof Stark) {
                    Statistics.southernerKilledByStark();
                }
                else if (character instanceof Wilding) {
                    Statistics.southernerKilledByWildings();
                }
                else {
                    Statistics.humanKilledByWW();
                }
            }
        }
    }
    
    //Affichage des caractéristiques statiques de la classe
    public static void displayStatics() {
        String display = "\n\nClasse Character";
        display += "\nPortée max : "+MAX_RANGE;
        display += "\nValeur de stat par défaut : "+DEFAULT_STAT_VALUE;
        display += "\nChance par défaut : "+DEFAULT_THRESHOLD_VALUE;
        display += "\nChance max : "+THRESHOLD_MAX;
        
        System.out.println(display);
    }
    
    //Getters & setters utiles

    //  position
    public void setMap(GameBoard map) {
        this.westeros = map;
    }

    public void setBox(Box position) {
        this.currentBox = position;
    }
    
    public Box getBox() {
        return this.currentBox;
    }
    
    //  attributs
    public void addLife(int life) {
        this.life += life;
        if (this.life > maxLife) {
        	this.life = maxLife;
        }
    }
    
    public void reduceLife(int life, DamageSource cause, Character character) throws InterruptedException {
        this.life -= life;
        if (this.life <= 0) {
        	this.death(cause,character);
        }
    }
    
    public boolean isAlive() {
        return this.life > 0;
    }

    public void addPower(int power) {
        this.power += power;
        if (this.power > maxPower) {
        	this.power = maxPower;
        }
    }
    
    //Méthodes private - actions spécifiques
    
    //Détermine la portée du personnage pour le déplacement
    private int currentRange() throws InterruptedException {
        //si humain à court d'energie, ne bouge plus
        if (this instanceof Human && ((Human)this).stamina == 0) {
            if (((Human)this).turnsWithoutStamina++ >= Human.MAX_TURNS_WITHOUT_STAMINA) {//not found : no rescue
                this.reduceLife(this.life,DamageSource.Nature,null);
            }
            return 0;
        }
        
        //lancer de dés pour déterminer la portée 
        switch(this.rollDice()) {
            case CRITICAL_SUCCESS:
                return MAX_RANGE;
            case SUCCESS: 
                return (int) (0.6*MAX_RANGE);
            default: 
                return (int) (0.3*MAX_RANGE);
        }	
    }
    
    //Vérifie que la case suivante dans la direction donnée est accessible et libre 
    private boolean isNextFree(Direction takenDirection) {
        int posX = this.currentBox.getX();
        int posY = this.currentBox.getY();
        int xMax = GameBoard.getSize();
        int yMax = GameBoard.getSize();
        
        switch(takenDirection) {//bas gauche: (0,0) ; haut droite : (max,max)
            case NorthWest :
                return (posX-1 >= 0 &&      posY+1 < yMax &&    westeros.getMap()[posX-1][posY+1].isEmpty());
            case North :
                return (                    posY+1 < yMax &&    westeros.getMap()[posX][posY+1].isEmpty());
            case NorthEast :
                return (posX+1 < xMax &&    posY+1 < yMax &&    westeros.getMap()[posX+1][posY+1].isEmpty());
            case East :
                return (posX+1 < xMax &&                        westeros.getMap()[posX+1][posY].isEmpty());
            case SouthEast :
                return (posX+1 < xMax &&    posY-1 >= 0 &&      westeros.getMap()[posX+1][posY-1].isEmpty());
            case South :
                return (                    posY-1 >= 0 &&      westeros.getMap()[posX][posY-1].isEmpty());
            case SouthWest :
                return (posX-1 >= 0 &&      posY-1 >= 0 &&      westeros.getMap()[posX-1][posY-1].isEmpty());
            case West :
                return (posX-1 >= 0 &&                          westeros.getMap()[posX-1][posY].isEmpty());
            default:
                return false;
        }
    }
    
    //Retourne la direction diagonale droite de la direction donnée
    private Direction diagoRight(Direction dir) {
        switch (dir) {
            case North:
                return Direction.NorthEast;
            case NorthEast:
                return Direction.East;
            case East:
                return Direction.SouthEast;
            case SouthEast:
                return Direction.South;
            case South:
                return Direction.SouthWest;
            case SouthWest:
                return Direction.West;
            case West:
                return Direction.NorthWest;
            case NorthWest:
                return Direction.North;
            default: 
                return null;
        }
    }
    
    //Retourne la direction diagonale gauche de la direction donnée
    private Direction diagoLeft(Direction dir) {
        switch (dir) {
            case North:
                return Direction.NorthWest;
            case NorthWest:
                return Direction.West;
            case West:
                return Direction.SouthWest;
            case SouthWest:
                return Direction.South;
            case South:
                return Direction.SouthEast;
            case SouthEast:
                return Direction.East;
            case East:
                return Direction.NorthEast;
            case NorthEast:
                return Direction.North;
            default: 
                return null;
        }
    }
    
    // Retourne la liste des directions que peut emprunter un personnage (liste de directions où la case suivante est libre)
    private ArrayList<Direction> potentialDirections() {
        ArrayList<Direction> list = new ArrayList<>();
        
        //si humain bientot à court d'énergie, recherche safezone
        if (this instanceof Human && !currentBox.isSafe() && ((Human)this).stamina <= Human.LOW_STAMINA) {
            //les 3 directions qui rapprochent de la safezone en verifiant qu'elles ne sont pas bouchées
            //si bouchées, ne se déplace pas (économise ses forces)
            
            //corner : duo de directions cardinales
            Direction corner = ((Human)this).safeZoneDirection();
            if (isNextFree(corner)) {
                list.add(corner);
            }
            
            int posX = this.currentBox.getX();
            int posY = this.currentBox.getY();
            
            //une coordonnée au niveau de safezone : déplacement en "+"
            if ((corner.equals(Direction.NorthWest) && posX < SafeZone.getSize()) || 
                (corner.equals(Direction.SouthWest) && posY < SafeZone.getSize()) || 
                (corner.equals(Direction.SouthEast) && posX > GameBoard.getSize() - SafeZone.getSize()) || 
                (corner.equals(Direction.NorthEast) && posY > GameBoard.getSize() - SafeZone.getSize())) {
                Direction diagoRight = diagoRight(corner);
                if (isNextFree(diagoRight)) {
                    list.add(diagoRight);
                }
                diagoRight = diagoRight(diagoRight);
                if (isNextFree(diagoRight)) {
                    list.add(diagoRight);
                }
            }
            //l'autre coordonnée au niveau de safezone : déplacement en "+"
            else if ((corner.equals(Direction.SouthWest) && posX < SafeZone.getSize()) || 
                    (corner.equals(Direction.SouthEast) && posY < SafeZone.getSize()) || 
                    (corner.equals(Direction.NorthEast) && posX > GameBoard.getSize() - SafeZone.getSize()) || 
                    (corner.equals(Direction.NorthWest) && posY > GameBoard.getSize() - SafeZone.getSize())) {
                Direction diagoLeft = diagoLeft(corner);
                if (isNextFree(diagoLeft)) {
                    list.add(diagoLeft);
                }
                diagoLeft = diagoLeft(diagoLeft);
                if (isNextFree(diagoLeft)) {
                    list.add(diagoLeft);
                }
            }
            //aucune coordonnée au niveau de safezone : déplacement en "x"
            else {
                Direction diagoRight = diagoRight(corner);
                if (isNextFree(diagoRight)) {
                    list.add(diagoRight);
                }
                Direction diagoLeft = diagoLeft(corner);
                if (isNextFree(diagoLeft)) {
                    list.add(diagoLeft);
                }
            }
        }
        
        //si personnage peut se déplacer librement 
        else {
            //ajoute directions dégagées à une case de distance
            for (Direction dir : Direction.values()) {
                if(isNextFree(dir)) {
                    list.add(dir);
                }
            }
        }
        
        return list;
    }

    //Faire un pas dans une direction donnée
    private Box makeStep(Direction direction) {
        //complémentaire de isNextFree
        int posX = this.currentBox.getX();
        int posY = this.currentBox.getY();
        
        switch(direction) {
            case NorthWest :
                return westeros.getMap()[posX-1][posY+1];
            case North :
                return westeros.getMap()[posX  ][posY+1];
            case NorthEast :
                return westeros.getMap()[posX+1][posY+1];
            case East :
                return westeros.getMap()[posX+1][posY  ];
            case SouthEast :
                return westeros.getMap()[posX+1][posY-1];
            case South :
                return westeros.getMap()[posX  ][posY-1];
            case SouthWest :
                return westeros.getMap()[posX-1][posY-1];
            case West :
                return westeros.getMap()[posX-1][posY  ];
            default:
                return null;
        }
    }
    
    //Message affiché sur console lors d'un déplacement d'un personnage
    private void moveMessage() throws InterruptedException {
        String message1, message2;
        if (this instanceof Human) {
            message1 = ((Human)this).getFullName();
            message2 = "; energie : " + ((Human)this).stamina + ")";
        }
        else {
            message1 = "Un marcheur blanc";
            message2 = ")";
        }
        displayConsole(message1 + " se déplace (vie : " + this.life + message2, westeros, 4);
    }
    
    //Scan des environs : retourne vrai quand a interagi avec un autre personnage 
    private boolean foundSomeoneInSurroundings(int range) throws IOException, InterruptedException {
        //scan des environs dans carte et interaction avec persos des cases juxtaposées
        boolean foundSomeone = false;
        
        //diminutifs
        int limXInf = currentBox.getX()-1,
            limXSup = currentBox.getX()+1,
            xMax = GameBoard.getSize(),
            limYInf = currentBox.getY()-1,
            limYSup = currentBox.getY()+1,
            yMax = GameBoard.getSize();
        
        //test des limites de map dans boucle for (moins de test au total)
        for (int x = limXInf+(limXInf<0 ? 1 : 0); x <= limXSup-(limXSup>=xMax ? 1 : 0); ++x) {
            for (int y = limYInf+(limYInf<0 ? 1 : 0); y <= limYSup-(limYSup>=yMax? 1 : 0); ++y) {
                //si être vivant présent autour du perso
                Character somebody = westeros.getMap()[x][y].getCharacter();
                if (somebody != null && somebody.isAlive() && !(x == currentBox.getX() && y == currentBox.getY())) {
                    foundSomeone = true;
                    //va a sa rencontre
                    if (somebody instanceof Human) {
                        meet((Human) somebody,range);
                    }
                    else {
                        meet((WhiteWalker) somebody,range);
                    }
                    
                    //si instance meurt à la fin de la rencontre, fin prématurée du scan et a bien rencontré qqn
                    if (!this.isAlive()) return true;
                }
            }
        }
        
        return foundSomeone;
    }
    
    //Méthodes protected - actions définies ou réutilisables en interne dans d'autres contextes
    /** 
    * Rolls a dice
    * @return diceResult   result of the throw
    */ 
    protected  DiceResult rollDice() {
       int diceValue = (int) (Math.random() * THRESHOLD_MAX) + 1;//plus grand échec : 1 | plus grande réussite : 100
       
       //more to less probable
       if (diceValue < this.criticalSuccessThreshold && diceValue > this.failureThreshold) {
           return DiceResult.SUCCESS;
       }
       else if (diceValue < this.failureThreshold) {
           return DiceResult.FAILURE;
       }
       else {
           return DiceResult.CRITICAL_SUCCESS;
       }
    }

    protected abstract void movmentConsequences() throws InterruptedException;

    protected abstract void meet(Human character, int remainingBoxes) throws IOException, InterruptedException;
    
    protected abstract void meet(WhiteWalker character, int remainingBoxes) throws IOException, InterruptedException;

    protected abstract void attack(Character character) throws IOException, InterruptedException;
    
    //Méthodes public - actions que peut réaliser l'instance
    public void move() throws IOException, InterruptedException {
        //etape 1 : connaitre la portée
        int range = currentRange();
        
        if (range > 0) {
            //etape 2 : recuperer les directions de deplacement envisageables
            ArrayList<Direction> validDir = potentialDirections();

            if (!validDir.isEmpty()) {
                //etape 3 : se deplacer le long d'une direction choisie au hasard
                Direction takenDir = validDir.get((int)(Math.random() * validDir.size()));

                //tant que la case suivante est vide et à portée, le perso se déplace + action de se déplacer dans movmentConsequences (perte de stamina, gain de pv, xp...)
                moveMessage();//etat initial
                do {//premiere case forcément vide
                    movmentConsequences();
                    if (this instanceof Human && (!this.isAlive() || ((Human)this).stamina == 0)) return;//humains qui perdent stamina par déplacement et vie sur terrain inhospitaliers

                    westeros.getMap()[currentBox.getX()][currentBox.getY()].removeCharacter();
                    currentBox = makeStep(takenDir);
                    westeros.getMap()[currentBox.getX()][currentBox.getY()].setCharacter(this);

                    moveMessage();//etat final
                } while(!foundSomeoneInSurroundings(range) && --range > 0 && isNextFree(takenDir));
                return;
            }
            else {
            	foundSomeoneInSurroundings(range);
            }
        }
        else if (this.isAlive()) {//pas (mort par manque de stamina et de secours)
            foundSomeoneInSurroundings(0);
        }
    }
}
