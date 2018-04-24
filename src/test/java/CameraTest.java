import org.ejml.EjmlUnitTests;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

public class CameraTest {

    @Test
    public void getMatrix() {
        Camera camera = new Camera(200, 100, 400, 200);

        SimpleMatrix matrix = new SimpleMatrix(new double[][]{
                {0.5, 0, 0, 100},
                {0, -0.5, 0, 50},
                {0, 0, 0, 1}
        });

        EjmlUnitTests.assertEquals(matrix.getMatrix(), camera.getProjectionMatrix().getMatrix());
    }
}