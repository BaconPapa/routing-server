package com.advanced_network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    private DatagramSocket server;

    public UDPServer(int port) throws SocketException {
        this.server = new DatagramSocket(port);
    }

    public DatagramPacket receivedPacket() throws IOException {
        DatagramPacket receivedPacket = new DatagramPacket(new byte[Config.PACKET_SIZE], Config.PACKET_SIZE);
        this.server.receive(receivedPacket);
        return receivedPacket;
    }

    public InetAddress getAddress() {
        return this.server.getLocalAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }
}
