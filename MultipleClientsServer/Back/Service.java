import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.ByteBuffer;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Service implements Runnable{

    private Thread thread;
    private SocketChannel player1Channel;
    private SocketChannel player2Channel;
    private int player1Index;
    private int player2Index;

    public Service(SocketChannel player1Channel, SocketChannel player2Channel, int player1Index, int player2Index) {
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

       try {
          //getting level from clients
           // String currentLevel = readData(player1Channel);
           // System.out.println("received level number from client 1 >>> " + currentLevel);
           // currentLevel = readData(player2Channel);
           // System.out.println("received level number from client 2 >>> " + currentLevel);
           //
           // System.out.println("received level number from clients >>> " + currentLevel);
           // sendLevelToClients(Integer.parseInt(currentLevel));

           String player1Level = readData(player1Channel);
           System.out.println("received level number from client 1 >>> " + player1Level);
           String player1LevelContent = loadLevel(Integer.parseInt(player1Level));
           sendData(player1Channel, player1LevelContent);

           String player2Level = readData(player2Channel);
           System.out.println("received level number from client 2 >>> " + player2Level);
           String player2LevelContent = loadLevel(Integer.parseInt(player2Level));
           sendData(player2Channel, player2LevelContent);

           /// send levels of enemies
            sendData(player1Channel, player2LevelContent);
             System.out.println("send level of enemy  ");
            sendData(player2Channel, player1LevelContent);
             System.out.println("send level of enemy " );

          // change data
           while (player1Channel.isOpen() && player2Channel.isOpen()) {
               System.out.println("!player1Channel.isOpen() && !player2Channel.isOpen()");
               String infoFromPlayer1 = readData(player1Channel);
               System.out.println("received info from first player  >>> " + infoFromPlayer1);
               String infoFromPlayer2 = readData(player2Channel);
               System.out.println("received info from second player  >>> " + infoFromPlayer2);

               sendData(player1Channel, infoFromPlayer2);
               System.out.println("sent info to first player  >>> ");

               sendData(player2Channel, infoFromPlayer1);
               System.out.println("sent info to second player  >>> ");
           }
       } catch (IOException exception) {
           System.out.println("exception " + exception);
           exception.printStackTrace();
       }
   }

   private void sendLevelToClients(int level) throws IOException {
       String levelContent = loadLevel(level);
       sendData(player1Channel, levelContent);
       sendData(player2Channel, levelContent);
   }


   private String readData(SocketChannel channel) throws IOException {
       ByteBuffer buffer = ByteBuffer.allocate(1024);
       int bytesRead = channel.read(buffer);
       if (bytesRead > 0) {
           buffer.flip();
           byte[] data = new byte[buffer.remaining()];
           buffer.get(data);
           return new String(data);
       }
       return null;
   }

   private void sendData(SocketChannel channel, String data) throws IOException {
       if (data != null) {
           ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
           channel.write(buffer);
           System.out.println("sent data to client");
       }
   }


    //load level from file on server with parsing
    private String loadLevel(int level) {
        if(level <= 9 && level >= 7) {
            String levelFileName = "Levels/level" + level + ".sok";
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





}
