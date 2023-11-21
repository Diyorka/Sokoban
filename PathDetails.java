public class PathDetails implements Comparable<PathDetails>{
    private double value;
    private int i;
    private int j;

    public PathDetails(double value, int i, int j) {
        this.value = value;
        this.i = i;
        this.j = j;
    }

    public double getValue() {
        return value;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public int compareTo(PathDetails other) {
        return Double.compare(this.value, other.value);
    }
}
