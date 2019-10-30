# ConceptObjet-GOT

TO-DO List :

# Debut du jeu : afficher attributs statiques importants (nouveau : priorité haute | durée estimée : courte)

- afficher les attributs statiques du package character

# Fin du jeu : condition, affichage (nouveau : priorité haute | durée estimée : moyenne)

- gérer conditions de victoire : FAIT
  - reste que des ww
  - reste que des nordistes
  - reste que des sudistes
- afficher le gagnant
  - ww
  - famille restante avec le plus de représentants? avec les meilleurs niveaux? avec le plus de vie restante? les faire s'affronter "virtuellement" pour déterminer qui reste?
- afficher les stats de la partie (nouvelle classe)

# safeZone : interdire les combats (amélioration : priorité moyenne | durée estimée : très courte)

- entre humains uniquement : FAIT

# Affichage : niveaux (amélioration : priorité moyenne | durée estimée : courte)

- à implémenter, prendre en compte l'entrée utilisateur

# attack : Implémenter dodge (amélioration : priorité moyenne | durée estimée : courte)

- si réussite critique ou échec à l'attaque, pas d'esquive
- si réussite simple : lance un dé d'esquive
  - esquive critique : prend aucun dégât (alternative : regain de vie? inflige légers dégâts à l'attaquant?)
  - esquive réussie : prend la moitié des dégats (alternative : évite simplement l'attaque? perd de l'énergie?)
  - esquive échouée : prend les dégâts initialement prévus

# Données : statistiques (nouveau : priorité moyenne | durée estimée : longue)

- affichage en fin de partie
- nouvelle classe de Gameplay - statFaction
  - dégâts moyens par famille
  - nombre de rencontres par famille
  - nombre de combats par famille
  - nombre de meurtres par famille
  - nombre de ww tués par famille
  - nombre de tours survécus par famille
  - nombre de cases parcourues par famille
  - ...
- lancer des générations successives pour faire une moyenne sur plusieurs parties?

# Affichage : unicode (amélioration sous condition : priorité basse | durée estimée : courte)

- choisir les caractères d'affichage et tester le résultat
  - affichage unicode ou ascii dépendant d'un booléen de map?

# SafeZone : libération ? (amélioration : priorité basse | durée estimée : moyenne)

- si dernier représentant d'une faction meurt, la safezone de la faction est désactivée et revient à la faction du premier humain qui s'y rend

# Gameplay : équilibrage (amélioration : priorité basse | durée estimée : moyenne à longue)

### s'aider des statistiques

- vie, dégats
- chance
- niveau
- taille map et safezone
- ...
