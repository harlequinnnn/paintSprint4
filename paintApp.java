package com.example.paintsprint;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class paintApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        paintWindow.initialize(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}