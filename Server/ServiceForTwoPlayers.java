import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.ByteBuffer;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.Random;

public class ServiceForTwoPlayers implements Runnable{

    private Thread thread;
    private SocketChannel player1Channel;
    private SocketChannel player2Channel;
    private int player1Index;
    private int player2Index;

    public ServiceForTwoPlayers(SocketChannel player1Channel, SocketChannel player2Channel, int player1Index, int player2Index) {
        thread = new Thread(this);
        this.player1Channel = player1Channel;
        this.player2Channel = player2Channel;
        this.player1Index = player1Index;
        this.player2Index = player2Index;
    }

    @Override
    public void run() {
        System.out.println("Game started");
        startSession();
        // always read data from first client and send them to seconds client EnemyFieldController
        ClientListener firstClientListener = new ClientListener(player1Channel, player2Channel, this, "firstClient");
        firstClientListener.start();

        // always read data from second client and send them to first  client EnemyFieldController
        ClientListener secondClientListener = new ClientListener(player1Channel, player2Channel, this, "secondClient");
        secondClientListener.start();

        // wait other threads !!!
        try {
            firstClientListener.join();
            secondClientListener.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SocketPool.removeSocketAt(player1Index);
        SocketPool.removeSocketAt(player2Index);
        System.out.println(player1Channel.isOpen() + " " + player2Channel.isOpen());
        System.out.println("Game over");

    }

    public void startService() {
        thread.start();
    }



    public void startSession() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int generatedLevel = generateRandomLevel();
        System.out.println("Generated level = " + generatedLevel);
        // String levelContent = loadLevel(generatedLevel);
        String levelContent = loadLevel(10);
        sendData(player1Channel, levelContent);
        sendData(player2Channel, levelContent);

        /// send levels of enemies
        sendData(player1Channel, levelContent);
        System.out.println("send level of enemy  ");
        sendData(player2Channel, levelContent);
        System.out.println("send level of enemy " );

        try {
            Thread.sleep(50);
        } catch (InterruptedException ie) {
            System.out.println("ServiceForTwoPlayers.java Thread sleep ex " + ie);
        }

        String player1NickName = readData(player1Channel);
        System.out.println("read nickname " + player1NickName);
        String player2NickName = readData(player2Channel);
        System.out.println("read nickname " + player1NickName);

        sendData(player1Channel, player2NickName);
        sendData(player2Channel, player1NickName);

   }

   public  String readData(SocketChannel channel) {
       try {
           ByteBuffer buffer = ByteBuffer.allocate(1024);
           int bytesRead = channel.read(buffer);
           if (bytesRead > 0) {
               buffer.flip();
               byte[] data = new byte[buffer.remaining()];
               buffer.get(data);
               return new String(data);
           }
       } catch (SocketException socketExc) {
           System.out.println("exception while readData from client" + socketExc);
           socketExc.printStackTrace();
           closeConnection();
           return null;
       } catch (IOException exception) {
           System.out.println("exception while readData from client" + exception);
           exception.printStackTrace();
           return null;
       }
        return null;
   }

   public void sendData(SocketChannel channel, String data) {
       try {
           if (data != null) {
               ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
               channel.write(buffer);
               System.out.println("sent data to client");
           }
       } catch (SocketException socketExc) {
           System.out.println("exception while readData from client" + socketExc);
           socketExc.printStackTrace();
           closeConnection();

       } catch (IOException exception) {
           System.out.println("exception while readData from client" + exception);
           exception.printStackTrace();

       }

   }

   private int generateRandomLevel() {
       System.out.println("Generating level ...");
       Random random = new Random();
       return random.nextInt(5) + 10; // from 7 to 9
   }
    //load level from file on server with parsing
    private String loadLevel(int level) {
        if(level <= 15 && level >= 10) {
            String levelFileName = "Levels/Level" + level + ".sok";
            StringBuilder data = new StringBuilder();

            try {
                Path filePath = Paths.get(levelFileName);

                List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
                String pattern = "[0-4]";
                Pattern compiledPattern = Pattern.compile(pattern);
                Matcher matcher = null;

                for (String line : lines) {
                    matcher = compiledPattern.matcher(line);
                    if(matcher.find()) {
                        data.append(matcher.group());
                        while(matcher.find()){
                            data.append(matcher.group());
                        }
                    data.append('A');
                }
                }
                System.out.println(data.toString());
                return data.toString();

            } catch (IOException ioe) {
                System.out.println("Error " + ioe);
            }


            return data.toString();
        }
        return null;
    }

    private void closeConnection() {
        SocketPool.removeSocketAt(player1Index);
        SocketPool.removeSocketAt(player2Index);
        closeChannel(player1Channel);
        closeChannel(player2Channel);

        System.out.println("Closing connection  player1Channel.isOpen() = "+ player1Channel.isOpen());
        System.out.println("player2Channel.isOpen() = "+ player2Channel.isOpen());

    }
    private void closeChannel(SocketChannel playerChannel) {
        if(playerChannel.isOpen()) {
            try {
                playerChannel.close();
            } catch (IOException exc) {
                System.out.println(exc);

            }
        }
    }
}
