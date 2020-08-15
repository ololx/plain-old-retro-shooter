package org.plain.old.retro.shooter.multi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

        private String requestMessage = null;

        private BufferedReader in = null;

        private PrintWriter out = null;

        private final Socket producerSocket;

        private final Set<Socket> consumerSockets;

        public ServerFlow(Socket producerSocket, Set<Socket> consumerSockets) {
            Objects.requireNonNull(producerSocket);
            Objects.requireNonNull(consumerSockets);
            this.producerSocket = producerSocket;
            this.consumerSockets = consumerSockets;
        }

        public void run() {
            List<PrintWriter> writers = new ArrayList<>();

            try {
                in = new BufferedReader(new InputStreamReader(producerSocket.getInputStream()));

                synchronized (consumerSockets) {
                    consumerSockets
                            .stream()
                            .filter(v -> !v.equals(producerSocket))
                            .forEach(v -> {
                                try {
                                    writers.add(new PrintWriter(v.getOutputStream()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            } catch (IOException e) {
                System.err.println("IO error in server thread - " + e.getMessage());
            }

            try {
                requestMessage = in.readLine();
                while (requestMessage.compareTo("") != 1) {
                    for (PrintWriter sc : writers) {
                        sc.println(requestMessage);
                        sc.flush();
                        sc.close();
                    }

                    requestMessage = in.readLine();
                }
            } catch (IOException e) {
                System.err.println("IO Error/ Client " + this.getName() + " terminated abruptly - " + e.getMessage());
            } catch (NullPointerException e) {
                requestMessage = this.getName(); //reused String line for getting thread name
                System.err.println("Client " + this.getName() + " is closed - " + e.getMessage());
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

