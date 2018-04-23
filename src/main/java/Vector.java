public class Vector {
  /**
   * Calculates the dot product of two vectors
   * @param a First vector
   * @param b Second vector
   * @return Dot product
   */
  public static double dot(Vector a, Vector b) {
    return a.x * b.x + a.y * b.y;
  }

  private double x;
  private double y;
  private double z;

  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public Vector setX(double x) {
    return new Vector(x, y, z);
  }

  public Vector setY(double y) {
    return new Vector(x, y, z);
  }

  public Vector setZ(double z) {
    return new Vector(x, y, z);
  }

  public double magnitude() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  public Vector add(Vector other) {
    return new Vector(x + other.x, y + other.y, z + other.z);
  }

  public Vector subtract(Vector other) {
    return new Vector(x - other.x, y - other.y, z - other.z);
  }

  public Vector multiply(double n) {
    return new Vector(x * n, y * n, z * n);
  }

  public Vector divide(double n) {
    return new Vector(x / n, y / n, z / n);
  }

  public Vector unit() {
    double m = magnitude();

    if (m > 0) {
      return divide(magnitude());
    } else {
      return this;
    }
  }

  @Override
  public String toString() {
    return String.format("(%.3f, %.3f, %.3f)", x, y, z);
  }
}