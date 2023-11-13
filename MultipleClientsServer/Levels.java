import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class Levels {

  private int level;
  private Client client;

  public Levels(Client client) {
    level = 1;
    this.client = client;
  }

  public int[][] nextLevel() {
    int[][] desktop = null;

    switch (level) {
      case 1:
      desktop = firstLevel();
      break;
      case 2:
      desktop = secondLevel();
      break;
      case 3:
      desktop = thirdLevel();
      break;
      case 4:
      desktop = fourthLevel();
      break;
      case 5:
      desktop = fifthLevel();
      break;
      case 6:
      desktop = sixthLevel();
      break;
      case 7:
      desktop = seventhLevel();
      break;
      case 8:
      desktop = eigththLevel();
      break;
      case 9:
      desktop = ninethLevel();
      break;
      default:
      desktop = firstLevel();
      level = 1;
    }

    level = level + 1;
    return desktop;
  }

  private int[][] firstLevel() {
    // return new int[][] {
    //   {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
    //   {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
    //   {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
    //   {2, 0, 0, 1, 3, 0, 0, 4, 0, 2},
    //   {2, 0, 0, 0, 3, 0, 0, 4, 0, 2},
    //   {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
    //   {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
    //   {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
    //   {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
    //   {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    // };
    String answer =  client.loadLevelFromServer("7");
    System.out.println(answer);
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }


  private int[][] secondLevel() {
    // return new int[][] {
    //   {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
    //   {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
    //   {2, 0, 0, 1, 3, 0, 0, 4, 0, 2},
    //   {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    // };
    String answer =  client.loadLevelFromServer("7");
    System.out.println(answer);
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }

  private int[][] thirdLevel() {
    // return new int[][] {
    //   {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
    //   {2, 4, 0, 0, 3, 0, 0, 1, 0, 2},
    //   {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    // };
    String answer =  client.loadLevelFromServer("7");
    System.out.println(answer);
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }

  private int[][] fourthLevel() {
    // String levelName = "levels/level4.sok";
    // String answer = loadLevel(levelName);
    // int[][] desktop = parseData(answer);
    // return desktop;
    String answer =  client.loadLevelFromServer("7");
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }

  private int[][] fifthLevel() {
    // String levelName = "levels/level5.sok";
    // String answer = loadLevel(levelName);
    // int[][] desktop = parseData(answer);
    // return desktop;
    String answer =  client.loadLevelFromServer("7");
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }

  private int[][] sixthLevel() {
    // String levelName = "levels/level6.sok";
    // String answer = loadLevel(levelName);
    // int[][] desktop = parseData(answer);
    // return desktop;
    String answer =  client.loadLevelFromServer("7");
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }

  private  int[][] parseData(String data) {

    int[][] array = null;

    int row = 0;
    for(int i = 0; i < data.length(); i++) {
      char symbol = data.charAt(i);
      if(symbol == '\n') {
        row = row + 1;
      }
    }

    array = new int[row][3];
    int column = 0;
    row = 0;
    for(int i = 0; i < data.length(); i++) {
      char symbol = data.charAt(i);
      if(symbol == '\n') {
        array[row] = new int[column];
        row = row + 1;
        column = 0;
        continue;
      }
      column = column + 1;
    }

    column = 0;
    row = 0;
    for(int i = 0; i < data.length(); i++) {
      char symbol = data.charAt(i);
      if(symbol == '\n') {
        row = row + 1;
        column = 0;
        continue;
      }
      int element = Integer.parseInt("" + symbol);
      array[row][column] = element;

      column = column + 1;
    }


    return array;
  }

  private String loadLevel(String levelFileName) {

    String data = "";

    FileInputStream in = null;
    try {
      in = new FileInputStream(levelFileName);
      int unicode;
      while ((unicode = in.read()) != -1) {
        char symbol = (char)unicode;
        if(('0' <= symbol && symbol <= '4') || (symbol == '\n')) {
          data = data + symbol;
        }
      }
    } catch (IOException ioe) {
      System.out.println("Error " + ioe);
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (IOException ioe) {
        System.out.println("Error " + ioe);
      }
    }
    return data;
  }



  private int[][] seventhLevel() {
    String answer =  client.loadLevelFromServer("7");
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }

  private int[][] eigththLevel() {
    String answer =  client.loadLevelFromServer("8");
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }


  private int[][] ninethLevel() {
    String answer = client.loadLevelFromServer("9");
    int[][] desktop = parseData(answer, 'A');
    return desktop;
  }


  public static int[][] parseData(String data, char newLineSymbol) {

    int[][] array = null;

    int row = 0;
    for(int i = 0; i < data.length(); i++) {
      char symbol = data.charAt(i);
      if(symbol == newLineSymbol) {
        row = row + 1;
      }
    }

    array = new int[row][3];
    int column = 0;
    row = 0;
    for(int i = 0; i < data.length(); i++) {
      char symbol = data.charAt(i);
      if(symbol == newLineSymbol) {
        array[row] = new int[column];
        row = row + 1;
        column = 0;
        continue;
      }
      column = column + 1;
    }

    column = 0;
    row = 0;
    for(int i = 0; i < data.length(); i++) {
      char symbol = data.charAt(i);
      if(symbol == newLineSymbol) {
        row = row + 1;
        column = 0;
        continue;
      }
      int element = Integer.parseInt("" + symbol);
      array[row][column] = element;

      column = column + 1;
    }
    return array;
  }


}
