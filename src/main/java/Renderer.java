import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.ejml.simple.SimpleMatrix;

import java.util.List;

public class Renderer {
    private GraphicsContext context;

    public Renderer(GraphicsContext context) {
        this.context = context;
    }

    public static void prepareTriangles(List<Triangle> triangles) {
        SimpleMatrix camera = Vectors.create3d(0, 0, -20000);

        triangles.sort((o1, o2) -> -Double.compare(
                Vectors.distance(o2.getCenteroid(), camera),
                Vectors.distance(o1.getCenteroid(), camera)
        ));
    }

    public void renderTriangles(Camera camera, Light light, List<Triangle> triangles) {
        for (Triangle triangle : triangles) {
            renderTriangle(camera, light, triangle);
        }
    }

    public void renderTriangle(Camera camera, Light light, Triangle triangle) {
        SimpleMatrix m = triangle.getPosition();
        SimpleMatrix a = camera.project(m.extractVector(false, 0));
        SimpleMatrix b = camera.project(m.extractVector(false, 1));
        SimpleMatrix c = camera.project(m.extractVector(false, 2));

        context.beginPath();
        context.moveTo(a.get(0), a.get(1));
        context.lineTo(b.get(0), b.get(1));
        context.lineTo(c.get(0), c.get(1));
        context.closePath();

        Color fill = getTriangleColor(light, triangle);
        context.setFill(fill);

        context.fill();
    }

    private Color getTriangleColor(Light light, Triangle triangle) {
        double illumination = light.getIllumination(triangle.getSurfaceNormal(), triangle.getCenteroid());
        return Color.gray(illumination);
    }

}
