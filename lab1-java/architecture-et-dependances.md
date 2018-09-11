# Quelques réflexions sur l'architecture de l'application et les dépendances

## Qui fait quoi ? Principe de responsabilité unique

Les remarques que nous pouvons faire dépendent bien sûr de la manière
dont vous avez codé, mais parmi les problèmes classiques :

* La classe `Poney` gère beaucoup de choses à la fois, et a beaucoup
  de dépendances de natures différentes :

  * Elle gère l'état du poney, c'est à dire ses coordonnées, la
    vitesse et le fait qu'il soit en mode boost ou non.

  * Elle gère une partie de la logique du jeu (le fait qu'un Poney
    avance à une certaine vitesse, l'action à lancer quand le Poney
    arrive en fin de tour).

  * Elle s'occupe de l'affichage du Poney (choix de l'image en mode
    normal et boost), avec les dépendances sur la bibliothèque
    graphique qui en résultent.

  * Selon comment vous avez codé la gestion des évènements claviers,
    la classe `Poney` peut aussi gérer l'interaction avec
    l'utilisateur.

* De la même manière, la classe `Field` mélange la gestion de
  l'affichage, la gestion de l'ensemble de classes `Poney` et la
  gestion des évènements claviers.

En l'état, le code marche et on pourrait s'en satisfaire, mais ceci
rend notre code peu testable et peu évolutif :

* Pour tester unitairement la classe `Poney`, nous avons besoin des
  dépendances sur la bibliothèque graphique. En fait, le code fourni
  contient même un hack peu élégant pour pouvoir instancier `Poney`
  sans produire d'affichage à l'écran (ce qui sera absolument
  nécessaire pour faire des tests automatiques) : un `if (gc != null)`
  qui désactive une partie du code si le contexte graphique n'est pas
  défini.

* L'affichage graphique est trop lié au reste du programme. Si on
  devait changer de bibliothèque graphique (par exemple, pour faire
  une version en réseau où l'affichage graphique ne serait pas fait
  sur la même machine que la gestion de la logique du programme), il
  faudrait reprendre l'ensemble du programme. Imaginez le casse-tête
  si on voulait ajouter un aperçu miniature du champ de jeu dans un
  coin de la fenêtre par exemple.

* De la même manière, l'interaction avec l'utilisateur est trop liée au
  reste du programme. Si on voulait ne serait-ce que modifier les
  touches à utiliser pour jouer, il faudrait aller les chercher un peu
  partout dans le code. Et si on décidait de jouer à la souris au lieu
  du clavier, il faudrait tout reprendre.

Autrement dit, notre code est largement perfectible du point de vue du
[Single Responsibility
Principle](https://en.wikipedia.org/wiki/Single_responsibility_principle).

## Visibilité et dépendances entre nos classes

Pour l'instant, difficile de cacher les détails d'implémentation des
poneys. Par exemple pour le calcul de distance entre les poneys, on a
besoin d'accéder aux coordonnées de l'autre poney.
