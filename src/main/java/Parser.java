import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Parser {
  public static List<Triangle> parseTriangles(InputStream shapeInputStream) throws IOException {
    Scanner shapeScanner = new Scanner(shapeInputStream);

    List<Triangle> triangles = new ArrayList<>();

    while (shapeScanner.hasNext()) {
      triangles.add(parseTriangle(shapeScanner));
    }

    return triangles;
  }

  private static Triangle parseTriangle(Scanner scanner) throws IOException {
    Vector a = parseVector(scanner);
    Vector b = parseVector(scanner);
    Vector c = parseVector(scanner);

    return new Triangle(a, b, c);
  }

  private static Vector parseVector(Scanner scanner) throws IOException {
    double x = scanner.nextDouble();
    double y = scanner.nextDouble();
    double z = scanner.nextDouble();

    return new Vector(x, y, z);
  }
}