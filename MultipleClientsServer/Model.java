 import java.util.Arrays;

public class Model implements SokobanModel {
  private Viewer viewer;
  private int[][] desktop;
  private int indexX;
  private int indexY;
  private int[][] arrayOfBoxes;
  private Levels repository;
  private boolean stateGame;
  ////
  private Client client;


  public Model(Viewer viewer, Client client) {
    this.viewer = viewer;
    this.client = client;
    repository = new Levels(client);

    initialization();
  }

  private void initialization() {
    stateGame = true;
    desktop = repository.nextLevel();
    int counterOne = 0;
    int counterThree = 0;
    int counterFour = 0;
    for(int i = 0; i < desktop.length; i++) {
      for(int j = 0; j < desktop[i].length; j++) {
        if(desktop[i][j] == 1) {
          counterOne = counterOne + 1;
          indexX = i;
          indexY = j;
        }
        if(desktop[i][j] == 3) {
          counterThree = counterThree + 1;
        }

        if(desktop[i][j] == 4) {
          counterFour = counterFour + 1;
        }
      }
    }


    if((counterOne != 1) || (counterThree == 0 && counterFour == 0) || (counterThree != counterFour)) {
      stateGame = false;
      desktop = null;
      return;
    }

    arrayOfBoxes = new int[2][counterFour];

    int column = 0;
    for(int i = 0; i < desktop.length; i++) {
      for(int j = 0; j < desktop[i].length; j++) {
        if(desktop[i][j] == 4) {
          arrayOfBoxes[0][column] = i;
          arrayOfBoxes[1][column] = j;
          column = column + 1;
        }
      }
    }
  }

  public void move(String direction) {
    if(direction.equals("Left")) {
      moveLeft();
    } else if(direction.equals("Up")) {
      moveUp();
    } else if(direction.equals("Right")) {
      moveRight();
    } else if(direction.equals("Down")) {
      moveDown();
    } else {
      return;
    }
    check();
    viewer.update();
    won();
  }

  private void won() {
    for(int a = 0; a < arrayOfBoxes[0].length; a++) {
      int i = arrayOfBoxes[0][a];
      int j = arrayOfBoxes[1][a];
      if(desktop[i][j] != 3) {
        return;
      }
    }

    javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), "You are won!!!");
    initialization();
    viewer.update();
  }

  private void check() {
    for(int a = 0; a < arrayOfBoxes[0].length; a++) {
      int i = arrayOfBoxes[0][a];
      int j = arrayOfBoxes[1][a];
      if(desktop[i][j] == 0) {
        desktop[i][j] = 4;
        break;
      }
    }
  }

  private void moveLeft() {
    if(desktop[indexX][indexY - 1] == 3) {
      if(desktop[indexX][indexY - 2] == 0 || desktop[indexX][indexY - 2] == 4) {
        desktop[indexX][indexY - 1] = 0;
        desktop[indexX][indexY - 2] = 3;
      }
    }

    if(desktop[indexX][indexY - 1] == 0 || desktop[indexX][indexY - 1] == 4) {
      desktop[indexX][indexY] = 0;
      indexY = indexY - 1;
      desktop[indexX][indexY] = 1;
    }
    /// send data about our movement on server
    client.sendDataToServer("Left");
  }

  private void moveUp() {
    if(desktop[indexX - 1][indexY] == 3) {
      if(desktop[indexX - 2][indexY] == 0 || desktop[indexX - 2][indexY] == 4) {
        desktop[indexX - 1][indexY] = 0;
        desktop[indexX - 2][indexY] = 3;
      }
    }

    if(desktop[indexX - 1][indexY] == 0 || desktop[indexX - 1][indexY] == 4) {
      desktop[indexX][indexY] = 0;
      indexX = indexX - 1;
      desktop[indexX][indexY] = 1;
    }
    client.sendDataToServer("Up");
  }

  private void moveRight() {

    if(desktop[indexX][indexY + 1] == 3) {
      if(desktop[indexX][indexY + 2] == 0 || desktop[indexX][indexY + 2] == 4) {
        desktop[indexX][indexY + 1] = 0;
        desktop[indexX][indexY + 2] = 3;
      }
    }

    if(desktop[indexX][indexY + 1] == 0 || desktop[indexX][indexY + 1] == 4) {
      desktop[indexX][indexY] = 0;
      indexY = indexY + 1;
      desktop[indexX][indexY] = 1;
    }
    client.sendDataToServer("Right");
  }

  private void moveDown() {
    if(desktop[indexX + 1][indexY] == 3) {
      if(desktop[indexX + 2][indexY] == 0 || desktop[indexX + 2][indexY] == 4 ) {
        desktop[indexX + 1][indexY] = 0;
        desktop[indexX + 2][indexY] = 3;
      }
    }

    if(desktop[indexX + 1][indexY] == 0 || desktop[indexX + 1][indexY] == 4) {
      desktop[indexX][indexY] = 0;
      indexX = indexX + 1;
      desktop[indexX][indexY] = 1;
    }
    client.sendDataToServer("Down");
  }

  public int[][] getDesktop() {
    return desktop;
  }

  public int[][] copyDesktop() {
      return Arrays.copyOf(desktop, desktop.length);
  }
  public boolean getState() {
    return stateGame;
  }
}
