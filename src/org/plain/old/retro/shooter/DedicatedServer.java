package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.multi.Server;

/**
 * @project plain-old-retro-shooter
 * @created 14.08.2020 14:28
 * <p>
 * @author Alexander A. Kropotin
 */
public class DedicatedServer {

    public static void main(String args[]) {

        if (args.length > 0) {
            Server server = new Server(Integer.valueOf(args[0]));
            server.start();
        }
    }
}

