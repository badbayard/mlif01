Objectif
--------

Il vous est demandé d’effectuer une ré-ingénierie d’un code existant en
mettant en oeuvre les patrons de conception vus en cours.

**Votre travail devra être rendu sous forme d’un projet déposé sur la
Forge Lyon 1, au plus tard le dimanche 30 septembre 2018 à 23h59.**

Penser à remplir dès à présent TOMUSS pour indiquer le nom de votre
binome et votre dépôt forge. Cf. instructions ci-dessous.

Déroulement
-----------

Le travail peut être organisé en deux étapes :

-   la première sera une étape de ré-ingénierie (refactoring) du code
    utilisé dans les premiers TP, afin de mieux structurer le projet et
    le rendre plus modulaire.
-   la seconde sera dédiée à l’extension du projet pour réaliser un jeu
    plus complet.
	
Il est conseillé de travailler en binômes.

Consigne générale
-----------------

Le but de ce TP et de ce cours en général est de vous apprendre à
écrire du code propre (irréprochable ?). On accordera une attention
particulière à la qualité du code à tous les niveaux (style,
indentation, architecture, ...). Privilégiez la propreté du code à la
quantité de fonctionalités.

### Partie 1 : Ré-ingénierie du code

Le code fourni lors de la première séance est un package relativement
fouillis. Toutes les classes sont dépendantes les unes des autres, les
couches graphique et métier ne sont pas séparées.

Il va vous falloir reconcevoir le code en appliquant les patrons de
conception adéquats. Pour cela, vous devez réorganiser les éléments de
l’application. Entre autres, l’affichage des joueurs doit être séparé de
leur gestion (déplacements, stratégies, etc.). Les changements dans le
modèle métier (déplacements) devront être répercutés dans l’affichage.
En d'autres termes, nous vous imposons un modèle MVC.

L’utilisation d’entrées claviers ou souris entraînera des changements
dans le modèle métier en passant par un contrôleur.

Appliquez un maximum de patterns et principes vus en cours, et
documentez dans votre rapport lesquels vous avez utilisé.

Ne vous contentez pas de vous dire (ni d'écrire dans votre rapport)
« nous faisons du MVC » ! Il y a bien plus de questions à vous poser,
comme par exemple :

* Comment faire communiquer Modèle, Vue et Contrôleur ? Des appels de
  méthodes directement sur les classes ? Un patron
  « [Observateur](https://en.wikipedia.org/wiki/Observer_pattern) » ?
  Un appel de méthode en passant par une classe abstraite ou une
  interface pour faire une inversion de dépendance (le « D » de
  [SOLID](https://en.wikipedia.org/wiki/SOLID)) ?
  
* Comment découper Modèle, Vue et Contrôleur ? Par exemple, mettre
  tout le modèle dans une classe violerait le
  [SRP](https://en.wikipedia.org/wiki/Single_responsibility_principle),
  mais comment le découper correctement ? Comment faire circuler
  l'information d'une classe à l'autre
  ([delegation](https://en.wikipedia.org/wiki/Delegation_pattern) ?
  Observateur ? ...) ? L'API exposée au reste du programme doit-elle
  refléter la structure de nos classes, ou bien est-ce pertinent
  d'utiliser une
  [facade](https://en.wikipedia.org/wiki/Facade_pattern) pour en
  exposer une plus simple via des
  [indirections](https://en.wikipedia.org/wiki/GRASP_(object-oriented_design)#Indirection) ?
  
* ...

Reprenez les transparents du cours et parcourez la liste des patterns
GRASP, des patterns de création, de structure, des principes SOLID, et
posez-vous la question de l'applicabilité sur votre projet.

Si vous êtes assez à l'aise avec les design patterns, à vous de jouer,
vous êtes libres ! Si vous voulez plus de conseils, voici quelques
éléments pour démarrer : [Mettre en place le pattern MVC](mvc.md). Ce
document donne quelques éléments de réponses aux questions ci-dessus,
mais ce ne sont ni les seules questions ni les seules réponses
pertinentes.

### Partie 2 : Vérification de la flexibilité du MVC

#### Plusieurs vues identiques

Une des propriétés du MVC est qu'on peut avoir un nombre quelconque de
vues, identiques ou non, d'un modèle donné. On peut vérifier
simplement que notre MVC vérifie cette propriété en ajoutant quelque
chose comme ceci dans le programme principal :

```java
// Secondary view
Stage s2 = new Stage();
JfxView v2 = new JfxView(s2, 5, 1000, 600);
c.addView(v2);
v2.setModel(m);
v2.setControler(c);
```

Vous devriez voir une deuxième fenêtre, de taille différente, qui
affiche le jeu. Les deux fenêtres doivent être synchronisées : le
temps mis par un poney pour parcourir l'écran est constant.

Si la deuxième fenêtre vous gène, mettez en commentaire le morceau de
code concerné, mais conservez-le et vérifier qu'il marche toujours au
moment du rendu.

#### Vue "score"

Créez maintenant une seconde vue qui n'affiche que le score (nombre de
tours, et éventuellement nombre de victoires) de chaque poney. Cette
vue doit s'afficher en plus de la vue principale (vous pouvez au choix
utiliser une seconde fenêtre graphique ou l'affichage texte via
`System.out`).

#### Anticiper sur le TP 5 "test"

Avant d'aller plus loin sur les extensions, c'est une bonne idée de
progresser en tests : nous serons plus en sécurité pour continuer le
refactoring et les extensions avec une bonne base de tests. Allez
jeter un œil au [TP "Test"](../lab5-test/README.md) (en particulier la
section sur le TDD), et revenez pour la suite de ce TP après. A vous
de voir dans quel ordre vous voulez avancer précisément.

### Partie 3 : Extension

Dans toute cette partie, l'ajout de fonctionnalité est un prétexte
pour se servir de design-patterns. Ajoutez chaque fonctionnalité en
appliquant les principes et patterns vus en cours, et justifiez-le
dans le rapport.

#### Boutons pour contrôler le jeu

On commencera par ajouter des éléments de contrôle au jeu pour le faire
démarrer, le mettre en pause, etc. On utilisera pour cela des widgets
javafx tels que des boutons. Il n'est pas demandé de faire une
interface « jolie » : concentrez-vous sur le fond.

Un moyen simple d'ajouter une barre de boutons en haut de l'écran est
d'ajouter ceci après `root.getChildren().add(field);` :

```java
HBox hb = new HBox(); // Boite où ranger les éléments horizontalement

Button hello = new Button("Say hello");
hello.setOnMouseClicked(new EventHandler<MouseEvent>() {
	public void handle(MouseEvent arg0) {
		System.out.println("Hello");
	}
});

Button goodBye = new Button("Say good bye");
goodBye.setOnMouseClicked(new EventHandler<MouseEvent>() {
	public void handle(MouseEvent arg0) {
		System.out.println("Good bye");
	}
});

hb.getChildren().add(hello);
hb.getChildren().add(goodBye);
root.getChildren().add(hb);
```

Vous devez avoir au minimum :

* Des boutons pour activer le mode "boost" des poneys gérés
  manuellement.
  
* Un bouton pour mettre le jeu en pause

* Un bouton pour relancer le jeu quand il est en pause (qui peut être
  le même que le bouton pause).

#### Intelligence artificielle

Assurez-vous que l'intelligence artificielle est bien intégrée et
fonctionne toujours dans votre programme MVC [(voir fin du TP remise
en route Java)](../lab1-java/).

#### Style et intégration continue

Assurez-vous que votre programme respecte toujours le style imposé
(`mvn test`), et que l'intégration continue mise en place au
[TP2](../lab2-tools) fonctionne toujours.

#### Ajouter des règles

A vous de jouer pour la suite : vous pouvez ajouter d'autres types de
coureurs que les Poneys (pourquoi pas des crocodiles qui avanceraient
plus doucement mais croqueraient les poneys qui s'aventureraient trop
près d'eux sans courir assez vite ?), des pièces bonus à ramasser pour
prolonger/augmenter le boost, des possibilités de tacler les poneys
suffisament proches, ...

Rendu du TP / projet
--------------------

### Projet Forge et questionnaire

Les projets peuvent être rendus en binômes. Les autres cours vont
arriver très vite, il est conseillé d’avoir presque terminé le week-end
suivant le TP.

**Votre travail devra être rendu sous forme d’un projet déposé sur la
Forge Lyon 1, au plus tard le dimanche 30 septembre 2018 à 23h59. Cf.
Instructions ci-dessus.**

Pensez à remplir dès à présent TOMUSS indiquant votre binome et
votre dépôt forge. Le dépot ne sera relevé qu’après la date de rendu.
**Ajoutez Matthieu MOY et Lionel MEDINI en tant que “reporters”.**

Votre dépôt sur la Forge devra contenir :

-   un fichier `README.md` (ou `.txt`) à la racine du projet
-   un fichier maven (`pom.xml`) pour le build du projet
-   les sources (fichiers Java)
-   la documentation javadoc de vos classes
-   les fichiers natifs de votre modélisation UML (indiquez quel outil a
    été utilisé)
-   le rapport en PDF (6 pages maximum, format libre).

Le rapport doit comprendre une présentation globale du projet, une
motivation des choix d’architecture (et des patterns choisis), et leur
explication en s’aidant de diagrammes appropriés et adaptés au degré de
précision et au type d’explication. Donc des diagramme de classe, mais
pas que cela, et pas de plats de spaghettis généré automatiquement
représentant tout le code.

Barême indicatif (sur 27, remis sur 20) :

-   Réalisation et exécution : 18 points
    -   Clone git qui fonctionne (les bonnes personnes sont rapporteurs,
        la bonne branche est indiquée dans TOMUSS) (0,5 pts)
    -   Compilation Maven (1 pts)
    -   Code qui tourne directement sur l’ordinateur de l’évaluateur (1
        pts)
    -   Qualité du code (2 pts)
    -   Structure globale du code, utilisation de Packages (0,5 pts)
    -   README et respect des consignes (1 pts)
    -   Interface (UI) propre (1 pts)
    -   Stratégies **simples** implémentées (2 pts)
    -   Gestion des tirs / touchés (1 pts)
    -   Tests (3 pts)
    -   Patterns mis en oeuvre (3 pts)
-   Rapport et modélisation : 9 points
    -   Qualité de la réalisation Patterns utilisés (MVC est
        obligatoire + 3 autres minimums) (3pts)
    -   Modélisation des parties clés de l’application (3pts)
    -   Explications (3pts)
    -   Les points suivants entrainent des malus (jusqu’à -5 pts)
        -   Contenu et forme (voir ci-dessus)
        -   Orthographe
