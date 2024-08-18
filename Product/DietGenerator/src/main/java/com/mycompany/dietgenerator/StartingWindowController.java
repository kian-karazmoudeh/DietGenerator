package com.mycompany.dietgenerator;

import javafx.fxml.FXML;
import FileManager.FileManager;
import GeneticAlgorithm.Diet;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class for Starting window.
 * 
 * @author kxg708
 */
public class StartingWindowController {
    /**
     * The stage representing the GUI window.
     */
    private Stage stage;
    
    /**
     * Method for controller to access its own stage. 
     * 
     * @param stage the GUI window. 
     */
    public void injectStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Method which takes in a Diet object and displays it in a new diet
     * display window.
     * 
     * @param diet the Diet object to be displayed in window.
     */
    private void switchToDietDisplayWindow(Diet diet) throws IOException {
        Stage dietDisplayStage = new Stage();
        FXMLLoader window = App.getWindow("dietDisplayWindow");

        Scene scene = new Scene(window.load());
        DietDisplayWindowController controller = window.getController();
        controller.injectStage(dietDisplayStage);
        controller.injectDiet(diet);
        dietDisplayStage.setScene(scene);
        dietDisplayStage.show();
    }
    
    /**
     * Button event handler which displays the diet creation window.
     */
    @FXML
    private void swtichToDietCreationWindow() {
        try {
            Stage dietCreationStage = new Stage();
            FXMLLoader window = App.getWindow("dietCreationWindow");

            Scene scene = new Scene(window.load());
            dietCreationStage.setScene(scene);
            dietCreationStage.show();

            stage.close();
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
    }
    
    /**
     * Button event handler which opens a diet file using file manager and displays it
     * in diet display window using {@link #switchToDietDisplayWindow(GeneticAlgorithm.Diet)}
     */
    @FXML
    private void openADietFile() {
        try {
            Diet diet = FileManager.openDietFile();
            
            // safety check
            if (diet == null) return;
            
            switchToDietDisplayWindow(diet);
            stage.close();
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
        
    }
}
