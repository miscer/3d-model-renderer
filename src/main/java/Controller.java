import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.ejml.simple.SimpleMatrix;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Canvas canvas;
    private GraphicsContext context;

    private static final SimpleMatrix TRIANGLE_PROJECTION_MATRIX =
            new SimpleMatrix(new double[][]{{0.005, 0, 0}, {0, 0.005, 0}});

    private static final SimpleMatrix LIGHT = Vectors.create3d(0, 0, 0);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        context = canvas.getGraphicsContext2D();
    }

    public void render(List<Triangle> triangles) {
        for (Triangle triangle : triangles) {
            renderTriangle(triangle);
        }
    }

    private void renderTriangle(Triangle triangle) {
        SimpleMatrix projection = projectTriangle(triangle);

        context.beginPath();
        context.moveTo(projection.get(0, 0), projection.get(1, 0));
        context.lineTo(projection.get(0, 1), projection.get(1, 1));
        context.lineTo(projection.get(0, 2), projection.get(1, 2));
        context.closePath();

        double illumination = Vectors.dot(
                Vectors.unit(triangle.getSurfaceNormal()),
                Vectors.unit(LIGHT.minus(triangle.getCenteroid()))
        );

        Color fill = Color.gray(Math.abs(illumination));
        context.setFill(fill);

        context.fill();
    }

    private SimpleMatrix projectTriangle(Triangle triangle) {
        return TRIANGLE_PROJECTION_MATRIX.mult(triangle.getMatrix());
    }
}
