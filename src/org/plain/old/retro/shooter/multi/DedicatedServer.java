package org.plain.old.retro.shooter.multi;

import org.plain.old.retro.shooter.engine.unit.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @project plain-old-retro-shooter
 * @created 14.08.2020 14:28
 * <p>
 * @author Alexander A. Kropotin
 */
public class DedicatedServer {

    static class ServerFlow extends Thread {

        //private String requestMessage = null;

        //private BufferedReader in = null;

        //private PrintWriter out = null;

        private final Socket producerSocket;

        private final Set<Socket> consumerSockets;

        private Object requestMessage = null;

        private ObjectInputStream in = null;

        private ObjectOutputStream out = null;

        public ServerFlow(Socket producerSocket, Set<Socket> consumerSockets) {
            Objects.requireNonNull(producerSocket);
            Objects.requireNonNull(consumerSockets);
            this.producerSocket = producerSocket;
            this.consumerSockets = consumerSockets;
        }

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

    public static void main(String args[]) {
        Set<Socket> producerSockets = new HashSet<Socket>();
        Socket producerSocket = null;
        ServerSocket consumerServerSocket = null;
        int port = 666;

        if (args.length > 0) port = Integer.parseInt(args[0]);

        System.out.println("Server listening on port " + port + "...");

        try {
            consumerServerSocket = new ServerSocket(port);

            while (true){
                producerSocket = consumerServerSocket.accept();
                System.out.println("Connection established");
                producerSockets.add(producerSocket);
                new ServerFlow(producerSocket , producerSockets).start();
            }

        } catch(IOException e){
            System.err.println("Server error - " + e.getMessage());

        }
    }
}

