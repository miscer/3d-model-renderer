import org.ejml.simple.SimpleMatrix;

public class Vectors {
    public static SimpleMatrix create3d(double x, double y, double z) {
        return new SimpleMatrix(new double[][]{{x}, {y}, {z}});
    }

    public static SimpleMatrix cross(SimpleMatrix u, SimpleMatrix v) {
        assert u.isVector();
        assert v.isVector();

        return create3d(
                u.get(1) * v.get(2) - u.get(2) * v.get(1),
                u.get(2) * v.get(0) - u.get(0) * v.get(2),
                u.get(0) * v.get(1) - u.get(1) * v.get(0)
        );
    }

    public static double dot(SimpleMatrix u, SimpleMatrix v) {
        return u.dot(v);
    }

    public static double magnitude(SimpleMatrix u) {
        double x = u.get(0);
        double y = u.get(1);
        double z = u.get(2);

        return Math.sqrt(x * x + y * y + z * z);
    }

    public static SimpleMatrix unit(SimpleMatrix u) {
        double m = magnitude(u);

        if (m > 0) {
            return u.divide(m);
        } else {
            return u;
        }
    }

    public static double distance(SimpleMatrix a, SimpleMatrix b) {
        return magnitude(b.minus(a));
    }

    public static SimpleMatrix homogenize(SimpleMatrix u) {
        return u.concatRows(new SimpleMatrix(new double[][]{{1}}));
    }

    public static SimpleMatrix dehomogenize(SimpleMatrix u) {
        int lastRow = u.numRows() - 1;
        return u.extractMatrix(0, lastRow, 0, 1).divide(u.get(lastRow, 0));
    }
}
