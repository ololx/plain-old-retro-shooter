package io.github.ololx.plain.old.retro.shooter;

import io.github.ololx.plain.old.retro.shooter.multi.Server;

/**
 * The type Dedicated server.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 14.08.2020 14:28 <p>
 */
public class DedicatedServer {

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String args[]) {

        if (args.length > 0) {
            Server server = new Server(Integer.valueOf(args[0]));
            server.start();
        }
    }
}

