# Exercices

Pour cette séance, il est recommandé d'utiliser un IDE. Ce document
donne quelques pistes en utilisant Eclipse, mais n'hésitez pas à
proposer des conseils pour votre IDE préféré en faisant des
pull-requests sur le dépôt enseignant.

Un corrigé incomplet est disponible dans le répertoire
[tp_test](tp_test/).

## Création d’un projet de test

Pour vous faire la main, vous allez créer un nouveau projet où vous
effectuerez quelques tests. Les fonctionnalités à tester seront des
transformations simples de chaînes de caractères. Créez un projet
maven simple

```
mvn archetype:generate -DarchetypeGroupId=org.spilth -DarchetypeArtifactId=java8-junit4-quickstart
```

Cette commande va utiliser le squelette proposé
[ici](https://github.com/spilth/java8-junit4-quickstart) qui active
JUnit 4 avec Java 8.

Répondez à quelques questions, comme quel est le :

* `groupId` : `fr.univ_lyon1.info.m1`

* `artefactId` : `tp_test`

* `version` : laissez la valeur par défaut (`1.0-SNAPSHOT`) en appuyant sur <kbd>Entrée</kbd>

* `package` : laissez la valeur par défaut (`fr.univ_lyon1.info.m1`)

## Parcours du projet de test

Le projet généré devrait ressembler à ceci (sous Linux, utilisez la
commande `tree` pour le vérifier d'un coup d'œil) :

```
.
├── pom.xml
├── README.md
└── src
    ├── main
    │   └── java
    │       └── fr
    │           └── univ_lyon1
    │               └── info
    │                   └── m1
    ├── site
    │   ├── markdown
    │   │   └── index.md
    │   └── site.xml
    └── test
        └── java
            └── fr
                └── univ_lyon1
                    └── info
                        └── m1
```

Le fichier `pom.xml` est assez standard, il active JUnit 4.12 (on peut
utiliser `mvn versions:display-dependency-updates` pour vérifier qu'il
n'existe pas de version plus récente de JUnit 4), et nous fournit deux
répertoires vides :

* `main/java/` votre groupId `/`, qui contiendra le code de nos
  programmes.

* `test/java/` votre groupId `/`, qui contiendra les tests.

Le répertoire `src/site` ne nous servira pas.

## Écriture d'un programme à tester

Ouvrez ce projet avec votre IDE préféré si ce n’est pas déjà fait.
Créez la classe `CharManipulator` (dans
`fr.univ_lyon1.info.m1.tp_test`), qui implémentera l’interface
suivante :

```java
package fr.univ_lyon1.info.m1.tp_test;

public interface ICharManipulator {
    public String invertOrder(String s);
    public String invertCase(String s);
}
```

Laissez le corps des méthodes vides (ou avec un `return null;`) pour l’instant, vous les implémenterez après avoir écrit les tests.

## Création du premier test

Vous allez maintenant créer votre première classe de test. Le but est
d'écrire des tests automatiques (qui s'exécutent sans intervention de
l'utilisateur) : du code qui teste du code.

Vos pouvez créer la classe de test soit à la
main, soit en vous laissant guider par votre IDE (exemple sous
Eclipse : clic droit sur le projet → « New » → « Other... » →
« JUnit test case »). Vous devez obtenir une classe
`CharManipulatorTest` dans `src/test/java` (et non `src/main/java` !)
qui :

* ne dérive d’aucune classe

* contient un `import static org.junit.Assert.*`; en haut de son fichier
  et bien sûr `import org.junit.Test`;

* possède une unique méthode, qui ne fait rien (si votre IDE a généré
  un appel à `fail(...)`, supprimez-le pour l'instant), et à laquelle
  est attachée une annotation `@Test`

Attention, si votre classe dérive de `TestCase`, votre projet utilise
JUnit 3 ! Vous devrez probablement rajouter JUnit4 au path de votre
projet, soit en sélectionnant la bonne option lors de la création du
test, soit en l’ajoutant à la main.

Pour vérifier que vous avez bien tout fait comme il faut, exécutez
`mvn test` à la racine de votre projet. Il devrait afficher un gros
"Build success" en vert, avec un seul "test run". Pour être vraiment
sûrs que votre projet est bien configuré, faites échouer votre test.
Rappel : un test Junit échoue lorsqu’une exception non catchée est
lancée. Pour faire simple, vous pouvez utiliser la fonction
`fail(String message);` de JUnit 4. Ensuite, ré-effectuez un `mvn
test`, qui cette fois doit échouer. Tout va bien ? Parfait, passez à
la suite.

Essayez de faire les mêmes tests via votre IDE (Sous Eclipse,
<kbd>Alt</kbd>+<kbd>Shift</kbd>+<kbd>x</kbd> puis <kbd>t</kbd> pour
lancer le test, <kbd>Alt</kbd>+<kbd>Shift</kbd>+<kbd>d</kbd> puis
<kbd>t</kbd> pour faire la même chose en mode debug, ou à la souris :
Clic droit sur un fichier ou un projet → « Run as » → « JUnit
test »)

vous aurez ainsi une jolie barre qui partira rouge et se colorera de
vert à mesure de votre avancée, et vous pourrez ouvrir l'éditeur sur
l'assertion fautive en un clic.

Pour les maniaques des tests, vous pouvez même jouer avec le plugin
[infinitest](https://infinitest.github.io/) qui lance les tests en
tâche de fond à chaque fois que vous sauvez !

En pratique, lancer les tests depuis l'IDE est intéressant pour le
confort au quotidien (bientôt, vous lancerez les tests aussi souvent
que vous compilez), et lancer les test en ligne de commande via `mvn
test` est indispensable pour l'intégration continue (où vos tests
tourneront sur un serveur sans utilisateur humain ni interface
graphique).

## Création de (vrais) tests

Votre classe de tests va comporter trois parties : une pour chaque
fonctionnalité à tester. Par chance, les fonctionnalités que vous
voulez tester correspondent aux fonctions de la classe. Chacune des
parties comportera plusieurs fonctions de test : une pour chaque cas
de figure à tester. En règle générale, on crée une fonction pour
l’ensemble des cas "standards", puis une pour chaque cas particulier
que l’on a repéré. Si vous n’êtes pas familier avec JUnit 4, un
récapitulatif de ses possibilités est présenté
[ici](http://www.vogella.com/tutorials/JUnit/article.html#usingjuni4)
(toute la partie 4).

Un test est classiquement écrit en 3 parties, que l'on appelle souvent
"Given/when/then" :

* "Given" (étant donné ...) construit les objets que l'on souhaite
  tester et amène le programme juste avant l'opération que l'on veut
  tester.
  
* "When" (quand ...) effectue l'action que l'on cherche à tester

* "Then" (alors ...) vérifie que l'action s'est bien déroulée comme on
  le souhaite.

Une manière un peu verbeuse de tester que la méthode `invertOrder`
fonctionne correctement sur l'entrée `"ABC"` est donc :


```java
public class CharManipulatorTest {
    @Test
    public void testAbcd() {
    	// Given
    	CharManipulator manipulator = new CharManipulator();
    	
    	// When
    	String invert = manipulator.invertOrder("ABCD");
    	
    	// Then
    	assertEquals("DCBA", invert);
    }
}
```

Recopiez ce test dans votre projet, et vérifiez qu'il échoue (vous
n'avez pas encore codé `manipulator.invertOrder()` ...). Attaquez
l’implémentation de la fonction invertOrder. Vous pourrez ainsi tester
au fur et à mesure du développement et être emplis de joie en voyant
le nombre d’échecs diminuer.

En pratique on peut être beaucoup plus concis :

* La ligne `CharManipulator manipulator = new CharManipulator()` sera
  probablement la même pour tous les tests, on peut transformer
  `manipulator` en un champ de classe et l'initialiser avant le
  lancement du test (JUnit fournit une annotation `@Before` pour
  cela).
  
* Les deux lignes `String invert = ...` et `assertEquals(..., invert)`
  peuvent être groupées en une.
  
* Ici, le test est suffisamment simple pour qu'on accepte de mettre
  plusieurs tests dans la même fonction (mais ce n'est pas une bonne
  idée pour les tests non-triviaux, entre autres car cela empêche
  d'exécuter les tests qui suivent une assertion violée).
  
Au final, on arrive à ceci :
  
```java
public class CharManipulatorTest {

    private CharManipulator manipulator;
    
    @Before
    public void setUp() {
        manipulator = new CharManipulator();
    }

    @Test
    public void orderNormalString() {
        assertEquals("A", manipulator.invertOrder("A"));
        assertEquals("DCBA", manipulator.invertOrder("ABCD"));
        assertEquals("321DCBA", manipulator.invertOrder("ABCD123"));
    }
	
    @Test
    public void orderEmptyString()
    {
        assertEquals("", manipulator.invertOrder(""));
    }
}
```

Quelques compléments :

* JUnit fournit aussi une annotation `@After`, symétrique de
  `@Before`, qui peut être utilisée pour nettoyer les ressources
  allouées par `@After` (en pratique le garbage-collector de Java fait
  souvent tout ce qu'il faut donc `@After` est plus rarement utilisé).
  
* On peut aussi exécuter un morceau de code avant et après l'ensemble
  des tests d'une classe via `@BeforeClass`

* Les fonctions de tests seront appelées par JUnit, mais jamais par
  vous-mêmes. Il n'est donc pas gênant d'avoir des noms de fonctions
  longs, au contraire ! N'hésitez pas à utiliser des noms très
  expressifs. Par exemple le dernier test aurait pu s'appeler
  `invertOrderEmptyStringReturnsEmptyString()`. L'intérêt de ces noms
  à rallonge est qu'on sait immédiatement de quoi il s'agit quand on
  voit un rapport d'échec de test.

Faites ensuite de même pour `invertCase`. Pour préciser :

* La fonction `invertCase` est censée inverser la casse, c’est-à-dire inverser les majuscules et les minuscules.

* exemple : "abCD" → "ABcd"

### Test-Driven Development (TDD)

C'est en général une bonne idée d'écrire les tests avant d'écrire le
code. Une bonne discipline est même : ne jamais écrire de code dans le
programme tant qu'on n'a pas un test qui échoue (et qui dicte ce qu'on
doit écrire).

Nous allons l'appliquer pour ajouter une fonction `removePattern` à
notre programme. La fonction `removePattern` doit retourner la
première chaîne de caractère à qui l’on a enlevé toutes les
occurrences d'une autre chaîne.

#### Écriture du test

Commencez par écrire un test (avant de toucher quoi que ce soit au
programme). Un premier test peut par exemple vérifier :

```java
assertEquals("cc", manipulator.removePattern("coucou", "ou"));
```

Pensez vos tests non seulement comme un moyen de vérifier votre
programme, mais aussi comme un exemple d'utilisation. Ici, en
écrivant le test on a aussi décidé que `removePattern` allait prendre
deux arguments : la chaîne puis le pattern à supprimer. Dans des cas
plus compliqués, les parties "Then" et "When" du tests peuvent être
non-triviales à écrire, mais c'est en général plus facile de
concevoir une API en écrivant des exemples d'utilisation (donc des
tests) qu'en écrivant l'API directement.

A ce stade, votre code ne compile pas car la fonction n'existe pas.
Votre IDE a probablement souligné `removePattern`.

#### Squelette de code

Pour écrire le code nécessaire pour que votre test soit compilable,
vous pouvez vous aider de votre IDE. Par exemple avec Eclipse, en
passant la souris sur le code souligné (ou bien <kbd>Control</kbd>
+<kbd>1</kbd> au clavier),
l'IDE propose comme correction du problème « create method
`removePattern(...)` ». Choisissez cette option : l'IDE génère pour
vous un squelette de méthode. Vérifiez et adaptez le prototype de la
fonction. Par exemple, Eclipse a généré une méthode avec un type de
retour `Object`, il faut mettre `String` à la place. Si votre IDE a
bien deviné les types des arguments, il n'a probablement pas inventé
de nom pertinent : à vous de le faire.

Une fois le squelette de méthode concrète défini, on peut demander à
l'IDE de l'ajouter dans l'interface (toujours
<kbd>Control</kbd>+<kbd>1</kbd> sous Eclipse, puis « create
`removePattern()` in super type »).

Vous pouvez maintenant lancer le test : il est compilable, mais doit
échouer. S'il n'échoue pas, c'est que vous avez mal écrit votre test
(auquel cas c'est le bon moment pour le corriger), ou que la
fonctionnalité existe déjà (et c'est le bon moment pour s'en rendre
compte).

#### Écriture du code dans le programme

L'étape suivante est d'écrire le code nécessaire pour que le test
passe. Dans cette phase on s'autorise souvent à écrire du code
temporaire pour que le test passe, et on le nettoiera juste après. Par
exemple ici on peut écrire :

```java
public String removePattern(String string, String string2) {
    return "cc"; // TODO
}
```

(En n'oubliant pas le `TODO` qu'il faudra supprimer avant d'oublier)

#### Refactoring

Maintenant que les tests passent, on peut s'occuper de refactorer le
code, de remplacer les morceaux de code temporaires par le vrai
code, ...

Votre IDE peut vous aider ici aussi (sous Eclipse, regardez le contenu
de menu « Refactor », accessible via un clic droit sur du code ou bien
<kbd>Shift</kbd>+<kbd>Alt</kbd>+<kbd>t</kbd>). Refaites passer les
tests régulièrement pour vérifier que vous n'avez rien cassé.

#### Commit :-)

Voila, vous avez un code irréprochable et bien testé. Un `git commit`
et sans doute un `git push` s'impose.

#### goto "Écriture du test"

Recommencez jusqu'à épuisement du développeur ! Ajoutez des tests pour
des cas particuliers que vous risquez d'avoir oublié (avez-vous pensé
à `removePattern("aabb", "ab")` par exemple ?), ou pour des nouvelles
fonctionnalités de votre programme.

Pour l'exercice, vous devez avoir 3 fonctions dans l'interface et au
moins 3 tests par fonction. Quand c'est fait, passez à la partie
intéressante : l’implémentation des tests pour votre projet.

### Aller plus loin

Quelques outils amusants si vous voulez creuser un peu la question des
tests :

* Les [outils de couverture de
  code](https://en.wikipedia.org/wiki/Java_Code_Coverage_Tools) qui
  vous permettent de savoir quelles instructions sont exécutées
  (« couvertes ») par vos tests, et donc quelles parties du programme
  n'ont pas été testées. Ces outils peuvent s'intégrer à Maven et à
  votre IDE.
  
* Les outils de mock comme [Mockito](https://site.mockito.org/), qui
  vous permettent de fabriquer des « mocks », c'est à dire des faux
  objets, pour tester une classe sans avoir besoin d'instancier ses
  dépendances. Par exemple, on pourrait tester le contrôleur de notre
  programme MVC en isolation en faisant des « mocks » de la vue et du
  modèle.
  
* Les bibliothèques d'assertions avancées, comme alternative aux
  assertions JUnit, comme
  [AssertJ](http://joel-costigliola.github.io/assertj/) ou [Google
  truth](http://google.github.io/truth/) qui permettent d'écrire des
  assertions plus proches du langage naturel
  (`assertThat(string).startsWith("awe");` : enlevez la ponctuation et
  vos avez la phrase en anglais) et de mieux bénéficier de la
  complétion automatique de votre IDE.
  
## Tests unitaires du projet

Vous avez probablement beaucoup modifié le projet de base lors du TP
de refactoring. Si vous aviez appris le TDD plus tôt, vous auriez
écrit les tests avant de refactorer, mais il n'est jamais trop tard !

A ce stade, vos projets sont tous différents donc il n'y a pas de
consignes précises. Quelques conseils pour vous lancer :

* Tester le modèle est assez simple, le contrôleur un peu plus dur
  mais faisable, la vue relativement difficile
  
* Concentrez-vous sur les tests automatiques, mais vous pouvez aussi
  faire des tests manuels quand les tests automatiques sont
  impossibles ou trop difficiles (c'est difficile d'instancier des
  classes JavaFX dans des tests automatiques ...). Documentez dans
  votre rapport quels tests manuels vous avez fait.

* Faites vos tests avant l’implémentation : comme ça vous réfléchissez
  à tous les cas étranges pendant vos tests, qui sont au demeurant
  simples à coder, et vous pourrez vous concentrer sur les problèmes
  de code lors de l’implémentation

* Vous êtes en binôme, profitez-en ! Donnez les tests d’une partie à
  l’un des membres du binôme, et faites-la implémenter par l’autre ;
  cela peut paraître contre-productif, mais c’est ce qui vous
  permettra de couvrir tout de suite tous les cas particuliers

* Pensez à tester aussi les cas "normaux", cela vous permettra de
  distinguer une fonction mal codée d’une fonction pas codée du tout
  ou inaccessible

Que doivent faire les tests à minima :

* Tester que les poneys ne sortent jamais du terrain.

* Tester que les poneys avancent bien à la bonne vitesse.

* Tester le mode boost

* Tester que votre IA se comporte correctement

Bon travail ! ;)
