import org.ejml.simple.SimpleMatrix;

public class Vectors {
    /**
     * Creates a new matrix representing a 3D vector
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return Matrix contaning the vector
     */
    public static SimpleMatrix create3d(double x, double y, double z) {
        return new SimpleMatrix(new double[][]{{x}, {y}, {z}});
    }

    /**
     * Calculates the cross product of two 3D vectors
     * @param u First vector
     * @param v Second vector
     * @return Cross product vector
     */
    public static SimpleMatrix cross(SimpleMatrix u, SimpleMatrix v) {
        assert u.isVector();
        assert v.isVector();

        return create3d(
                u.get(1) * v.get(2) - u.get(2) * v.get(1),
                u.get(2) * v.get(0) - u.get(0) * v.get(2),
                u.get(0) * v.get(1) - u.get(1) * v.get(0)
        );
    }

    /**
     * Calculates the dot product of two vectors
     * @param u First vector
     * @param v Second vector
     * @return Dot product
     */
    public static double dot(SimpleMatrix u, SimpleMatrix v) {
        return u.dot(v);
    }

    /**
     * Calculates the length/magnitude of a 3D vector
     * @param u
     * @return
     */
    public static double magnitude(SimpleMatrix u) {
        double x = u.get(0);
        double y = u.get(1);
        double z = u.get(2);

        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Calculates the unit vector
     * @param u Input vector
     * @return Unit vector
     */
    public static SimpleMatrix unit(SimpleMatrix u) {
        double m = magnitude(u);

        if (m > 0) {
            return u.divide(m);
        } else {
            return u;
        }
    }

    /**
     * Creates homogeneous coordinates from the vector
     * @param u Input vector
     * @return Homogeneous coordinates
     */
    public static SimpleMatrix homogenize(SimpleMatrix u) {
        return u.concatRows(new SimpleMatrix(new double[][]{{1}}));
    }

    /**
     * Returns vector from homogeneous coordinates
     * @param u Input vector in homogeneous coordinates
     * @return De-homogenised vector
     */
    public static SimpleMatrix dehomogenize(SimpleMatrix u) {
        int lastRow = u.numRows() - 1;
        return u.extractMatrix(0, lastRow, 0, 1).divide(u.get(lastRow, 0));
    }
}
