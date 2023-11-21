public class PathCell {
    private PathPair parent;
    private double f;
    private double g;
    private double h;

    public PathCell() {
        parent = new PathPair(-1, -1);
        f = -1;
        g = -1;
        h = -1;
    }

    public PathCell(PathPair parent, double f, double g, double h) {
        this.parent = parent;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    public void setParent(PathPair parent) {
        this.parent = parent;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setG(double G) {
        this.g = g;
    }

    public PathPair getParent() {
        return parent;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }
}
