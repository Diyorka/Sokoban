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

        // if(clientNumber.equals("firstClient")) {
        //     while(player1Channel.isOpen() && player2Channel.isOpen()) {
        //
        //         String infoFromPlayer1 = service.readData(player1Channel);
        //         System.out.println("received info from first player  >>> " + infoFromPlayer1);
        //
        //         service.sendData(player2Channel, infoFromPlayer1);
        //         System.out.println("sent info to second player  >>> ");
        //     }
        // } else if (clientNumber.equals("secondClient")) {
        //     while(player1Channel.isOpen() && player2Channel.isOpen()) {
        //
        //         String infoFromPlayer2 = service.readData(player2Channel);
        //         System.out.println("received info from second player  >>> " + infoFromPlayer2);
        //
        //         service.sendData(player1Channel, infoFromPlayer2);
        //         System.out.println("sent info to first player  >>> ");
        //     }
        // }



    }
}
