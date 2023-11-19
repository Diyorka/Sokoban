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
    private Client client;

    public Levels(Client client) {
        currentLevel = 1;
        this.client = client;
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
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 1, 3, 0, 5, 4, 0, 2},
        {2, 0, 0, 0, 3, 0, 5, 4, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
      };
  }

  private int[][] getSecondLevel() {
      return new int[][]
      {
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        {2, 0, 3, 4, 0, 0, 0, 0, 0, 2},
        {2, 4, 3, 5, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 1, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
        {2, 0, 0, 0, 0, 0, 5, 3, 4, 2},
        {2, 0, 0, 0, 0, 0, 4, 3, 0, 2},
        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
      };
  }

  private int[][] getThirdLevel() {
      return new int[][]
      {
          {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
          {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
          {2, 0, 0, 0, 1, 0, 0, 0, 0, 2},
          {2, 0, 0, 0, 0, 5, 2, 0, 0, 2},
          {2, 0, 2, 4, 3, 4, 0, 2, 0, 2},
          {2, 0, 0, 5, 3, 2, 0, 2, 0, 2},
          {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
          {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
          {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
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
      return getLevelFromServer("7");
  }

  private int[][] getEighthLevel() {
      return getLevelFromServer("8");
  }

  private int[][] getNinthLevel() {
      return getLevelFromServer("9");
  }

  public int[][] getLevelFromServer(String level) {
      String levelContent = client.loadLevelFromServer(level);
      System.out.println(levelContent);
      if(levelContent != null) {
        return parseData(levelContent, 'A');
      }
      return null;
  }

  public int[][] getRandomLevelFromServer() {
      String levelContent = client.loadRandomLevelFromServer();
      System.out.println(levelContent);
      if(levelContent != null) {
        return parseData(levelContent, 'A');
      }
      return null;
  }

  public int[][] getEnemyLevelFromServer() {
      String levelContent = client.loadEnemyLevelFromServer();
      System.out.println(levelContent);
      if(levelContent != null) {
        return parseData(levelContent, 'A');
      }
      return null;
  }

  private String loadLevel(String levelPath) {
      StringBuilder data = new StringBuilder();

      try {
          Path filePath = Paths.get(levelPath);

          List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
          String pattern = "[0-5]";
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

  private int[][] parseData(String data, char newLineSymbol) {

      String[] rows = data.split(String.valueOf(newLineSymbol));

      int rowCount = rows.length;
      int maxColumnCount = 0;

      for (String row : rows) {
          maxColumnCount = Math.max(maxColumnCount, row.length());
      }

      int[][] array = new int[rowCount][];

      for (int i = 0; i < rowCount; i++) {
          String row = rows[i];
          array[i] = new int[row.length()];
          for (int j = 0; j < row.length(); j++) {
              char symbol = row.charAt(j);
              int element = Character.getNumericValue(symbol);
              array[i][j] = element;
          }
      }
      return array;
  }
}
