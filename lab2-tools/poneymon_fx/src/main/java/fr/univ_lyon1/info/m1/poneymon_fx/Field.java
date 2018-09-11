package fr.univ_lyon1.info.m1.poneymon_fx;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Classe gerant le terrain de jeu.
 *
 */
public class Field extends Canvas {

    /** Poneys. */
    Poney[] poneys = new Poney[5];
    /** Couleurs possibles. */
    String[] colorMap =
        new String[] {"blue", "green", "orange", "purple", "yellow"};
    /** Tableau traçant les evenements. */
    ArrayList<String> input = new ArrayList<String>();

    final GraphicsContext gc;
    final int width;
    final int height;

    /**
     * Canvas dans lequel on va dessiner le jeu.
     *
     * @param scene Scene principale du jeu a laquelle on va ajouter notre
     *              Canvas

     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
    public Field(Scene scene, int w, int h) {
        super(w, h);
        width = w;
        height = h;

        /*
         * Permet de capturer le focus et donc les evenements clavier et
         * souris
         */
        this.setFocusTraversable(true);

        gc = this.getGraphicsContext2D();

        /* On initialise le terrain de course */
        for (int i = 0; i < poneys.length; i++) {
            poneys[i] = new Poney(gc, colorMap[i], i * 110);
            poneys[i].display();
        }

        /*
         * Event Listener du clavier
         * quand une touche est pressee on la rajoute a la liste d'input
         *
         */
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                ; // TODO
            }
        });

        /*
         * Event Listener du clavier
         * quand une touche est relachee on l'enleve de la liste d'input
         */
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                ; // TODO
            }
        });

        /*
         * Boucle principale du jeu.
         *
         * handle() est appelee a chaque rafraichissement de frame
         * soit environ 60 fois par seconde.
         */
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // On nettoie le canvas a chaque frame
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(0, 0, width, height);

                // Deplacement et affichage des poneys
                for (int i = 0; i < poneys.length; i++) {
                    poneys[i].move();
                    poneys[i].display();
                }
            }
        }
            .start(); // On lance la boucle de rafraichissement
    }
}
