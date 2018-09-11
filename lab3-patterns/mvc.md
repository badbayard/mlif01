# Mettre en place le pattern MVC

## Découpage et responsabilités du M, V, C.

Il existe beaucoup de variantes du pattern MVC. Certains mettent la
logique métier dans le contrôleur, d'autres dans le modèle. Parfois
les entrées au clavier de l'utilisateur arrivent via le contrôleur,
parfois c'est la vue qui en est chargée. Vous êtes libres d'appliquer
la variante que vous souhaitez (et donc d'ignorer les suggestions de
ce document).

Nous proposons ici d'appliquer le MVC tel qu'il est présenté dans les
transparents, donc appliquer le découpage suivant :

* Le modèle définit les données (position des poneys, qui est en mode
  boost, ...). Tout doit être pensé en terme de données logiques,
  indépendamment du rendu graphique, donc :
  
  - Aucune référence à un nombre de pixels. Un poney peut par exemple
    être défini par sa rangée (`int`) et par sa progression dans un
    tour (`double` compris entre `0.0` et `1.0`).
	
  - Aucune dépendance vers le toolkit graphique (JavaFX). Le modèle
    doit pouvoir être réutilisable sans aucune modification pour une
    application en mode texte par exemple.
	
  - Aucune référence à des noms de touches au clavier. Par exemple, le
    modèle doit recevoir l'information « l'utilisateur a demandé à
    passer le Poney numéro 2 en mode boost », mais pas « l'utilisateur
    a appuyé sur la touche ... » (qui est du domaine de la vue).

* La vue s'occupe de gérer l'affichage et les entrées clavier. Dans la
  vue, les positions des objets sont comptés en pixels. C'est la vue
  qui s'occupe d'afficher les poneys avec un arc en ciel quand ils
  sont en mode "boost". La vue récupère les entrées claviers, et
  prévient le modèle, via le contrôleur, qu'une action doit être
  faite.

* Le contrôleur s'occupe de transmettre l'information. Il est attaché
  à un modèle, et à un nombre quelconque de vues (0, 1, plusieurs).
  Quand le modèle envoie de l'information, il passe par le contrôleur
  qui route l'information aux vues. Le contrôleur s'occupera aussi de
  mettre en place le timer qui fait avancer le jeu avec le temps.

## Mise en place

Nous allons implémenter `Model`, `View` et `Controler` avec 3 packages
java (`fr.univ-lyon1.info.m1.poneymon_fx.Model`, et idem pour `View`
et `Controler`), contenant chacun une ou plusieurs classes.

Commencez par créer un package `Model`, contenant une classe
`FieldModel`, contenant elle-même un tableau de `PoneyModel` (qui sera
définie dans le même package). Chaque `PoneyModel` contient ses
coordonnées logiques (attention, pas de position en pixel ...).

Commencez à écrire la logique du jeu en ajoutant une fonction `step()`
à `FieldModel` qui appellera la méthode du même nom sur chaque
`PoneyModel` du jeu. `PoneyModel.step()` fait avancer le poney d'un
pas (dépendant de sa vitesse). Ne cherchez pas à coder l'ensemble des
règles maintenant, vous le ferez quand vous aurez l'architecture
complète.

Pour parler technique, nous venons d'utiliser les patrons
[Composite](https://en.wikipedia.org/wiki/Composite_pattern) pour
gérer tous les Poneys d'un coup, et
[Expert](https://en.wikipedia.org/wiki/GRASP_(object-oriented_design)#Information_expert)
pour décider que c'est à la classe `PoneyModel` d'appliquer la règle
« chaque poney avance à chaque pas ».

Créez maintenant le contrôleur : un package
`fr.univ-lyon1.info.m1.poneymon_fx.Controler` contenant une classe
`Controler`. Cette classe contient une référence vers le modèle, et
une liste de vues (`ArrayList<AbstractView>`), avec les méthodes
associées (`addView`, `setModel`, ...). Elle pourra contenir
par exemple une méthode `notifyViews` qui appelle la méthode `update`
sur chaque vue enregistrée (pattern
[Observer](https://en.wikipedia.org/wiki/Observer_pattern)).

Créez maintenant la vue : un package
`fr.univ-lyon1.info.m1.poneymon_fx.View` contenant les classes
`AbstractView` (qui définit l'interface d'une vue, sans donner son
implémentation), `JfxView` (qui s'occupe de l'affichage de la fenêtre
via JavaFX), `FieldView` (qui dérive de `Canvas` et s'occupe
d'afficher le rectangle de jeu), et `PoneyView`.

`PoneyView` contient les coordonnées en pixels du poney. Ces
coordonnées sont mis à jour en interrogeant le modèle à chaque fois
que le contrôleur notifie la vue qu'un changement a eu lieu.

`PoneyView` et `FieldView` ont chacun une méthode `display()` qui fait
l'affichage effectif (remplissage du fond en gris pour `FieldView`, et
affichage de l'image pour `PoneyView`).

`FieldView` s'occupe également de rattraper les évènements claviers
(`this.setOnKeyPressed`) et de les transmettre au contrôleur.

Au final, la fonction principale de notre application peut être aussi
simple que :

```java
    @Override
    public void start(Stage stage) throws Exception {
        FieldModel m = new FieldModel(5); // 5 poneys
        JfxView v = new JfxView(stage, 600, 600); // 600x600 pixels
        Controler c = new Controler();

        c.addView(v);
        c.setModel(m);
        v.setModel(m);
        v.setControler(c);

        c.startTimer();
    }
```
