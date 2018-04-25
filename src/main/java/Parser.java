import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Parser {
    public static List<Triangle> parseTriangles(InputStream shapeInputStream, InputStream textureInputStream) throws IOException {
        Scanner shapeScanner = new Scanner(shapeInputStream);
        Scanner textureScanner = new Scanner(textureInputStream);

        List<Triangle> triangles = new ArrayList<>();

        while (shapeScanner.hasNext() && textureScanner.hasNext()) {
            triangles.add(parseTriangle(shapeScanner, textureScanner));
        }

        return triangles;
    }

    private static Triangle parseTriangle(Scanner shapeScanner, Scanner textureScanner) throws IOException {
        SimpleMatrix position = new SimpleMatrix(3, 3);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                double value = shapeScanner.nextDouble();
                position.set(y, x, value);
            }
        }

        SimpleMatrix texture = new SimpleMatrix(3, 1);

        for (int i = 0; i < 3; i++) {
            double value = Math.max(textureScanner.nextDouble(), 0);
            texture.set(i, value);
        }

        return new Triangle(position, texture);
    }
}