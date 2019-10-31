# ConceptObjet-GOT - TO-DO List :


## Début du jeu : afficher attributs statiques importants (nouveau : priorité haute | durée estimée : courte)
- afficher les attributs statiques du package character


## Affichage : niveaux de détails (amélioration : priorité moyenne | durée estimée : courte)
### Mathilde
- à implémenter


## Attack : Implémenter dodge (amélioration : priorité moyenne | durée estimée : courte)
### Mathilde?
- si réussite critique ou échec à l'attaque, pas d'esquive
- si réussite simple : lance un dé d'esquive
  - esquive critique : prend aucun dégât (alternative : regain de vie? inflige légers dégâts à l'attaquant?)
  - esquive réussie : prend la moitié des dégats (alternative : évite simplement l'attaque? perd de l'énergie?)
  - esquive échouée : prend les dégâts initialement prévus


## Données : statistiques (nouveau : priorité moyenne | durée estimée : longue)
### Clara
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


## Affichage : unicode (amélioration sous condition : priorité basse | durée estimée : courte)
- choisir les caractères d'affichage et tester le résultat
  - affichage unicode ou ascii dépendant d'un booléen de map?


## Gameplay : équilibrage (amélioration : priorité basse | durée estimée : moyenne à longue)
### s'aider des statistiques
- vie, dégats
- chance
- niveau
- taille map et safezone
- ...
