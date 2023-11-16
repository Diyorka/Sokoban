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
      return null;
  }

  private int[][] getFifthLevel() {
      return null;
  }

  private int[][] getSixthLevel() {
      return null;
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


}
