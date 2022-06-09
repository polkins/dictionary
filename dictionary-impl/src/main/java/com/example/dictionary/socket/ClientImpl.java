package com.example.dictionary.socket;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

@Service
public class ClientImpl implements Client{
    public String start(int port) {
        System.out.println("client starts");
        try (Socket socket = new Socket(InetAddress.getLocalHost(), port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            var message = reader.readLine();
            System.out.println("message received: " + message);

            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
