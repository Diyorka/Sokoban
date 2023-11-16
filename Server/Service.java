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

public class Service implements Runnable{

    private Thread thread;
    private SocketChannel playerChannel;
    private int playerIndex;
    private boolean gameIsRunning;
    private static final String LAST_LEVEL = "9";

    public Service(SocketChannel playerChannel,int playerIndex) {
        thread = new Thread(this);
        this.playerChannel = playerChannel;
        this.playerIndex = playerIndex;
        gameIsRunning = true;
    }
    public Service() {
        
    }

    @Override
    public void run() {
        System.out.println("Game started");
        String levelNumber = "";
        while(gameIsRunning && playerChannel.isOpen()) {
            sendLevelToClient();
        }
        closeConnection();
        System.out.println("Game over");
    }

    public void startService() {
        thread.start();
    }


   public static String readData(SocketChannel channel) {
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
           return null;
       } catch (IOException exception) {
           System.out.println("exception while readData from client" + exception);
           exception.printStackTrace();
           return null;
       }
        return null;
   }

   public  void sendData(SocketChannel channel, String data) {
       try {
           if (data != null) {
               ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
               channel.write(buffer);
               System.out.println("sent data to client");
           }
       }  catch (SocketException socketExc) {
           System.out.println("exception while sendData from client" + socketExc);
           socketExc.printStackTrace();
           gameIsRunning = false;
       } catch (IOException exception) {
           System.out.println("exception while sendData from client" + exception);
           exception.printStackTrace();

       }

   }

   private void sendLevelToClient() {
       // getting level number from client
       String levelNumber = readData(playerChannel);
       if(levelNumber != null) {
           if(levelNumber.equals(LAST_LEVEL)) {
               gameIsRunning = false;
           }
           System.out.println("Successfully get level from client >>> " + levelNumber);
           String levelContent = loadLevel(Integer.parseInt(levelNumber));
           if(levelContent != null) {
                sendData(playerChannel, levelContent);
                return;
           }
       }
       gameIsRunning = false;
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

    private void closeConnection() {
        SocketPool.removeSocketAt(playerIndex);
        if(playerChannel.isOpen()) {
            try {
                playerChannel.close();
            } catch (IOException exc) {
                System.out.println(exc);
            }
        }
        System.out.println(playerChannel.isOpen());

    }

}
