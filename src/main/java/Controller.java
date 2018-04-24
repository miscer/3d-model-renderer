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
    private Renderer renderer;

    private static final Camera CAMERA = new Camera(1200, 800, 240000, 160000);
    private static final SimpleMatrix LIGHT = Vectors.create3d(0, 0, 0);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        renderer = new Renderer(context);
    }

    public void render(List<Triangle> triangles) {
        Renderer.prepareTriangles(triangles);
        renderer.renderTriangles(CAMERA, LIGHT, triangles);
    }
}
