package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.engine.Scene;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Scene game = new Scene();
        game.init();
    }
}
