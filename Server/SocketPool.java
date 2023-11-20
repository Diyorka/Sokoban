// import java.net.Socket;
import java.nio.channels.SocketChannel;

public class SocketPool {
    private static final int POOL_SIZE = 4;
    private static final SocketChannel[] SOCKET_POOL = new SocketChannel[POOL_SIZE];

    public static int addSocketChannel(SocketChannel socketChannel) {
        for(int i = 0; i < POOL_SIZE; i++) {
            if(SOCKET_POOL[i] == null) {
                SOCKET_POOL[i] = socketChannel;
                return i;
            }
        }

        return -1;
    }

    public static int removeSocketAt(int socketIndex) {
        if(socketIndex >= POOL_SIZE || socketIndex < 0) {
            return -1;
        }
        SOCKET_POOL[socketIndex] = null;
        return 0;
    }
}
