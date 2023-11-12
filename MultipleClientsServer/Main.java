public class Main {
  public static void main(String args[]) {
    Client client = new Client();// connecting to server
    Viewer myField = new Viewer(client);



    // new thread for enemy field control
    EnemyFieldController enemyFieldController = new EnemyFieldController(client, myField);
    enemyFieldController.go();
  }
}
