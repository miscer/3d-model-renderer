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
     *
     * @param context Canvas graphics context
     */
    public Renderer(GraphicsContext context) {
        this.context = context;
        this.writer = context.getPixelWriter();
    }

    /**
     * Prepares the list of triangles to be rendered; orders them by the z-coordinate of their centeroids
     *
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
     *
     * @param camera    Camera used for projection
     * @param light     Light used for illumination
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
        // calculate the number of samples (the step size)
        double step = getTriangleFillStep(a, b, c);

        // sample values of j and k in the range [0, 1], such that j + k <= 1
        for (double j = 0; j <= 1; j += step) {
            for (double k = 0; k <= 1; k += step) {
                if (j + k <= 1) {
                    // calculate value of i based on j and k
                    double i = 1 - j - k;

                    // interpolate the position and colour of the point
                    SimpleMatrix position = a.scale(i).plus(b.scale(j)).plus(c.scale(k));
                    double color = texture.get(0) * i + texture.get(1) * j + texture.get(2) * k;

                    // paint the pixel
                    writer.setColor(
                            (int) Math.round(position.get(0)),
                            (int) Math.round(position.get(1)),
                            Color.gray(color / 255)
                    );
                }
            }
        }
    }

    private double getTriangleFillStep(SimpleMatrix a, SimpleMatrix b, SimpleMatrix c) {
        // calculate the bounding box of the triangle
        double maxX = Math.max(a.get(0), Math.max(b.get(0), c.get(0)));
        double minX = Math.min(a.get(0), Math.max(b.get(0), c.get(0)));
        double maxY = Math.max(a.get(1), Math.max(b.get(1), c.get(1)));
        double minY = Math.min(a.get(1), Math.max(b.get(1), c.get(1)));

        // select either width or height, depending on which one is bigger
        double maxDelta = Math.max(maxX - minX, maxY - minY);

        return 1 / maxDelta;
    }
}
