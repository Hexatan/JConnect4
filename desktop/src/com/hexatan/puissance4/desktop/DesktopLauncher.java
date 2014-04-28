package com.hexatan.puissance4.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hexatan.puissance4.Puissance4;

public class DesktopLauncher {
    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Puissance 4";
        cfg.useGL30 = true;
        cfg.width = 800;
        cfg.height = 600;
        cfg.resizable = true;
        // cfg.addIcon("", Files.FileType.Internal);

        new LwjglApplication(new Puissance4(), cfg);
    }
}
