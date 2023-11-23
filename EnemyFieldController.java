public class EnemyFieldController implements Runnable {
    private Client client;
    private Thread thread;
    private Viewer viewer;
    private CanvasForTwoPlayers canvas;
    private EnemyModel enemyModel;

    public EnemyFieldController(Client client, Viewer viewer, EnemyModel enemyModel) {
        this.thread = new Thread(this);
        this.client = client;
        this.viewer = viewer;
        this.enemyModel = enemyModel;
        canvas = viewer.getEnemyCanvas();
    }

    @Override
    public void run() {
        while (client.hasConnectionToServer()) {
            String enemyAction = client.getDataFromServer();

            if (enemyAction == null) {
                break;
            }

            handleServerResponse(enemyAction);
        }
    }

    public void go() {
        thread.start();
    }

    private void handleServerResponse(String enemyAction) {
        for (int i = 1; i < enemyAction.length(); i++) {
            if (Character.isUpperCase(enemyAction.charAt(i))) {
                String singleAction = enemyAction.substring(0, i);
                enemyAction = enemyAction.substring(i);
                i = 0;

                enemyModel.doAction(singleAction);
            }
        }
        enemyModel.doAction(enemyAction);
    }
}
