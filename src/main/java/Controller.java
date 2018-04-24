import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.ejml.simple.SimpleMatrix;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Canvas canvas;
    private GraphicsContext context;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        context = canvas.getGraphicsContext2D();
    }

    public void render() {
        SimpleMatrix matrix = new SimpleMatrix(new double[][]{
                {10, 30, 20},
                {10, 20, 30},
                {100, 200, 150}
        });

        Triangle triangle = new Triangle(matrix);
        renderTriangle(triangle);
    }

    private void renderTriangle(Triangle triangle) {
        SimpleMatrix projection = projectTriangle(triangle);

        context.moveTo(projection.get(0, 0), projection.get(1, 0));
        context.lineTo(projection.get(0, 1), projection.get(1, 1));
        context.lineTo(projection.get(0, 2), projection.get(1, 2));

        SimpleMatrix light = Vectors.create3d(0, 0, 0);

        double illumination = Vectors.dot(
                Vectors.unit(triangle.getSurfaceNormal()),
                Vectors.unit(light.minus(triangle.getCenteroid()))
        );

        Color fill = Color.gray(Math.abs(illumination));
        context.setFill(fill);

        context.fill();
    }

    private SimpleMatrix projectTriangle(Triangle triangle) {
        return triangle.getMatrix().extractMatrix(0, 2, 0, 3);
    }
}
