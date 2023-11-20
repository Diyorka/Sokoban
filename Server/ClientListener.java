import java.nio.channels.SocketChannel;

public class ClientListener extends Thread {
    private SocketChannel player1Channel;
    private SocketChannel player2Channel;
    private ServiceForTwoPlayers service;
    private String clientNumber;

    public ClientListener(SocketChannel player1Channel, SocketChannel player2Channel, ServiceForTwoPlayers service, String clientNumber) {
        this.player1Channel = player1Channel;
        this.player2Channel = player2Channel;
        this.service = service;
        this.clientNumber = clientNumber;
    }

    public void run() {
        System.out.println("in ClientListener for client " + clientNumber);

        if(clientNumber.equals("firstClient")) {
            while(player1Channel.isOpen() && player2Channel.isOpen()) {

                if(!listenClient(player1Channel, player2Channel)) {
                    System.out.println("Something was wrong while Listening first client");
                    break;
                }
            }
            System.out.println("Closing thread ClientListener >>>");
        } else if (clientNumber.equals("secondClient")) {
            while(player1Channel.isOpen() && player2Channel.isOpen()) {

                if(!listenClient(player2Channel, player1Channel)) {
                    System.out.println("Something was wrong while Listening second client");
                    break;
                }

            }
            System.out.println("Closing thread ClientListener >>>");
        }
    }
    // return true if all operations were Successfully completed
    private boolean listenClient(SocketChannel socketToListen, SocketChannel socketToSendData) {
        String infoFromPlayer = service.readData(socketToListen);
        if(infoFromPlayer != null) {
            System.out.println("received info from  player  >>> " + infoFromPlayer);

            service.sendData(socketToSendData, infoFromPlayer);
            System.out.println("sent info to first player  >>> ");
            return true;
        }

        return false;
    }
}
