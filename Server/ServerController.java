import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ExecutionException;

public class ServerController {
    private Service service;
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel notAloneClient;
    private int notAloneClientIndex;

    private ScheduledExecutorService timerExecutor;

    public ServerController() {
        service = new Service();
        timerExecutor = Executors.newSingleThreadScheduledExecutor();
        int activePort = 4444;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(activePort));
            System.out.println("Server is running on port " + activePort);
        } catch (IOException ioe) {
            System.out.println("Error while trying to connect on port " + activePort + ioe);
        }
    }

    public  void resetNotAloneClient() {
        notAloneClient = null;
        notAloneClientIndex = -1;
    }

    public void launchServer() {
        while (true) {
            try {
                //  client acception
                SocketChannel client1Channel = serverSocketChannel.accept();
                int client1Index = SocketPool.addSocketChannel(client1Channel);

                if(client1Index != -1) {
                    System.out.println("Client1 connected from " + client1Channel.getRemoteAddress());
                    if(!acceptClient(client1Channel, client1Index)) {
                        disconnectClient(client1Channel, client1Index);
                    }

                }

            } catch(IOException ioe) {
                System.out.println("Error while trying to connect client " + ioe);
            }
        }
    }

    // return true if server Successfully accepted Client or false otherwise
    private boolean acceptClient(SocketChannel clientChannel, int clientChannelIndex) {

        // read info about how client wants to play(alone, with someone)
        String clientMessage = service.readData(clientChannel);
        System.out.println("ClientMessage = " + clientMessage);
        if(clientMessage != null) {
            //  to say client that serever read message
            service.sendData(clientChannel, "ok");
            if(clientMessage.equals("alone")) {
                Service newService = new Service(clientChannel, clientChannelIndex);
                newService.startService();
                System.out.println("Started new Thread for one player" );
                return true;

            } else if (clientMessage.equals("battle")) {
                // if there is a client who also wants to play with someone
                if(notAloneClient != null) {
                    resetTimer();
                    service.sendData(notAloneClient, "ENEMY_WAS_FOUND");
                    service.sendData(clientChannel, "ENEMY_WAS_FOUND");
                    ServiceForTwoPlayers serviceForTwoPlayers = new ServiceForTwoPlayers(clientChannel, notAloneClient, clientChannelIndex, notAloneClientIndex);
                    serviceForTwoPlayers.startService();
                    System.out.println("Started new Thread for two players" );
                    resetNotAloneClient();
                    return true;
                }
                notAloneClient = clientChannel;
                notAloneClientIndex = clientChannelIndex;
                setTimerForClientDisconnect(clientChannel, clientChannelIndex);
                return true;
            }
        }
        return false;
    }

    private void setTimerForClientDisconnect(SocketChannel clientChannel, int clientChannelIndex) {
        //  timer for 15 seconds
        System.out.println("Client Disconnect Timer launch");
        timerExecutor.schedule(new ClientDisconnectTask(clientChannel, clientChannelIndex, this, service), 20, TimeUnit.SECONDS);
    }

    private void resetTimer() {
        System.out.println("Reset timer");
        if (timerExecutor != null && !timerExecutor.isShutdown()) {
            timerExecutor.shutdownNow();
        }
        timerExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void disconnectClient(SocketChannel clientChannel, int clientIndex) {
        SocketPool.removeSocketAt(clientIndex);
        try {
            clientChannel.close();
            System.out.println("Something went wrong on client >>> Disconnecting client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
