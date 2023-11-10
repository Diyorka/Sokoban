import javax.swing.JFrame;

public class Viewer {

    private Controller controller;
    private Canvas canvas;
    private LevelChooser levelChooser;

    public Viewer() {
        controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);

        JFrame frame = new JFrame("Sokoban MVC");
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setLocation(270, 70);
        frame.add("Center", canvas);
        frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update() {

    }
}
