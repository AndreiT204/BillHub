package com.billhub;

import java.io.*;
import java.net.Socket;

public class AdminClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 9090;

        System.out.println("Connecting to BillHub Admin Server on port " + port + "...");

        try (Socket socket = new Socket(hostname, port)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Sending Command: STATUS");
            out.println("STATUS");

            String response = in.readLine();
            System.out.println("Server Replied: " + response);

        } catch (IOException e) {
            System.err.println("Connection failed!");
            System.err.println("   -> Is the Spring Boot Backend running?");
            System.err.println("   -> Did SocketServerService start on port 9090?");
            e.printStackTrace();
        }
    }
}