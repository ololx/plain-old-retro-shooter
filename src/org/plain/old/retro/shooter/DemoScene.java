package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.engine.Scene;
import org.plain.old.retro.shooter.multi.DedicatedClient;

import java.io.IOException;

/**
 * The type Main.
 */
public class DemoScene {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DedicatedClient client = null;

        if (args.length == 2) {
            client = new DedicatedClient(args[0], Integer.valueOf(args[1]));
        }

        Scene game = new Scene(client);
        game.init();
    }
}
