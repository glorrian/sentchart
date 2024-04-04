package dev.sentchart.application.controller;

import dev.sentchart.application.chart.FourierSample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController {
    @FXML
    private ScrollPane leftPane;

    @FXML
    private VBox leftVBox;

    @FXML
    private ScrollPane rightPane;

    @FXML
    private VBox rightVBox;

    @FXML
    void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file with data");
        File choosedFile = fileChooser.showOpenDialog(rightPane.getScene().getWindow());
        addSignalChart(choosedFile);
    }

    void addSignalChart(File file) {
        long time = System.currentTimeMillis();
        FourierSample sample = new FourierSample(file);
        if (rightVBox == null) {
            rightVBox = new VBox();
            rightVBox.alignmentProperty().set(Pos.CENTER);
        }
        rightVBox.getChildren().add(sample.getChartPanel());
        System.out.println(System.currentTimeMillis() - time);
    }

}
