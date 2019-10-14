package map;

import java.util.SortedSet;

public class Westeros {
	public 	SortedSet<Box> boxes;
	public static final int MAP_LENGTH = 40;
	public static final int MAP_WIDTH = 50;
	private static Westeros uniqueInstance;
	
	private Westeros() {
	}
	
	public static Westeros getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Westeros();
		}
		return uniqueInstance;
	}
	
	public void mapDisplay() {
		//displayBox

		/*
		pour chaque ligne du plateau :
			si climat : 
				détermine ou récupère caractères séparateur horizontal et vertical (o pour chaud, | et - ou _ pour tempéré, * pour froid (@ pour marcheur blanc mais plus complexe car localisé))

			affiche séparation horizontale entre lignes (boucle) (o, * ou -) (taille tab * 5 caractères (1 pour séparation + 4 pour case))

			affiche dernier caractère de séparation horizontale + retour à la ligne + premier caractère de séparation verticale
			pour chaque case (colonne) de la ligne :
				affiche contenu de la case (méthode) + caractère séparateur vertical

		affiche séparation horizontale entre lignes (même boucle)
		affiche dernier caractère de séparation horizontale
		*/
	}
}
