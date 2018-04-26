import org.ejml.simple.SimpleMatrix;

/**
 * Represents a single triangle in the model
 */
public class Triangle {
    private SimpleMatrix position;
    private SimpleMatrix texture;

    /**
     * Creates a new triangle
     * @param position Position of the triangle as a 3x3 matrix containing the three vertices of the triangle
     * @param texture 1x3 matrix containing the colour at each vertex
     */
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

    /**
     * Calculates the surface normal vector for the triangle
     * @return Surface normal vector
     */
    public SimpleMatrix getSurfaceNormal() {
        SimpleMatrix p1 = position.extractVector(false, 0);
        SimpleMatrix p2 = position.extractVector(false, 1);
        SimpleMatrix p3 = position.extractVector(false, 2);

        SimpleMatrix u = p2.minus(p1);
        SimpleMatrix v = p3.minus(p1);

        return Vectors.cross(u, v);
    }

    /**
     * Returns the position of the triangle centeroid
     * @return Position of the centeroid
     */
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