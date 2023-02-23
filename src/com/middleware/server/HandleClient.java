/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.middleware.server;

import com.middleware.bussines.Student;
import com.middleware.interpret.RegistryDataInterpreter;
import com.middleware.interpret.TeacherDataInterpreter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public class HandleClient implements Runnable {

    private Client client;
    private Map<String, List<HandleClient>> clients;

    public HandleClient() {
    }

    public HandleClient(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Map<String, Object> data;
        try {
            while (true) {
                if (this.client.getIn().available() > 0) {
                    String msg = this.client.getIn().readUTF();

                    if (this.client.getTopic().equals("REGSYSTEM")) {
                        data = new RegistryDataInterpreter().decode(msg);
                        sendToClient(data);
                    } else if (this.client.getTopic().equals("TEACHERSYSTEM")) {

                        data = new TeacherDataInterpreter().decode(msg);
                        sendToClient(data);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Metodo para enviar un mensaje a un cliente en especifico.
     *
     * @param client Representa un cliente
     * @param msg representa al contenido del mensaje
     */
    public void sendToClient(Client client, String msg) {
        try {
            client.getOut().writeUTF(msg);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo para enviar un mensaje a un cliente dentro de un topico
     *
     * @param data representa los datos a enviar
     */
    public void sendToClient(Map<String, Object> data) {
        String msg;
        String to = (String) data.get("to");
        String from = (String) data.get("from");
        String action = (String) data.get("Action");
        Student student = (Student) data.get("Student");

        this.doAction(action, student, from);
        try {
            List<HandleClient> aux = this.clients.get(to);
            if (to.equals("REGSYSTEM")) {
                msg = new RegistryDataInterpreter().encode(action, student, to, from);
                for (HandleClient handleClient : aux) {
                    handleClient.getClient().getOut().writeUTF(msg);
                }
            } else if (to.equals("TEACHERSYSTEM")) {
                msg = new TeacherDataInterpreter().encode(action, student, to, from);
                for (HandleClient handleClient : aux) {
                    handleClient.getClient().getOut().writeUTF(msg);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * metodo para enviar mensajes a todos los clientes
     *
     * @param clients
     * @param data
     */
    public void broadcast(String data) {
        try {

            for (List<HandleClient> handleClients : this.clients.values()) {
                for (HandleClient handleClient : handleClients) {
                    handleClient.getClient().getOut().writeUTF(data);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setClients(Map<String, List<HandleClient>> connectedClients) {
        this.clients = connectedClients;
    }

    public void doAction(String action, Student student, String who) {
        if (action.equals("SAVESTUDENT")) {
            Server.getSimulateBD().addStudent(student);
            

        }
        if (action.equals("UPDATEQUALIFICATION")) {
            Server.getSimulateBD().updateStudent(student);
        }
                    System.out.println(who + " " + action + " client:" + this.client.getIdClient());

    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "HandleClient{" + "client=" + client + '}';
    }

}
