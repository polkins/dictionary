package com.example.dictionary.socket;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class ServerImpl implements Server {
    public void start(int port, String message) {
        System.out.println("server starts");
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            writer.println(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server is closed");
    }
}
