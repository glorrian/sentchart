package dev.sentchart.application;

import dev.sentchart.LibrariesLoader;
import dev.sentchart.application.controller.MainController;
import dev.sentchart.application.element.FileElement;
import dev.sentchart.math.NativeMath;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LibrariesLoader.loadAll();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css").toExternalForm()));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        MainController controller = loader.getController();
        CSSFX.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
