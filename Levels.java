import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Levels {
  private int currentLevel;

  public Levels() {
      currentLevel = 1;
  }

  public int getCurrentLevel() {
      return currentLevel;
  }

  public int[][] getNextMap() {
      currentLevel++;
      return getCurrentMap();
  }

  public int[][] getCurrentMap() {
      int[][] map = null;

      switch (currentLevel) {
        case 1:
            map = getFirstLevel();
            break;
        case 2:
            map = getSecondLevel();
            break;
        case 3:
            map = getThirdLevel();
            break;
        case 4:
            map = getFourthLevel();
            break;
        case 5:
            map = getFifthLevel();
            break;
        case 6:
            map = getSixthLevel();
            break;
        case 7:
            map = getSeventhLevel();
            break;
        case 8:
            map = getEighthLevel();
            break;
        case 9:
            map = getNinthLevel();
            break;
        default:
            map = getFirstLevel();
            currentLevel = 1;
      }

      return map;
  }

  public void setCurrentLevel(int level) {
      currentLevel = level;
  }

  private int[][] getFirstLevel() {
      return new int[][]
      {
              {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
              {2, 0, 0, 0, 0, 0, 1, 0, 0, 2},
              {2, 0, 5, 0, 0, 0, 3, 4, 0, 2},
              {2, 0, 2, 0, 0, 0, 0, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 3, 0, 0, 2},
              {2, 0, 4, 0, 0, 5, 0, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
              {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
      };
  }

  private int[][] getSecondLevel() {
      return new int[][]
      {
              {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
              {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
              {2, 0, 2, 0, 0, 5, 0, 0, 0, 2},
              {2, 0, 2, 0, 2, 0, 0, 0, 0, 2},
              {2, 0, 0, 0, 2, 0, 0, 0, 0, 2},
              {2, 0, 5, 4, 2, 0, 0, 0, 0, 2},
              {2, 0, 0, 0, 2, 0, 1, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 0, 3, 0, 2},
              {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
              {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
      };
  }

  private int[][] getThirdLevel() {
      return new int[][]
      {
              {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
              {2, 0, 0, 0, 0, 0, 1, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 3, 0, 0, 2},
              {2, 0, 0, 5, 0, 0, 4, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
              {2, 0, 0, 0, 0, 0, 0, 5, 0, 2},
              {2, 0, 0, 5, 0, 0, 3, 0, 0, 2},
              {2, 0, 4, 0, 0, 0, 0, 0, 0, 2},
              {2, 0, 3, 4, 0, 0, 0, 0, 0, 2},
              {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
      };
  }

  private int[][] getFourthLevel() {
      String levelPath = "levels/level4.sok";
      String data = loadLevel(levelPath);
      int[][] map = parseData(data, '\n');
      return map;
  }

  private int[][] getFifthLevel() {
      String levelPath = "levels/level5.sok";
      String data = loadLevel(levelPath);
      int[][] map = parseData(data, '\n');
      return map;
  }

  private int[][] getSixthLevel() {
      String levelPath = "levels/level6.sok";
      String data = loadLevel(levelPath);
      int[][] map = parseData(data, '\n');
      return map;
  }

  private int[][] getSeventhLevel() {
      return null;
  }

  private int[][] getEighthLevel() {
      return null;
  }

  private int[][] getNinthLevel() {
      return null;
  }

  private String loadLevel(String levelPath) {
      StringBuilder data = new StringBuilder();

      try {
          Path filePath = Paths.get(levelPath);

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
                  data.append('\n');
              }
          }
          System.out.println(data.toString());
          return data.toString();

      } catch (IOException ioe) {
          System.out.println("Error " + ioe);
      }

      return data.toString();
  }

//TODO UPDATE PARSE DATA
private int[][] parseData(String data, char newLineSymbol) {
   String[] rows = data.split(String.valueOf(newLineSymbol));

   int rowCount = rows.length;
   int maxColumnCount = 0;

   for (String row : rows) {
       maxColumnCount = Math.max(maxColumnCount, row.length());
   }

   int[][] array = new int[rowCount][maxColumnCount];

   for (int i = 0; i < rowCount; i++) {
       String row = rows[i];
       for (int j = 0; j < row.length(); j++) {
           char symbol = row.charAt(j);
           int element = Character.getNumericValue(symbol);
           array[i][j] = element;
       }
   }

   return array;
}

  //
    //
    // // TODO Code for Backend Server parser
    // import java.nio.charset.StandardCharsets;
    // import java.nio.file.Path;
    // import java.nio.file.Paths;
    // import java.nio.file.Files;
    // import java.util.List;
    // import java.util.regex.Matcher;
    // import java.util.regex.Pattern;
    // private String loadLevel(String levelFileName) {
    //     StringBuilder data = new StringBuilder();
    //
    //     try {
    //         Path filePath = Paths.get(levelFileName);
    //
    //         List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
    //         String pattern = "[0-4]";
    //         Pattern compiledPattern = Pattern.compile(pattern);
    //         Matcher matcher = null;
    //
    //         for (String line : lines) {
    //             matcher = compiledPattern.matcher(line);
    //             if(matcher.find()) {
    //                 data.append(matcher.group());
    //                 while(matcher.find()){
    //                     data.append(matcher.group());
    //                 }
    //             data.append('A');
    //         }
    //         }
    //         System.out.println(data.toString());
    //         return data.toString();
    //
    //     } catch (IOException ioe) {
    //         System.out.println("Error " + ioe);
    //     }
    //
    //
    //     return data.toString();
    // }
    //
    //
    // //Verion 2
    // import java.io.RandomAccessFile;
    // import java.nio.ByteBuffer;
    // import java.nio.channels.FileChannel;
    // private String loadLevel(String levelFileName) {
    //     StringBuilder data = new StringBuilder();
    //     RandomAccessFile file = null;
    //     FileChannel channel = null;
    //
    //     try {
    //         file = new RandomAccessFile(levelFileName, "r");
    //         channel = file.getChannel();
    //
    //         ByteBuffer buffer = ByteBuffer.allocate(1024);
    //
    //         while (channel.read(buffer) != -1) {
    //             buffer.flip();
    //
    //             while (buffer.hasRemaining()) {
    //                 char symbol = (char) buffer.get();
    //                 if ('0' <= symbol && symbol <= '4') {
    //                     data.append(symbol);
    //                 }
    //
    //                 if (symbol == '\n') {
    //                     data.append('A');
    //                 }
    //             }
    //
    //             buffer.clear();
    //         }
    //
    //     } catch (IOException ioe) {
    //         System.out.println("Error " + ioe);
    //     } finally {
    //         try {
    //             if (channel != null) {
    //                 channel.close();
    //             }
    //             if (file != null) {
    //                 file.close();
    //             }
    //         } catch (IOException e) {
    //             System.out.println("Error closing resources: " + e);
    //         }
    //     }
    //
    //     return data.toString();
    // }
    //
    // public String loadLevelFromServer() {
    //
    // }
}
