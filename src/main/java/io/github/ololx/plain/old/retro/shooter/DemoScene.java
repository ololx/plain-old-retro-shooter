package io.github.ololx.plain.old.retro.shooter;

import io.github.ololx.plain.old.retro.shooter.multi.Client;
import io.github.ololx.plain.old.retro.shooter.multi.Server;

import java.io.IOException;

/**
 * The type Main.
 */
public class DemoScene {

    /**
     * The constant DEFAULT_ADDRESS.
     */
    public static final String DEFAULT_ADDRESS = "127.0.0.1";

    /**
     * The constant DEFAULT_PORT.
     */
    public static final int DEFAULT_PORT = 6666;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = null;
        Client client = null;

        switch (args.length) {
            case 1:
                server = new Server(Integer.valueOf(args[0]));
                server.start();
                client = new Client(DEFAULT_ADDRESS, Integer.valueOf(args[0]));
                break;
            case 2:
                client = new Client(args[0], Integer.valueOf(args[1]));
                break;
            default:
                server = new Server(DEFAULT_PORT);
                server.start();
                client = new Client(DEFAULT_ADDRESS, DEFAULT_PORT);
        }

        Scene game = new Scene(client);
        game.init();
    }
}
