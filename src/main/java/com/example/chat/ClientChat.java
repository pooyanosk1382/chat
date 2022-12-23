package com.example.chat;

import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.PrintWriter;


public class ClientChat {

    private Socket socket;

    public ClientChat(String ip, int port) {
        Scanner in = new Scanner(System.in);

        try {
            socket = new Socket(ip, port);
            System.out.println("Connected");
            new Thread(new threadServer(socket.getInputStream())).start();
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            while (in.hasNextLine()) {
                printWriter.println(in.nextLine());
                printWriter.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class threadServer implements Runnable {

        private Scanner scanner;

        public threadServer(InputStream stream) {
            scanner = new Scanner(stream);
        }

        public void run() {
            while (scanner.hasNextLine()) {
                System.err.println(scanner.nextLine());
            }
        }
    }

    public static void main(String[] args) {
        new ClientChat("hostname", 999);
    }

}