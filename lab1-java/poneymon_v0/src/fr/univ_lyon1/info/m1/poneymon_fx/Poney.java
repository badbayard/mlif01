package fr.univ_lyon1.info.m1.poneymon_fx;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class gerant un Poney.
 *
 */
public class Poney {
    static final int X_INIT = -100 ;

    double x;       // position horizontale du poney
    final double y; // position verticale du poney
    double speed;   // vitesse du poney
    String poneyColor;
    int round; //nombre de tours

    // On cree trois images globales pour ne pas les recreer en permanence
    Image currentPoney;
    Image poneyImage;

    GraphicsContext graphicsContext;

    /**
     * Constructeur du Poney.
     *
     * @param gc ContextGraphic dans lequel on va afficher le poney
     * @param color couleur du poney
     * @param yInit position verticale
     */
    Poney(GraphicsContext gc, String color, int yInit) {
        // Tous les poneys commencent a gauche du canvas,
        // on commence a -100 pour les faire apparaitre progressivement
        x = X_INIT;
        y = yInit;
        round = 0;
        if (gc != null) {
            // gc == null can be provided for testing
            graphicsContext = gc;
            poneyColor = color;

            // On charge l'image associée au poney
            poneyImage = new Image("assets/pony-" + color + "-running.gif");

            currentPoney = poneyImage;
        }

        // Tous les poneys ont une vitesse aleatoire entre 0.0 et 1.0
        Random randomGenerator = new Random();
        speed = randomGenerator.nextFloat();
    }

    /**
     *  Affichage du poney.
     */
    void display() {
        graphicsContext.drawImage(currentPoney, x, y);
    }

    /**
     *  Deplacement du poney.
     */
    void move() {
        x += speed;
        
        if(round < 5) {
	        if (x > 520) {
	        	round = round + 1;
	            Random randomGenerator = new Random();
	            speed = randomGenerator.nextFloat();
	            x = -poneyImage.getWidth();
	        }
        }
        currentPoney = poneyImage;
    }
}
