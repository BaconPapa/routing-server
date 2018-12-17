import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        try{
            DatagramSocket socket = new DatagramSocket(0);
            System.out.printf("UDP Client run on %s:%d", socket.getLocalAddress(), socket.getPort());
            InetAddress serverAddress = InetAddress.getByName("localhost");
            byte[] content = "hello".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(content,content.length, serverAddress, Config.UDP_PORT);
            socket.send(sendPacket);
            socket.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
