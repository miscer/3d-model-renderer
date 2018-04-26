import org.ejml.simple.SimpleMatrix;

/**
 * Light implementation using Lambert's illumination model
 */
public class Light {
    private double intensity;
    private SimpleMatrix position;

    /**
     * Creates a new light
     * @param intensity Intensity as a value between 0 and 1
     * @param position Position of the light in the world
     */
    public Light(double intensity, SimpleMatrix position) {
        this.intensity = intensity;
        this.position = position;
    }

    /**
     * Calculates the intensity at the specified point with the specified surface normal
     * @param normal Surface normal at the point
     * @param point Illuminated point
     * @return Illumination intensity
     */
    public double getIllumination(SimpleMatrix normal, SimpleMatrix point) {
        double illumination = intensity * Vectors.dot(
                Vectors.unit(normal),
                Vectors.unit(position.minus(point))
        );

        return Math.max(illumination, 0);
    }
}
