import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;


public class Client {
    public static String loadLevelFromServer() {
        String serverAddress = "localhost";
        int serverPort = 4444;
        System.out.println("trying to connect to server on ip " + serverAddress + "  port  : " + serverPort );

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            
            socket.close();
        } catch (UnknownHostException uhe) {
            System.out.println("Error " + uhe);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(loadLevelFromServer());
    }
}
