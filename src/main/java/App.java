import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        InputStream shapeInputStream = new FileInputStream("data/face-shape.snip.txt");

        List<Triangle> triangles = Parser.parseTriangles(shapeInputStream);
        System.out.println(triangles);
    }
}
