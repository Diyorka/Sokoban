import java.util.Comparator;

public class PathDetailsComparator implements Comparator<PathDetails> {
    @Override
    public int compare(PathDetails o1, PathDetails o2) {
        return Double.compare(o1.getValue(), o2.getValue());
    }
}
