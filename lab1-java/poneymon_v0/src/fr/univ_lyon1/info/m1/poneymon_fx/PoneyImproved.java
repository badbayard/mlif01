package fr.univ_lyon1.info.m1.poneymon_fx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PoneyImproved extends Poney {
	
	PoneyImproved(GraphicsContext gc, String color, int yInit) { 
		poneyImage = new Image("assets/pony-" + color + "-rainbow.gif");
	}
}
