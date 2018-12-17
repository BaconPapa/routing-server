package com.advanced_network;

public class Main {

    public static void main(String[] args) {
        TCPServer server = new TCPServer(Config.UDP_PORT, Config.TCP_PORT);
        server.run();
    }
}
