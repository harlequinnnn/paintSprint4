package com.example.paintsprint;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class paintController {
    @FXML
    public Canvas canvas;

    @FXML
    public AnchorPane anchorPane;
    public static paintFunctions paintHandler;
    public static windowFunctions windowHandler;

    public String selectedTool;

    @FXML
    public void initialize(){
        windowHandler.initialize();
        paintHandler.initialize();
        canvasResizing();
    }

    public void setSelectedTool(String tool){
        selectedTool = tool;
    }

    public void canvasResizing(){
        AnchorPane.setTopAnchor(canvas, 0.0);
        AnchorPane.setBottomAnchor(canvas, 0.0);
        AnchorPane.setLeftAnchor(canvas, 0.0);
        AnchorPane.setRightAnchor(canvas, 0.0);
        canvas.widthProperty().bind(anchorPane.widthProperty());
        canvas.heightProperty().bind(anchorPane.heightProperty());
    }
}
