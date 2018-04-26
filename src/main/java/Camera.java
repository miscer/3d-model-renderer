import org.ejml.simple.SimpleMatrix;

/**
 * Simple camera implementation that uses orthographic projection
 */
public class Camera {
    private SimpleMatrix matrix;

    /**
     * Creates a new camera
     * @param screenWidth Width of the 2D canvas
     * @param screenHeight Height of the 2D canvas
     * @param worldWidth Width of the camera window
     * @param worldHeight Height of the camera window
     */
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

    /**
     * Returns projection matrix that can be multiplied by a 3D homogeneous coordinate, returning a projected
     * 2D coordinate
     * @return
     */
    public SimpleMatrix getProjectionMatrix() {
        return matrix;
    }

    /**
     * Projects a single 3D point to a 2D point
     * @param a 3D point
     * @return 2D point
     */
    public SimpleMatrix project(SimpleMatrix a) {
        SimpleMatrix h = Vectors.homogenize(a);
        SimpleMatrix p = matrix.mult(h);
        return Vectors.dehomogenize(p);
    }
}
