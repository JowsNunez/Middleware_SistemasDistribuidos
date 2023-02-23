package com.middleware.server;

import com.middleware.persists.Students;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public class Server implements Runnable {

    private ServerSocket serverSocket;
    private static Server instance;
    private int port;
    private HandleClient handleClient;
    private Map<String, List<HandleClient>> connectedClients;
    private static Students simulateBD;

    private Server(int port) {
        this.port = port;
        this.connectedClients = new HashMap<>();
        this.initServer();
    }

    /**
     * Metodo que crea una unica instancia de Servidor
     *
     * @param port
     * @return
     */
    private static Server getInstance(int port) {
        if (instance != null) {
            return instance;
        }
        return instance = new Server(port);
    }

    /**
     * Metodo que inica el Servidor
     */
    private void initServer() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Hilo que escucha la llegada de un cliente nuevo
    @Override
    public void run() {

        while (true) {
            try {

                Socket socket = this.serverSocket.accept();
                // se crea un instancia para el nuevo cliente
                Client client = new Client();
                client.setSocket(socket);
                client.setIdClient(this.generateId());
                client.setOut(new DataOutputStream(socket.getOutputStream()));
                client.setIn(new DataInputStream(socket.getInputStream()));
                // se obtiene la peticion de conexion
                String connect = client.getIn().readUTF();

                if (connect.contains("CONNECT")) {
                    this.handleClient = new HandleClient(client);
                    String topic = connect.substring("CONNECT:".length(), connect.length());
                    client.setTopic(topic);
                    List<HandleClient> aux = this.connectedClients.get(topic);

                    // si no existe la lista y el topico se crea uno
                    if (aux == null) {
                        aux = new ArrayList<>();
                        aux.add(handleClient);
                        System.out.println("Aqui");
                    } else {
                        aux.add(handleClient);
                    }

                    this.connectedClients.put(topic, aux);
                    this.handleClient.sendToClient(client, "TOKEN:" + client.getIdClient());
                    this.handleClient.setClients(this.connectedClients);
                    Thread thread = new Thread(this.handleClient);
                    thread.start();

                    System.out.println(this.connectedClients);

                } else {
                    client.getSocket().close();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * Metodo que genera Id
     *
     * @return devuelve un String con un Id Random
     */
    public String generateId() {
        String characters = "ABCDEFGHIJKLMNOPKRSTUVWXYZabcedefghijklmnopqrstuvwxyz1234567890#$%&";
        int max = characters.length();
        String id = "";

        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.floor(Math.random() * (0 - max + 1) + max));
            id += characters.charAt(random);

        }
        if (!id.isEmpty()) {
            id += "." + new Date().getTime();
        }
        return id;

    }

    /**
     * Metodo para obtener la lista que simula la BD
     *
     * @return devuelve la lista
     */
    public static Students getSimulateBD() {
        return simulateBD;
    }

    /**
     * inicia el servidor dentro de un hilo y ademas inicia la simulacion de la
     * BD
     */
    public static void init() {
        new Thread(getInstance(9000)).start();
        simulateBD = new Students();
    }

}
