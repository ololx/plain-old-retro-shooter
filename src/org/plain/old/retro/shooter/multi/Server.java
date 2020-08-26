package org.plain.old.retro.shooter.multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * @project plain-old-retro-shooter
 * @created 14.08.2020 14:28
 * <p>
 * @author Alexander A. Kropotin
 */
public class Server implements Runnable {

    protected final Thread flow;

    private boolean isActive;

    private int port;

    private ServerSocket serverSocket = null;

    private Set<Socket> clientSockets;

    {
        this.clientSockets = new HashSet<>();
    }

    public Server(int port) {
        this.port = port;
        this.isActive = false;
        this.flow = new Thread(this);
    }

    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            this.isActive = true;
            this.flow.start();
        } catch (IOException e) {
            System.err.println("Server error - " + e.getMessage());
        }
    }

    /**
     * Stop.
     */
    public void stop() {
        this.isActive = false;

        try {
            this.flow.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Server listening on port " + this.port + "...");

        while (true){
            Socket clientSocket = this.catchConnection();
            clientSockets.add(clientSocket);
            new ConnectionBroker(clientSocket, clientSockets).start();
        }
    }

    private Socket catchConnection() {
        Socket clientSocket = null;
        try {
            clientSocket = this.serverSocket.accept();
            System.out.println("Connection established");
        } catch(IOException e){
            System.err.println("Server error - " + e.getMessage());
        }

        return clientSocket;
    }
}

