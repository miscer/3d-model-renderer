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
        SimpleMatrix matrix = new SimpleMatrix(3, 3);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                double value = scanner.nextDouble();
                matrix.set(y, x, value);
            }
        }

        return new Triangle(matrix);
    }
}