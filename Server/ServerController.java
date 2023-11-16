import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerController {
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel notAloneClient;
    private int notAloneClientIndex;

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

                //  client acception
                SocketChannel client1Channel = serverSocketChannel.accept();
                int client1Index = SocketPool.addSocketChannel(client1Channel);

                if(client1Index != -1) {
                    System.out.println("Client1 connected from " + client1Channel.getRemoteAddress());
                    acceptClient(client1Channel, client1Index);
                }




            } catch(IOException ioe) {
                System.out.println("Error while trying to connect client " + ioe);
            }
        }
    }

    private void acceptClient(SocketChannel clientChannel, int clientChannelIndex) {
        // read info about how client wants to play(alone, with someone)
        String clientMessage = Service.readData(clientChannel);
        System.out.println("ClientMessage = " + clientMessage);
        if(clientMessage.equals("alone")) {
            Service service = new Service(clientChannel, clientChannelIndex);
            service.startService();
            System.out.println("Started new Thread for one player" );
            return;

        } else if (clientMessage.equals("battle")) {
            // if there is a client who also wants to play with someone
            if(notAloneClient != null) {
                ServiceForTwoPlayers serviceForTwoPlayers = new ServiceForTwoPlayers(clientChannel, notAloneClient, clientChannelIndex, notAloneClientIndex);
                serviceForTwoPlayers.startService();
                System.out.println("Started new Thread for two players" );
                notAloneClient = null;
                notAloneClientIndex = -1;
                return;
            }
            notAloneClient = clientChannel;
            notAloneClientIndex = clientChannelIndex;

        }
    }


}
