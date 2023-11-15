
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
        while(true) { //while enemy is playing
            // getting information about enemies actions
            String enemyAction = client.getDataFromServer();
            System.out.println(enemyAction);
            handleServerResponse(enemyAction);

        }
    }

    public void go() {
        thread.start();
    }
    // checking if the enemyAction string contains only one command if not split it on several commands and invoke method
    // move on model one by one
    private void handleServerResponse(String enemyAction) {// it will be more productive to use StringBuilder
        for(int i = 1; i < enemyAction.length(); i++) {
            if(Character.isUpperCase(enemyAction.charAt(i))) {
                String singleAction = enemyAction.substring(0, i);
                enemyAction = enemyAction.substring(i);
                i = 0;
                // send data about action to enemyModel
                System.out.println(singleAction);
                enemyModel.move(singleAction);
            }
        }
        // send last command
        System.out.println(enemyAction);
        enemyModel.move(enemyAction);
    }
}
