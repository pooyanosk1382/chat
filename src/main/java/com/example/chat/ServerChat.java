package com.example.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;


public class ServerChat {

    private ServerSocket socket;
    private List<Client> listOfClients = new ArrayList<Client>();

    private class Client {
        String ip;
        PrintWriter writer;

        public Client(String ip, OutputStream stream) {
            this.ip = ip;
            this.writer = new PrintWriter(stream);
        }

        public String getIp() {
            return ip;
        }

        public void showMessage(String message) {
            writer.println(message);
            writer.flush();
        }
    }

    private class threadClient implements Runnable {

        private Scanner scanner;

        public threadClient(InputStream stream) {
            scanner = new Scanner(stream);
        }

        public void run() {
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                for (Client client : listOfClients) {
                    client.showMessage(client.getIp() + " said "+ s);
                }
            }
        }
    }

    public ServerChat(int port) {
        try {
            socket = new ServerSocket(port);

            System.out.println("Server is running");

            while (true) {
                Socket novoSocket = socket.accept();

                System.out.println("accepted");

                String hostAddress = novoSocket.getInetAddress().getHostAddress();
                listOfClients.add(new Client(hostAddress, novoSocket.getOutputStream()));

                new Thread(new threadClient(novoSocket.getInputStream())).start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServerChat(999);
    }

}