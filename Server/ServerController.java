import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerController {
    private ServerSocketChannel serverSocketChannel;

    public ServerController() {
        int activePort = 4444;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(activePort));
            System.out.println("Server is running on port " + activePort);
        } catch (IOException ioe) {
            System.out.println("Error while trying to connect on port " + activePort + ioe);
        }
    }

    public void launchServer() {
        while (true) {
            try {
                // First gamer connection
                SocketChannel client1Channel = serverSocketChannel.accept();
                System.out.println("Client1 connected from " + client1Channel.getRemoteAddress());
                int client1Index = SocketPool.addSocketChannel(client1Channel);

                // Second gamer connection
                SocketChannel client2Channel = serverSocketChannel.accept();
                System.out.println("Client2 connected from " + client2Channel.getRemoteAddress());
                int client2Index = SocketPool.addSocketChannel(client2Channel);

                // If both clients have successfully connected
                if (client1Index != -1 && client2Index != -1) {
                    // Creating separate service for clients communication and separate thread
                    System.out.println("Creating new Thread ...");
                    Service service = new Service(client1Channel, client2Channel, client1Index, client2Index);
                    System.out.println("Indexes: " + client1Index + " " + client2Index);
                    service.startService();
                }


            } catch(IOException ioe) {
                System.out.println("Error while trying to connect client " + ioe);
            }
        }
    }


}
