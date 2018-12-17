package com.advanced_network;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class TCPServer {
    private UDPServer udpServer;
    private ServerSocket tcpSocket;
    private List<TCPSocket> clientSocket;
    private boolean acceptable;
    private boolean sendable;

    TCPServer(int udpPort, int tcpPort){
        try{
            this.udpServer = new UDPServer(udpPort);
            this.tcpSocket = new ServerSocket(tcpPort);
            this.clientSocket = Collections.synchronizedList(new LinkedList<>());
            this.acceptable = true;
            this.sendable = true;
            System.out.printf(
                    "Server start to run\tUDP %s:%d\tTCP %s:%d\n",
                    this.udpServer.getAddress(),
                    this.udpServer.getPort(),
                    this.tcpSocket.getLocalSocketAddress(),
                    this.tcpSocket.getLocalPort()
            );
        } catch (IOException e) {
            System.out.printf("Server init error: %s\n", e.getMessage());
            System.exit(0);
        }
    }
    void run() {
        Thread t = new Thread(this::accept);
        t.start();
        while(sendable) {
            try{
                DatagramPacket datagramPacket = udpServer.receivedPacket();
                String received = new String(datagramPacket.getData(), Config.CHAR_SET).trim();
                System.out.printf(
                        "Receive packet from %s:%d\tcontent:%s\n",
                        datagramPacket.getAddress(),
                        datagramPacket.getPort(),
                        received
                );
                BroadCastMessage(received);
            } catch (IOException e) {
                System.out.printf("packet receive error: %s\n", e.getMessage());
            }
        }
    }

    private void accept() {
        while(acceptable) {
            try{
                Socket socket = tcpSocket.accept();
                System.out.printf(
                        "accept connection from %s:%d\n",
                        socket.getInetAddress(),
                        socket.getPort());
                this.clientSocket.add(new TCPSocket(socket));
            }catch (IOException e) {
                System.out.printf("link error: %s\n", e.getMessage());
            }
        }
    }

    private void BroadCastMessage(String message) {
        List<TCPSocket> updatedSocket = new LinkedList<>();
        for (TCPSocket socket :
                this.clientSocket) {
           if (socket.enable()) {
               try{
                   OutputStream outputStream;
                   PrintWriter printWriter;
                   outputStream = socket.socket.getOutputStream();
                   printWriter = new PrintWriter(outputStream);
                   printWriter.println(message);
                   printWriter.flush();
                   System.out.printf("Send packet to %s:%d\n", socket.socket.getInetAddress(), socket.socket.getPort());
                   updatedSocket.add(socket);
               } catch (IOException e) {
                   System.out.println(e.getMessage());
               }
           }
        }
        this.clientSocket = updatedSocket;
    }
}
