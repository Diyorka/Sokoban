import java.util.Objects;

public class PathPair {
    private int y;
    private int x;
    public PathPair(int y, int x){
        this.y = y;
        this.x = x;
    }

    public int getY() {
          return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PathPair && this.y == ((PathPair)obj).y && this.x == ((PathPair)obj).x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
