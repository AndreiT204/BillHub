package com.billhub.app;

import java.io.*;
import java.net.Socket;

public class AdminClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 9090)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Sending: STATUS");
            out.println("STATUS");

            String response = in.readLine();
            System.out.println("Server Replied: " + response);

        } catch (IOException e) {
            System.err.println("Connection failed! Is the server running?");
        }
    }
}