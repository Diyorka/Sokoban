import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ClientDisconnectTask extends Thread{
    private final SocketChannel CLIENT_CHANNEL;
    private final int CLIENT_CHANNEL_INDEX;
    private ServerController controller;
    private Service service;

    public ClientDisconnectTask(SocketChannel clientChannel, int clientChannelIndex, ServerController controller, Service service) {
        this.CLIENT_CHANNEL = clientChannel;
        this.CLIENT_CHANNEL_INDEX = clientChannelIndex;
        this.controller = controller;
        this.service = service;
    }

    @Override
    public void run() {
        System.out.println("in new Thread ClientDisconnectTask");
        service.sendData(CLIENT_CHANNEL, "ENEMY_WAS_NOT_FOUND");
        controller.disconnectClient(CLIENT_CHANNEL, CLIENT_CHANNEL_INDEX);
        controller.resetNotAloneClient();
        System.out.println("Client " + CLIENT_CHANNEL_INDEX + " disconnected due to timeout");
    }
}
