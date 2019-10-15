package map;

import java.util.SortedSet;

public class Westeros {
	public static final int HEIGHT = 50;
	public static final int WIDTH = 50;
	public Box[][] boxes;
	
	

	public void mapDisplay() {
		//displayBox
		for (int y = HEIGHT-1 ; y >= 0; y-- ) {
			String str = "";
			for(int x = 0; x < WIDTH; x++) {
				if(boxes[x][y].isEmpty()) {
					if((x==0 || x==WIDTH-1) && y !=HEIGHT-1) {
						str+="|";
					}
					else if(y == 0 || y==HEIGHT-1) {
						str+="_";
					}
					else {
						str+=" ";
					}
				}
				else {
					boxes[x][y].displayBox();
				}
				
			}
			System.out.println(str);
//				boxes[x][y].displayBox();
			}

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
