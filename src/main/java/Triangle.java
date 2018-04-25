import javafx.scene.paint.Color;
import org.ejml.simple.SimpleMatrix;

public class Triangle {
    private SimpleMatrix position;
    private SimpleMatrix texture;

    public Triangle(SimpleMatrix position, SimpleMatrix texture) {
        this.position = position;
        this.texture = texture;
    }

    public SimpleMatrix getPosition() {
        return position;
    }

    public SimpleMatrix getTexture() {
        return texture;
    }

    public SimpleMatrix getSurfaceNormal() {
        SimpleMatrix p1 = position.extractVector(false, 0);
        SimpleMatrix p2 = position.extractVector(false, 1);
        SimpleMatrix p3 = position.extractVector(false, 2);

        SimpleMatrix u = p2.minus(p1);
        SimpleMatrix v = p3.minus(p1);

        return Vectors.cross(u, v);
    }

    public SimpleMatrix getCenteroid() {
        SimpleMatrix p1 = position.extractVector(false, 0);
        SimpleMatrix p2 = position.extractVector(false, 1);
        SimpleMatrix p3 = position.extractVector(false, 2);

        return p1.plus(p2).plus(p3).divide(3);
    }

    @Override
    public String toString() {
        return position.toString();
    }
}