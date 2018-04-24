import org.ejml.simple.SimpleMatrix;

public class Light {
    private double intensity;
    private SimpleMatrix position;

    public Light(double intensity, SimpleMatrix position) {
        this.intensity = intensity;
        this.position = position;
    }

    public double getIllumination(SimpleMatrix normal, SimpleMatrix point) {
        double illumination = intensity * Vectors.dot(
                Vectors.unit(normal),
                Vectors.unit(position.minus(point))
        );

        return Math.max(illumination, 0);
    }
}
