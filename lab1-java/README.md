Mif02 - TP Remise en route JAVA
===============================

Objectif
--------

Il vous est demandé de mettre en place quelques classes pour vous
remettre en tête les grands principes de la programmation orientée
objet : messages et collaboration entre objets, attributs et méthodes,
constructeurs, héritage, etc. Pour cela, vous manipulerez des poneys
qui font la course.

Votre travail servira de base aux TPs suivants qui feront l'objet d'une
note globale.
Le travail peut se faire en monome ou binome.

Environnement
-------------

Pour développer en Java durant le TP, vous pouvez choisir d'utiliser :

-   Un environnement de développement intégré (comme Eclipse ou
    Netbeans) qui permet de compiler, de générer un projet, de débugger
    et d'exécuter
-   N'importe quel éditeur de texte avec coloration syntaxique, et
    effectuer la compilation et l'exécution en ligne de commande
    (*javac*, *java*)

Vous pouvez travailler au choix sous Linux ou sous Windows.

En cas de problème avec Linux, il peut être nécessaire d'installer
JavaFX explicitement (`sudo apt install openjfx` sous Ubuntu 18.04).
Si votre distribution ne le fournit pas, ou bien sur les machines de
Lyon 1 où il n'est pas completement installé, utilisez la bibliothèque
fournie dans le dépôt Git du cours. Nous vous fournissons un script à
sourcer dans votre shell :

    source lib/setup-local-jfxrt.sh

Si vous avez installé JavaFX via votre distribution et que Java ne
trouve pas les classes JavaFX, ajoutez explicitement les fichiers JAR
concernés à votre classpath, avec quelque chose comme :

    CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/ext/jfxrt.jar
    CLASSPATH="$CLASSPATH":/usr/share/java/openjfx/jre/lib/jfxswt.jar
    export CLASSPATH

Travail demandé
---------------

On vous fournit un squelette [poneymon\_v0/](./poneymon_v0/) (merci à
[@dotsMarc](http://dotsmarc.tumblr.com/) pour les assets). Si ce n'est
pas encore fait, faites un `git clone` de l'ensemble des supports du
cours, et retrouvez ce répertoire (`lab1/poneymon_v0/`) dans votre
clone.

Le squelette contient ces classes :

-   Poney : une classe gérant un Poney et ses diverses méthodes
    (avancer, tourner, ...)
-   Field : une classe gérant le terrain de jeu
-   App : la principale, qui gère la création de l'application

Décompressez l'archive dans un dossier sur votre compte. Explorez le
code source. Compilez et lancez le programme principal *Poneymon*.

Consultez la documentation de [Java
8](https://docs.oracle.com/javase/10/docs/api/overview-summary.html) et de
[JavaFX Graphics](https://docs.oracle.com/javase/10/docs/api/javafx.graphics-summary.html)
(la bibliothèque graphique utilisée).

> Il est possible qu'Eclipse signale une erreur ou un warning de
> restriction d'access lors de l'utilisation de javafx. Ce problème peut
> être résolu en [modifiant les propriétés du projet pour autoriser
> javafx](http://stackoverflow.com/questions/22812488/using-javafx-in-jre-8).

### Amélioration du programme

Modifiez la classe Poney pour:

-   que la vitesse du Poney change aléatoirement à chaque tour,

Ajoutez quelques méthodes à Poney et Field pour :

-   qu'une partie consiste en 5 tours de pistes,
-   que le jeu annonce le Poney gagnant.

Testez.

### Des supers Poneys

Créez une sous-classe de Poney, appellée `PoneyImproved`, qui a pour
caractéristiques :

-   qu'en cas d'appui sur une touche associée a un Poney ce dernier
    passe en mode NianPoney qui double sa vitesse jusqu'à la fin du
    tour. Voir la [gestion des évènements clavier avec
    Javafx](http://docs.oracle.com/javafx/2/events/jfxpub-events.htm),
    et leur application dans un jeu [ici (section Handling User
    Input)](http://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835),
-   qu'en mode NianPoney l'image représentant le Poney soit différente
    (chargez le gif `pony-couleur-rainbow.gif` plutôt
    `pony-couleur-running.gif`).

Réfléchissez maintenant à la dépendance entre vos classe, et à la
visibilité (public, private, final, protected) de vos variables et
méthodes de classe.

Quelques éléments de reflexion (ne les lisez pas avant d'y avoir
réfléchi vous-mêmes) :
[architecture-et-dependances.md](architecture-et-dependances.md).

On va maintenant permettre aux Poneys de se connaitre.

-   Faites en sorte que chaque poney connaisse les autres (chaque classe
    contiendra une liste de références vers d'autres poneys). Les
    méthodes associées devront permettre d'ajouter et d'enlever un poney
    à la liste des poneys connues par le poney appelé.
-   Ajoutez une méthode permettant à un poney de connaitre la distance à
    laquelle il se trouve d'un autre poney (l'appel se fera en donnant
    une référence de poney).

Modifiez le programme principal pour tester cette nouvelle classe.
Mettez en place une méthode qui permet ce test (qui crée plusieurs
poneys, les fait se connaître les unes les autres, en gère les
déplacements, etc.). Utilisez la touche *espace* pour lancer le test:
chaque poney devra alors afficher sur la sortie standard une phrase du
type `I am green and the orange poney is 42 pixels away.`

### Un peu de stratégie

Rajoutez un peu de stratégie en ne permettant d'utiliser le mode
NianPoney qu'une seule fois (pour un tour de piste sur les cinq).

Faites en sorte que deux Poneys soient contrôlés par des joueurs et les
autres soient gérés par une IA simple, décidant quand utiliser le mode
NianPoney. Par exemple quand la vitesse est lente, pour ne pas prendre
trop de retard, quand la vitesse est rapide pour gagner un maximum
d'avance, ou quand la distance aux autres Poneys devient trop grande.

### Prendre de l'avance

Il est fortement recommandé de réaliser cette partie pendant le
week-end, si vous n'avez pas eu le temps de la faire pendant le TP.

Le code actuel mélange données, logique et interface. Commencez à
restructurer le projet pour séparer la logique générale de
l'application, de l'affichage du jeu, des données, et de la logique de
jeu (les stratégies).

Pensez à comment le programme pourrait être généralisé, pour facilement
transformer les poneys en voitures, changer le mode d'affichage (par
exemple bouger les personnages sur un circuit 2D vu de haut), ajouter de
nouvelles stratégies.
