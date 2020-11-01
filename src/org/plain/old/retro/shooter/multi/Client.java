package org.plain.old.retro.shooter.multi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * The type Client.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 14.08.2020 14:28 <p>
 */
public class Client {

    /**
     * The Ip.
     */
    private final String ip;

    /**
     * The Port.
     */
    private final int port;

    /**
     * The Socket.
     */
    private Socket socket;

    /**
     * Instantiates a new Client.
     *
     * @param ip   the ip
     * @param port the port
     */
    public Client(String ip, int port) {
        Objects.requireNonNull(ip);
        Objects.requireNonNull(port);
        this.ip = ip;
        this.port = port;
    }

    /**
     * Connect.
     */
    public void connect() {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect.
     */
    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send message object.
     *
     * @param requestMessage the request message
     * @return the object
     */
    public Object sendMessage(Object requestMessage) {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        Object responseMessage = null;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(requestMessage);
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

            responseMessage = in.readObject();

            in.close();
            out.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return responseMessage;
    }
}

