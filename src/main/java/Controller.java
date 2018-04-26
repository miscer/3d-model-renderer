import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Canvas canvas;
    public TextField lightPositionX;
    public TextField lightPositionY;
    public TextField lightPositionZ;
    public TextField lightIntensity;
    private Renderer renderer;
    private List<Triangle> triangles;
    private Camera camera;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsContext context = canvas.getGraphicsContext2D();

        renderer = new Renderer(context);
        camera = new Camera(canvas.getWidth(), canvas.getHeight(), 240000, 160000);
    }

    public void render(List<Triangle> triangles) {
        Renderer.prepareTriangles(triangles);
        this.triangles = triangles;

        Light light = getLight();

        if (light != null) {
            renderer.renderTriangles(camera, light, triangles);
        }
    }

    public void handleRenderClick(ActionEvent actionEvent) {
        Light light = getLight();

        if (light != null) {
            renderer.renderTriangles(camera, light, triangles);
        }
    }

    private Light getLight() {
        try {
            double x = Double.parseDouble(lightPositionX.getText());
            double y = Double.parseDouble(lightPositionY.getText());
            double z = Double.parseDouble(lightPositionZ.getText());
            double intensity = Double.parseDouble(lightIntensity.getText());
            return new Light(intensity, Vectors.create3d(x, y, z));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
