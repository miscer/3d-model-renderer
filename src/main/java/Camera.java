import org.ejml.simple.SimpleMatrix;

public class Camera {
    private SimpleMatrix matrix;

    public Camera(double screenWidth, double screenHeight, double worldWidth, double worldHeight) {
        double sx = screenWidth / worldWidth;
        double sy = screenHeight / worldHeight;
        double tx = worldWidth / 2;
        double ty = worldHeight / 2;

        matrix = new SimpleMatrix(new double[][]{
                {sx, 0, 0, sx * tx},
                {0, -sy, 0, sy * ty},
                {0, 0, 0, 1}
        });
    }

    public SimpleMatrix getProjectionMatrix() {
        return matrix;
    }

    public SimpleMatrix project(SimpleMatrix a) {
        SimpleMatrix h = Vectors.homogenize(a);
        SimpleMatrix p = matrix.mult(h);
        return Vectors.dehomogenize(p);
    }
}
