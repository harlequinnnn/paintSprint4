package com.example.paintsprint;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class windowFunctions extends paintWindow {
    @FXML
    public static Button exitButton;

    @FXML
    public MenuItem selectFile;

    @FXML
    public MenuItem saveButton;

    @FXML
    public MenuItem saveAsButton;

    @FXML
    public MenuItem aboutButton;

    @FXML
    public Button eyeDropper;

    @FXML
    private static StackPane stackPane;

    private static windowTools windowTools;
    public static boolean canvasSaved = false;

    @FXML
    public void initialize(){;
        saveFunctions();
        miscFunctions();
    }

    public void saveFunctions() {
        saveAsButton.setOnAction((actionEvent) -> {
            windowTools.saveAsClick();
            canvasSaved = true;
        });

        saveButton.setOnAction((actionEvent -> {
            windowTools.saveClick();
            canvasSaved = true;
        }));
    }
    public void miscFunctions(){
        selectFile.setOnAction((actionEvent) -> {
            windowTools.selectClick();
        });
        exitButton.setOnAction((actionEvent) -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            windowTools.exitClick(stage);
        });
        aboutButton.setOnAction((actionEvent -> {
            windowTools.aboutClick();
        }));
        eyeDropper.setOnAction((actionEvent -> {
            paintFunctions.pickColor();
        }));
    }

    public static void isSaved(Stage stage) {
        // Check if the work is saved and show the confirmation alert
        if (!canvasSaved) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(stage);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("You have unsaved changes. Do you want to save?");

            ButtonType saveButton = new ButtonType("Save");
            ButtonType dontSaveButton = new ButtonType("Exit Without Saving");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == saveButton) {
            } else if (result.get() == dontSaveButton) {
                stage.close();
            } else {
            }
        } else {
            stage.close();
        }
    }
}

