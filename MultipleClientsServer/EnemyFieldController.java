
public class EnemyFieldController implements Runnable {
    private Client client;
    private Thread thread;
    private Viewer viewer;
    private Canvas canvas;

    public EnemyFieldController(Client client, Viewer viewer) {
        this.thread = new Thread(this);
        this.client = client;
        this.viewer = viewer;
        canvas = viewer.getEnemyCanvas();
    }
    @Override
    public void run() {
        System.out.println("in EnemyFieldController thread");
        while(true) {
             System.out.println(client.getDataFromServer());



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
