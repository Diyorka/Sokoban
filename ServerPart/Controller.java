import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;


public class Controller {
    private ServerSocket serverSocket;

    public Controller() {
        int activePort = 4444;
        try {
            serverSocket = new ServerSocket(activePort);
            System.out.println("Server is running on port " + activePort);
        } catch(IOException ioe) {
            System.out.println("Error while trying to connect on port  " + activePort + ioe);
        }
    }

    public void launchServer() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
            } catch(IOException ioe) {
                System.out.println("Error while trying to connect client " + ioe);
            }
        }
    }

}
