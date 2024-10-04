package com.example.paintsprint;

// importing an unholy amount of stuff
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class paintFunctions {
    @FXML
    public CheckBox dashToggle;

    @FXML
    public Pane displayPane;

    @FXML
    public StackPane stackPane;

    @FXML
    public Scene scene;

    @FXML
    public Canvas canvas;

    @FXML
    public Slider widthSlider;

    @FXML
    public Text widthText;

    @FXML
    public ColorPicker colorPicker;

    @FXML
    public Button exitButton;

    @FXML
    public MenuItem selectFile;

    @FXML
    public MenuItem saveButton;

    @FXML
    public MenuItem saveAsButton;

    @FXML
    public MenuItem aboutButton;

    @FXML
    public ImageView displayFile;

    @FXML
    private static GraphicsContext graphicsContext;
    @FXML
    public MenuItem lineSelect;

    @FXML
    public MenuItem rectangleSelect;

    @FXML
    public MenuItem straightSelect;

    @FXML
    public MenuItem circleSelect;

    @FXML
    public MenuItem ellipseSelect;

    @FXML
    public MenuItem triangleSelect;
    private double beginningX;
    private double beginningY;
    private double tempX;
    private double tempY;

    private windowTools windowTools;

    public boolean drawingShape;

    enum shapeType{
        LINE, RECTANGLE, SQUARE, TRIANGLE, CIRCLE, ELLIPSE, STRAIGHTLINE
    }
    private shapeType currentShape = shapeType.LINE;

    @FXML
    public void initialize() {
        windowTools = new windowTools(stackPane);
        paintInitialization(canvas);
        canvasInitialize();
        mouseFunctions();
        initializeWindowTools();
        initializeShapeSelector();
        dashInitializer();
    }

    private void dashInitializer(){
        dashToggle.setOnAction(e -> dashToggle());
    }

    private void dashToggle(){
        boolean dashedLine = dashToggle.isSelected();
        if (dashedLine) {
            graphicsContext.setLineDashes(10); // Set a more reasonable dash length
        } else {
            graphicsContext.setLineDashes(0); // Reset to solid line
        }
    }


    @FXML
    private void initializeWindowTools() {
        saveButton.setOnAction(e -> windowTools.saveClick());
        saveAsButton.setOnAction(e -> windowTools.saveAsClick());
        selectFile.setOnAction(e -> windowTools.selectClick());
        exitButton.setOnAction(e -> windowTools.exitClick((Stage) exitButton.getScene().getWindow()));
        aboutButton.setOnAction(e -> windowTools.aboutClick());
    }

    @FXML
    public void paintInitialization(Canvas canvas) {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Color.BLACK); // initial pen color is black
        canvas.setWidth(displayPane.getPrefWidth()); // sets the canvas measurements to the displaypane's
        canvas.setHeight(displayPane.getPrefHeight());

        widthSlider.valueProperty().addListener((observable, oldWidth, newWidth) -> { // creates a listener for the slider
            graphicsContext.setLineWidth(newWidth.doubleValue()); // changes the width of the pen depending on the slider
            widthText.setText(newWidth.intValue() + "px");
        });

            // color picker controls
        colorPicker.valueProperty().addListener((observable, oldColor, newColor) -> {
            graphicsContext.setStroke(newColor);
        });
    }

    @FXML
    public void canvasInitialize(){
        canvas.widthProperty().bind(displayPane.widthProperty());
        canvas.heightProperty().bind(displayPane.heightProperty());
    }

    public static void pickColor(){
        Canvas pickCanvas = new Canvas();
        pickCanvas.setOnMouseClicked(event -> {
            Color color = pickCanvas.snapshot(null, null).getPixelReader().getColor((int) event.getX(), (int) event.getY());
            graphicsContext.setStroke(color);
        });
    }


    @FXML
    private void initializeShapeSelector() {
        lineSelect.setOnAction(e -> currentShape = shapeType.LINE);
        rectangleSelect.setOnAction(e -> currentShape = shapeType.RECTANGLE);
        circleSelect.setOnAction(e -> currentShape = shapeType.CIRCLE);
        ellipseSelect.setOnAction(e -> currentShape = shapeType.ELLIPSE);
        triangleSelect.setOnAction(e -> currentShape = shapeType.TRIANGLE);
        straightSelect.setOnAction(e -> currentShape = shapeType.STRAIGHTLINE);
    }

        public void mouseFunctions() {
            canvas.setOnMousePressed((this::mousePressed));
            canvas.setOnMouseDragged((this::mouseDragged));
            canvas.setOnMouseReleased((this::mouseReleased));
        }

    // line drawing
    public void mousePressed(MouseEvent mouseEvent) {
        beginningX = mouseEvent.getX();
        beginningY = mouseEvent.getY(); // when the mouse is clicked, start drawing the line
        tempX = beginningX;
        tempY = beginningY;
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (currentShape == shapeType.LINE) {
            double movingX = mouseEvent.getX();
            double movingY = mouseEvent.getY();
            graphicsContext.strokeLine(tempX, tempY, movingX, movingY);
            tempX = movingX;
            tempY = movingY;
        }
        else {
            drawShape(graphicsContext, beginningX, beginningY, mouseEvent.getX() - beginningX, mouseEvent.getY() - beginningY);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        double endX = mouseEvent.getX();
        double endY = mouseEvent.getY();
        if (currentShape != shapeType.LINE) {
            drawShape(graphicsContext, beginningX, beginningY, endX - beginningX, endY - beginningY);
        }
    }

    private void drawShape(GraphicsContext shapeContext, double x, double y, double width, double height) {
        switch (currentShape) {
            case RECTANGLE:
                drawRectangle(shapeContext, x, y, width, height);
                break;
            case SQUARE:
                drawSquare(shapeContext, x, y, Math.min(width, height));
                break;
            case TRIANGLE:
                drawTriangle(shapeContext, x, y, x + width, y, x + width / 2, y - height);
                break;
            case CIRCLE:
                drawCircle(shapeContext, x + width / 2, y + height / 2, Math.max(width, height) / 2);
                break;
            case ELLIPSE:
                drawEllipse(shapeContext, x, y, width, height);
                break;
            case STRAIGHTLINE:
                drawStraightLine(shapeContext, beginningX, beginningY, beginningX + width, beginningY + height);
                break;
        }
    }

    public void drawRectangle(GraphicsContext gc, double x, double y, double width, double height) {
        gc.strokeRect(x, y, width, height);
    }

    public void drawSquare(GraphicsContext gc, double x, double y, double size) {
        gc.strokeRect(x, y, size, size);
    }

    public void drawTriangle(GraphicsContext gc, double x1, double y1, double x2, double y2, double x3, double y3) {
        gc.strokePolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, 3);
    }

    public void drawCircle(GraphicsContext gc, double x, double y, double radius) {
        gc.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    public void drawEllipse(GraphicsContext gc, double x, double y, double width, double height) {
        gc.strokeOval(x, y, width, height);
    }

    public void drawStraightLine(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }
}