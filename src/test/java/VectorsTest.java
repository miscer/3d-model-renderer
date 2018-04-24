import org.ejml.EjmlUnitTests;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorsTest {

    @Test
    public void homogenize() {
        SimpleMatrix a = new SimpleMatrix(new double[][]{{1}, {2}, {3}});
        SimpleMatrix b = new SimpleMatrix(new double[][]{{1}, {2}, {3}, {1}});
        EjmlUnitTests.assertEquals(b.getMatrix(), Vectors.homogenize(a).getMatrix());
    }

    @Test
    public void dehomogenize() {
        SimpleMatrix a = new SimpleMatrix(new double[][]{{2}, {4}, {6}, {2}});
        SimpleMatrix b = new SimpleMatrix(new double[][]{{1}, {2}, {3}});
        EjmlUnitTests.assertEquals(b.getMatrix(), Vectors.dehomogenize(a).getMatrix());
    }
}