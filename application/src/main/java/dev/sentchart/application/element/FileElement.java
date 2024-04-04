package dev.sentchart.application.element;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;

public class FileElement extends HBox {
    @FXML
    private HBox fileItem;
    @FXML
    private Label fileName;
    @FXML
    private Label filePath;

    public FileElement(File file) {
         initialize();
         setFileName(file.getName());
         setFilePath(file.getPath());
    }

    public FileElement(String fileName, String filePath) {
        initialize();
        setFileName(fileName);
        setFilePath(filePath);
    }

    private void initialize(){
     FXMLLoader FXMLloader = new FXMLLoader(getClass().getResource("file_element.fxml"));
     FXMLloader.setRoot(this);
     FXMLloader.setController(this);

     try {
         FXMLloader.load();
     } catch (IOException e) {
         throw new RuntimeException(e);
     }
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    public void setFilePath(String filePath) {
         this.filePath.setText(filePath);
    }
}
