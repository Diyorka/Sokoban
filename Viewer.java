import javax.swing.JFrame;

public class Viewer {

    private Controller controller;
    private Canvas canvas;

    public Viewer() {
        controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);
    }

    public void runApplication() {
        JFrame frame = new JFrame("Sokoban");
        frame.setSize(1000, 700);
        frame.setLocation(270, 70);
        frame.add("Center", canvas);
        frame.setVisible(true);
        frame.addKeyListener(controller);
    }

    public void update() {

    }
}
