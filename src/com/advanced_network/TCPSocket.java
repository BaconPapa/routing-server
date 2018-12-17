package com.advanced_network;

import java.net.Socket;

public class TCPSocket {
    public Socket socket;
    public TCPSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean enable() {
        return this.socket.getRemoteSocketAddress() != null;
    }
}
