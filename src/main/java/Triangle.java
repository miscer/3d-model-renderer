public class Triangle {
  private Vector a;
  private Vector b;
  private Vector c;
  
  public Triangle(Vector a, Vector b, Vector c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public Vector getA() {
    return a;
  }

  public Vector getB() {
    return b;
  }

  public Vector getC() {
    return c;
  }

  @Override
  public String toString() {
    return String.format("Triangle{%s, %s, %s}", a, b, c);
  }
}