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

    private static final Camera CAMERA = new Camera(1200, 800, 240000, 160000);

    private static final SimpleMatrix LIGHT = Vectors.create3d(0, 0, -20000);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        context = canvas.getGraphicsContext2D();
    }

    public void render(List<Triangle> triangles) {
        SimpleMatrix camera = Vectors.create3d(0, 0, -20000);

        triangles.sort((o1, o2) -> -Double.compare(
                Vectors.distance(o2.getCenteroid(), camera),
                Vectors.distance(o1.getCenteroid(), camera)
        ));

        for (Triangle triangle : triangles) {
            renderTriangle(triangle);
        }
    }

    private void renderTriangle(Triangle triangle) {
        SimpleMatrix m = triangle.getMatrix();
        SimpleMatrix a = CAMERA.project(m.extractVector(false, 0));
        SimpleMatrix b = CAMERA.project(m.extractVector(false, 1));
        SimpleMatrix c = CAMERA.project(m.extractVector(false, 2));

        context.beginPath();
        context.moveTo(a.get(0), a.get(1));
        context.lineTo(b.get(0), b.get(1));
        context.lineTo(c.get(0), c.get(1));
        context.closePath();

        double illumination = Vectors.dot(
                Vectors.unit(triangle.getSurfaceNormal()),
                Vectors.unit(LIGHT.minus(triangle.getCenteroid()))
        );

        Color fill = Color.gray(Math.abs(illumination));
        context.setFill(fill);

        context.fill();
    }
}
