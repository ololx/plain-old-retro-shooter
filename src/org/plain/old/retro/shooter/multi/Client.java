package org.plain.old.retro.shooter.multi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

/**
 * @project plain-old-retro-shooter
 * @created 14.08.2020 14:28
 * <p>
 * @author Alexander A. Kropotin
 */
public class Client {

    private final String ip;

    private final int port;

    private Socket socket;

    public Client(String ip, int port) {
        Objects.requireNonNull(ip);
        Objects.requireNonNull(port);
        this.ip = ip;
        this.port = port;
    }

    public void connect() {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
