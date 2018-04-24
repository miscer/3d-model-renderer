import org.ejml.simple.SimpleMatrix;

public class Triangle {
    private SimpleMatrix matrix;

    public Triangle(SimpleMatrix matrix) {
        this.matrix = matrix;
    }

    public SimpleMatrix getMatrix() {
        return matrix;
    }

    public SimpleMatrix getSurfaceNormal() {
        SimpleMatrix p1 = matrix.extractVector(false, 0);
        SimpleMatrix p2 = matrix.extractVector(false, 1);
        SimpleMatrix p3 = matrix.extractVector(false, 2);

        SimpleMatrix u = p2.minus(p1);
        SimpleMatrix v = p3.minus(p1);

        return Vectors.cross(u, v);
    }

    public SimpleMatrix getCenteroid() {
        SimpleMatrix p1 = matrix.extractVector(false, 0);
        SimpleMatrix p2 = matrix.extractVector(false, 1);
        SimpleMatrix p3 = matrix.extractVector(false, 2);

        return p1.plus(p2).plus(p3).divide(3);
    }
}