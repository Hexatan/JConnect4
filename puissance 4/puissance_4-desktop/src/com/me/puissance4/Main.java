package com.me.puissance4;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Lanceur version bureau.
 */
public class Main {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Puissance 4";
		cfg.useGL20 = true;
		cfg.width = 800;
		cfg.height = 600;
		cfg.resizable = true;
		// cfg.addIcon("", Files.FileType.Internal);

		new LwjglApplication(new Puissance4(), cfg);
	}
}
