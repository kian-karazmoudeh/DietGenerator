package com.mycompany.dietgenerator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import GeneticAlgorithm.Aliment;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import GeneticAlgorithm.Diet;
import GeneticAlgorithm.Meal;
import GeneticAlgorithm.Breakfast;
import GeneticAlgorithm.Snack;
import GeneticAlgorithm.Lunch;
import GeneticAlgorithm.Dinner;


/**
 * FXML Controller class for diet creation window.
 * 
 * @author kxg708
 */
public class DietCreationWindowController implements Initializable {
    /**
     * ListView for user Likes input. 
     */
    @FXML private ListView<Aliment> likesListView;

    /**
     * ListView for user Dislikes input.
     */
    @FXML private ListView<Aliment> dislikesListView;

    /**
     * TextFields for calorie and macro-nutrient goals.
     */
    @FXML private TextField calorieTextField, proteinTextField, fatTextField, carbTextField;
    
    /**
     * This method is run when the Window is first created. The global variables
     * App.likes and App.dislikes are set to the contents list of the listViews,
     * context menus are added to the list views, and the goals text fields are
     * turned numeric.
     * 
     * @param url variable not used. Internally passed.
     * @param rb variable not used. Internally passed.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        likesListView.setItems(App.likes);
        dislikesListView.setItems(App.dislikes);
        
        addContextMenu(likesListView);
        addContextMenu(dislikesListView);
        
        Validation.createNumeric(calorieTextField);
        Validation.createNumeric(proteinTextField);
        Validation.createNumeric(carbTextField);
        Validation.createNumeric(fatTextField); 
    }
    
    /**
     * Utility method which takes in a ListView and adds a FXML context menu to it.
     * 
     * @param listView the ListView object to which the context menu should be added.
     */
    private void addContextMenu(ListView<Aliment> listview) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("حذف");
        deleteItem.setOnAction(e -> {
            Aliment selectedAliment = listview.getSelectionModel().getSelectedItem();
            listview.getItems().remove(selectedAliment);
        });

        contextMenu.getItems().addAll(deleteItem);

        listview.setContextMenu(contextMenu);
    }
    
    /**
     * Mouse click event handler which displays the aliment selector window when
     * the left click mouse button is pressed on a list view (likes or dislikes).
     * It then injects the List which called it to the window controller.
     * 
     * @param event variable containing information about the event, such as source.
     */
    @FXML
    private void switchToAlimentSelectorWindow(MouseEvent event) {
        
        try {
            if (event.getButton() == MouseButton.PRIMARY) {
                Stage alimentSelectionWindow = new Stage();
                FXMLLoader window = App.getWindow("alimentSelectorWindow");

                Scene scene = new Scene(window.load());

                AlimentSelectorWindowController controller = window.getController();

                ListView<Aliment> list = (ListView<Aliment>) event.getSource();

                controller.injectObservableList(list.getItems());
                controller.injectStage(alimentSelectionWindow);

                alimentSelectionWindow.setScene(scene);
                alimentSelectionWindow.show();
            }
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
    }
    
    /**
     * Button event handler which displays the automatic goals calculator window,
     * and injects the goals text fields to be populated.
     */
    @FXML
    private void switchToGoalsCalculatorWindow() {
        try {
            Stage goalsCalculatorStage = new Stage();
            FXMLLoader window = App.getWindow("goalsCalculatorWindow");

            Scene scene = new Scene(window.load());

            GoalsCalculatorWindowController controller = window.getController();
            controller.injectStage(goalsCalculatorStage);
            controller.injectFields(calorieTextField, proteinTextField, carbTextField, fatTextField);
            goalsCalculatorStage.setScene(scene);
            goalsCalculatorStage.show();
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
        
    }
    
    /**
     * Button event handler which gets a generated diet using {@link #generateDiet()}
     * and injects it to a created diet display window.
     */
    @FXML
    private void switchToDietDisplayWindow() {
        try {
            Diet diet = generateDiet();            
            
            if (diet == null) return;
            
            Stage dietDisplayStage = new Stage();
            FXMLLoader window = App.getWindow("dietDisplayWindow");
            Scene scene = new Scene(window.load());
            
            DietDisplayWindowController controller = window.getController();
            controller.injectStage(dietDisplayStage);
            controller.injectDiet(diet);
            
            dietDisplayStage.setScene(scene);
            
            dietDisplayStage.show();
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
        
    }
    
    /**
     * Method which creates 4 Meal objects and calls their run() methods on 4 separate threads.
     * This generates 5 meal options for each Meal object. It then adds the Meals to a Diet
     * object and returns it.
     * 
     * @return the generated Diet object.
     */
    private Diet generateDiet() {
        
        if (!isCalorieTxtFldValid() || !areMacronutrientTxtFldsValid()) return null;
        
        int calorieGoal = Integer.parseInt(calorieTextField.getText());
        int proteinGoal = Integer.parseInt(proteinTextField.getText());
        int carbGoal = Integer.parseInt(carbTextField.getText());
        int fatGoal = Integer.parseInt(fatTextField.getText());
        
        
        Meal breakfastMeal = new Breakfast(calorieGoal, proteinGoal, carbGoal, fatGoal);
        Meal snackMeal = new Snack(calorieGoal, proteinGoal, carbGoal, fatGoal);
        Meal lunchMeal = new Lunch(calorieGoal, proteinGoal, carbGoal, fatGoal);
        Meal dinnerMeal = new Dinner(calorieGoal, proteinGoal, carbGoal, fatGoal);

        
        Thread breakfastThread = new Thread(breakfastMeal);
        Thread snackThread = new Thread(snackMeal);
        Thread lunchThread = new Thread(lunchMeal);
        Thread dinnerThread = new Thread(dinnerMeal);
            
        breakfastThread.start();
        snackThread.start();
        lunchThread.start();
        dinnerThread.start();
        
        try {
            breakfastThread.join();
            snackThread.join();
            lunchThread.join();
            dinnerThread.join();
            
            Diet diet = new Diet(breakfastMeal.getMealOptions(),
                            lunchMeal.getMealOptions(), 
                            snackMeal.getMealOptions(), 
                            dinnerMeal.getMealOptions());
            
            return diet;
        } catch (Exception e) {
            Validation.showErrorAlert(e);
            return null;
        }        
    }
    
    /**
     * Method which checks validation of the calorie text field.
     * 
     * @return true if valid, false if invalid
     */
    private boolean isCalorieTxtFldValid() {
        if (Validation.isTxtFldEmpty(calorieTextField) ||
            !Validation.isTxtFldNumeric(calorieTextField)) {
            return false;
        }
        
        int calorieValue = Integer.parseInt(calorieTextField.getText());
        
        if (calorieValue <= 500) {
            Validation.showWarningAlert("کالری نمی تواند کمتر از 500 باشد");
            return false;
        }
        
        return true;
    }
    
    /**
     * Method which checks the validation of the macro-nutrient text fields.
     * 
     * @return true if valid, false if invalid
     */
    private boolean areMacronutrientTxtFldsValid() {
        if (Validation.isTxtFldEmpty(proteinTextField) ||
            Validation.isTxtFldEmpty(carbTextField) ||
            Validation.isTxtFldEmpty(fatTextField) ||
            !Validation.isTxtFldNumeric(proteinTextField) ||
            !Validation.isTxtFldNumeric(carbTextField) ||
            !Validation.isTxtFldNumeric(fatTextField)) {
            return false;
        }
        
        
        int proteinValue = Integer.parseInt(proteinTextField.getText());
        int carbValue = Integer.parseInt(carbTextField.getText());
        int fatValue = Integer.parseInt(fatTextField.getText());
        
        // range check
        if (proteinValue <= 10 || carbValue <= 10 || fatValue <= 10) {
            Validation.showWarningAlert("پروتئین، کربوهیدرات و چربی نمی تواند کمتر از 10 گرم باشد");
            return false;
        }
        
        int calorieValue = Integer.parseInt(calorieTextField.getText());
        int calorieSum = proteinValue * 4 + carbValue * 4 + fatValue * 9;
        // since each gram of protein has 4 calories
        // and each gram of carb has 4 calories
        // and each gram of fat has 9 calories
        
        // check whether the calorie goal matches the sum of the calories
        // of the macronutrients
        float calorieVariation = Math.abs( calorieValue - calorieSum ) / (float) calorieValue;
        
        if (calorieVariation > 0.1) {
            Validation.showWarningAlert("کالری پروتئین، کربوهیدرات و چربی وارد شده با هدف کالری وارد شده مطابقت ندارد!");
            return false;
        }
        
        return true;
    }
}

