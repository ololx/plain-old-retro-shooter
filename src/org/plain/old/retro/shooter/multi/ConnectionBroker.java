package org.plain.old.retro.shooter.multi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The type Connection broker.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 25.08.2020 21:21 <p>
 */
public class ConnectionBroker extends Thread {

    /**
     * The Producer socket.
     */
    private final Socket producerSocket;

    /**
     * The Consumer sockets.
     */
    private final Set<Socket> consumerSockets;

    /**
     * The Request message.
     */
    private Object requestMessage = null;

    /**
     * The In.
     */
    private ObjectInputStream in = null;

    /**
     * The Out.
     */
    private ObjectOutputStream out = null;

    /**
     * Instantiates a new Connection broker.
     *
     * @param producerSocket  the producer socket
     * @param consumerSockets the consumer sockets
     */
    public ConnectionBroker(Socket producerSocket, Set<Socket> consumerSockets) {
        Objects.requireNonNull(producerSocket);
        Objects.requireNonNull(consumerSockets);
        this.producerSocket = producerSocket;
        this.consumerSockets = consumerSockets;
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        List<ObjectOutputStream> writers = new ArrayList<>();

        try {
            in = new ObjectInputStream(producerSocket.getInputStream());

            synchronized (consumerSockets) {
                consumerSockets
                        .stream()
                        .filter(v -> !v.equals(producerSocket))
                        .forEach(v -> {
                            try {
                                ObjectOutputStream writer = new ObjectOutputStream(v.getOutputStream());
                                writers.add(writer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("IO error in server thread - " + e.getMessage());
        }

        try {
            while ((requestMessage = in.readObject()) != null) {

                for (ObjectOutputStream sc : writers) {
                    sc.writeObject(requestMessage);
                    sc.flush();
                    sc.close();
                }
            }
        } catch (IOException e) {
            System.err.println("IO Error/ Client " + this.getName() + " terminated abruptly - " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Client " + this.getName() + " is closed - " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Connection closing...");

                if (in != null) in.close();
                if (out != null) out.close();
                if (producerSocket != null) producerSocket.close();

                synchronized (consumerSockets) {
                    consumerSockets.remove(producerSocket);
                }

            } catch (IOException e) {
                System.out.println("Socket close error - " + e.getMessage());
            }
        }
    }
}
