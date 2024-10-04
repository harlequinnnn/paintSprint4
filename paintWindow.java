package com.example.paintsprint;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class paintWindow {
    @FXML
    private static MenuItem saveAsButton;
    @FXML
    public static AnchorPane anchorPane;

    @FXML
    public static Canvas canvas;

    @FXML
    public static Scene scene;
    public static int windowLength = 609;
    public static int windowHeight = 407;

    public static void initialize(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(paintApp.class.getResource("paint.fxml"));
        scene = new Scene(fxmlLoader.load(), windowLength, windowHeight);
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();

    }


}
