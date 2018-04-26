import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Renderer {
    private final PixelWriter writer;
    private final GraphicsContext context;

    public Renderer(GraphicsContext context) {
        this.context = context;
        this.writer = context.getPixelWriter();
    }

    public static void prepareTriangles(List<Triangle> triangles) {
        triangles.sort(Comparator.comparingDouble(o -> o.getCenteroid().get(2)));
    }

    public void renderTriangles(Camera camera, Light light, List<Triangle> triangles) {
        for (Triangle triangle : triangles) {
            renderTriangle(camera, light, triangle);
        }
    }

    private void renderTriangle(Camera camera, Light light, Triangle triangle) {
        SimpleMatrix m = triangle.getPosition();
        SimpleMatrix a = camera.project(m.extractVector(false, 0));
        SimpleMatrix b = camera.project(m.extractVector(false, 1));
        SimpleMatrix c = camera.project(m.extractVector(false, 2));

        double illumination = light.getIllumination(triangle.getSurfaceNormal(), triangle.getCenteroid());
        fillTriangle(a, b, c, triangle.getTexture().scale(illumination));
    }

    private Color getTriangleColor(Light light, Triangle triangle) {
        double illumination = light.getIllumination(triangle.getSurfaceNormal(), triangle.getCenteroid());
        return Color.gray(illumination);
    }

    private void fillTriangle(SimpleMatrix a, SimpleMatrix b, SimpleMatrix c, SimpleMatrix texture) {
        SimpleMatrix dp1 = b.minus(a);
        SimpleMatrix dp2 = c.minus(a);

        double dc1 = texture.get(1) - texture.get(0);
        double dc2 = texture.get(2) - texture.get(0);

        for (double i = 0; i <= 1; i += 0.1) {
            for (double j = 0; j <= 1; j += 0.1) {
                if (i + j <= 1) {
                    SimpleMatrix p = a.plus(dp1.scale(i)).plus(dp2.scale(j));

                    double gray = Math.min(Math.max(texture.get(0) + i * dc1 + j * dc2, 0), 255);
                    Color color = Color.gray(gray / 255);

                    writer.setColor(
                            (int) p.get(0),
                            (int) p.get(1),
                            color
                    );
                }
            }
        }
    }

}
