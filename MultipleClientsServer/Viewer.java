import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class Viewer {
  private Controller controller;
  private Canvas myCanvas;
  private Canvas enemyCanvas;

  public Viewer( Client client) {
    controller = new Controller(this, client);
    Model model = controller.getModel();
    myCanvas = new Canvas(model);
    enemyCanvas = new Canvas(model);

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, myCanvas, enemyCanvas);
    splitPane.setDividerLocation(720);
    splitPane.setResizeWeight(0.5);





    JFrame frame = new JFrame("Sokoban MVC Pattern by Erkin Koshoev");
    frame.setSize(1440, 1000);
    frame.setLocation(0, 0);

    frame.add("Center", splitPane);
    frame.setVisible(true);
    frame.addKeyListener(controller);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public Canvas getEnemyCanvas() {
      return enemyCanvas;
  }

  public void update() {
    myCanvas.repaint();
  }
  public void updateEnemyField() {
    enemyCanvas.repaint();
  }


}
