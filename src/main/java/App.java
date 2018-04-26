import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class App extends Application {
    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        InputStream shapeInputStream = getClass().getResource("face-shape.txt").openStream();
        InputStream textureInputStream = getClass().getResource("face-texture.txt").openStream();
        List<Triangle> triangles = Parser.parseTriangles(shapeInputStream, textureInputStream);

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("scene.fxml").openStream());
        controller = loader.getController();

        controller.render(triangles);

        stage.setTitle("CS4102");
        stage.setScene(new Scene(root, 1360, 800));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
