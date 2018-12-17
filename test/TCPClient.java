import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost", Config.TCP_PORT);
            System.out.printf("TCP client run at %s:%d\n", socket.getLocalAddress(), socket.getLocalPort());
            boolean recievable = true;
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String info = bufferedReader.readLine();
            System.out.printf("Get Message: %s\n", info);
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
