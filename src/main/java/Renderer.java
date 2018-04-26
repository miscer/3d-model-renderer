import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import org.ejml.simple.SimpleMatrix;

import java.util.Comparator;
import java.util.List;

public class Renderer {
    private final GraphicsContext context;
    private final PixelWriter writer;

    /**
     * Creates a new renderer that will use the specified context for rendering a model
     * @param context Canvas graphics context
     */
    public Renderer(GraphicsContext context) {
        this.context = context;
        this.writer = context.getPixelWriter();
    }

    /**
     * Prepares the list of triangles to be rendered; orders them by the z-coordinate of their centeroids
     * @param triangles
     */
    public static void prepareTriangles(List<Triangle> triangles) {
        triangles.sort(Comparator.comparingDouble(o -> o.getCenteroid().get(2)));
    }

    /**
     * Clears the canvas; erases all pixels
     */
    public void clear() {
        Canvas canvas = context.getCanvas();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Renders all triangles onto the canvas
     * @param camera Camera used for projection
     * @param light Light used for illumination
     * @param triangles Rendered model
     */
    public void renderTriangles(Camera camera, Light light, List<Triangle> triangles) {
        for (Triangle triangle : triangles) {
            renderTriangle(camera, light, triangle);
        }
    }

    private void renderTriangle(Camera camera, Light light, Triangle triangle) {
        SimpleMatrix m = triangle.getPosition();

        // project the triangle vertices to 2D coordinates
        SimpleMatrix a = camera.project(m.extractVector(false, 0));
        SimpleMatrix b = camera.project(m.extractVector(false, 1));
        SimpleMatrix c = camera.project(m.extractVector(false, 2));

        // calculate the illumination intensity for the triangle
        double illumination = light.getIllumination(triangle.getSurfaceNormal(), triangle.getCenteroid());

        // draw the triangle to the canvas
        fillTriangle(a, b, c, triangle.getTexture().scale(illumination));
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
