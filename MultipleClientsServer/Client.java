import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int  SERVER_PORT = 4444;
    private  SocketChannel SOCKET_CHANNEL;
    private ByteBuffer buffer;


    public Client() {
        buffer = ByteBuffer.allocate(1024);
        try {
            SOCKET_CHANNEL = SocketChannel.open();
            SOCKET_CHANNEL.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            System.out.println("Successfully connected to server ...");
        } catch (IOException e) {
            System.out.println("ERROR occurred while trying to make connection");
        }
    }

    public void closeClient() {
        if (SOCKET_CHANNEL != null && !SOCKET_CHANNEL.isOpen()) {
            try {
                SOCKET_CHANNEL.close();
                System.out.println("Close client Socket");
            } catch (IOException exc) {
                System.out.println(exc);
            }

        }
    }
    public  String loadLevelFromServer(String level) {
        String levelContent = null;
        if(SOCKET_CHANNEL != null) {
            sendDataToServer(level);
            levelContent = getDataFromServer();
        }
        return levelContent;
    }

    public void sendDataToServer(String data) {
        while(true) {
            try {
                buffer.clear();
                buffer.put(data.getBytes());
                buffer.flip();

                SOCKET_CHANNEL.write(buffer);
                buffer.clear();
                System.out.println("Successfully send data to server [~]");
                break;

            } catch(IOException exc) {
                System.out.println("exception in method sendDataToServer " + exc);
                exc.printStackTrace();
                boolean reconnected = reconnect();

                if (reconnected) {
                    System.out.println("Successfully reconnected to the server.");
                } else {
                    break;
                }

            }
        }
    }

    public String getDataFromServer() {
        String data = null;
        while(true) {
            try {
                buffer.clear();
                int bytesRead = SOCKET_CHANNEL.read(buffer);
                if (bytesRead > 0) {
                    buffer.flip();
                     data = new String(buffer.array(), 0, bytesRead);
                }
                buffer.clear();
                System.out.println("Successfully get data from server [~]");
                break;

            } catch(IOException exc) {
                System.out.println("exception in method getDataFromServer " + exc);
                exc.printStackTrace();
                boolean reconnected = reconnect();

                if (reconnected) {
                    System.out.println("Successfully reconnected to the server.");
                } else {
                    break;
                }

            }
        }
        return data;
    }


    private boolean reconnect() {

        try {
            if (SOCKET_CHANNEL.isOpen()) {
                SOCKET_CHANNEL.close();
            }
            SOCKET_CHANNEL = SocketChannel.open();
            SOCKET_CHANNEL.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            return true;
        } catch (IOException e) {
            System.out.println("Failed to reconnect: " + e.getMessage());
            return false;
        }
    }

    // public static void main(String[] args) {
    //     Client client = new Client();
    //
    //     System.out.println(client.loadLevelFromServer("7"));
    //
    //     Scanner console = new Scanner(System.in);
    //     String myData = console.nextLine();
    //
    //     EnemyFieldController enemyField = new EnemyFieldController(client);
    //     enemyField.go();
    //
    //     while(!myData.equals("game over")) {
    //         // send data to server
    //         client.sendDataToServer(myData);
    //         myData = console.nextLine();
    //     }
    //     client.sendDataToServer(myData);
    //     client.closeClient();
    // }

}
