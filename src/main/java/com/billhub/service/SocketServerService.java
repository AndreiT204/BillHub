package com.billhub.service;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class SocketServerService {

    private static final int PORT = 9090;

    @PostConstruct
    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("[Socket] Admin Server listening on port " + PORT);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }
            } catch (IOException e) {
                System.err.println("Could not start Socket Server: " + e.getMessage());
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String command = in.readLine();

            if ("STATUS".equalsIgnoreCase(command)) {
                out.println("OK: BillHub Server is RUNNING.");
            } else {
                out.println("ERROR: Unknown Command");
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}