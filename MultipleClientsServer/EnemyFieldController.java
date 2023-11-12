
public class EnemyFieldController implements Runnable {
    private Client client;
    private Thread thread;
    private Viewer viewer;
    private Canvas canvas;
    private SokobanModel enemyModel;

    public EnemyFieldController(Client client, Viewer viewer) {
        this.thread = new Thread(this);
        this.client = client;
        this.viewer = viewer;
        canvas = viewer.getEnemyCanvas();
        enemyModel = canvas.getModel();
    }
    @Override
    public void run() {
        System.out.println("in EnemyFieldController thread");
        while(true) {
            // getting information about enemies actions
            String enemyAction = client.getDataFromServer();
            System.out.println(enemyAction);
            // send data about action to enemyModel
            enemyModel.move(enemyAction);



            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
        }
    }

    public void go() {
        thread.start();
    }
}
