import org.ejml.simple.SimpleMatrix;

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
    return null;
  }

  private static SimpleMatrix parseVector3d(Scanner scanner) throws IOException {
    double x = scanner.nextDouble();
    double y = scanner.nextDouble();
    double z = scanner.nextDouble();

    return new SimpleMatrix(new double[][]{{x}, {y}, {z}});
  }
}