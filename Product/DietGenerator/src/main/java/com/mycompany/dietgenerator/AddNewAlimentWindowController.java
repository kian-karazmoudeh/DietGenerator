package com.mycompany.dietgenerator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import GeneticAlgorithm.Aliment;
import FileManager.FileManager;
import GeneticAlgorithm.Meal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class for add new aliment window.
 * 
 * @author kxg708
 */
public class AddNewAlimentWindowController implements Initializable {
    
    /**
     * Text Field for aliment name input.
     */
    @FXML private TextField nameTextField;

    /**
     * Text Field for aliment calorie per serving input.
     */
    @FXML private TextField calorieTextField;

    /**
     * Text Field for aliment serving size input.
     */
    @FXML private TextField servingSizeTextField;

    /**
     * Text Field for aliment carbohydrate (in grams) per serving input.
     */
    @FXML private TextField carbTextField;

    /**
     * Text Field for aliment fat (in grams) per serving input.
     */
    @FXML private TextField fatTextField;

    /**
     * Text Field for aliment protein (in grams) per serving input.
     */
    @FXML private TextField proteinTextField;

    /**
     * Choice box for serving size unit.
     */
    @FXML private ChoiceBox<String> unitChoiceBox;

    /**
     * Check box for snack meal tendency.
     */
    @FXML private CheckBox snackChckBx;

    /**
     * Check box for lunch meal tendency.
     */
    @FXML private CheckBox lunchChckBx;

    /**
     * Check box for dinner meal tendency.
     */
    @FXML private CheckBox dinnerChckBx;

    /**
     * Check box for breakfast meal tendency.
     */
    @FXML private CheckBox breakfastChckBx;
    
    /**
     * The stage representing the GUI window.
     */
    private Stage stage;

    /**
     * The array of units available in unitChoiceBox.
     * - gram
     * - milliliter
     * - teaspoon
     * - tablespoon
     * - amount
     * - glasses
     */
    private String[] units = {"گرم", "میلی لیتر", "قاشق چایخوری", "قاشق غذاخوری", "عدد", "لیوان"};

    /**
     * This method is run when the Window is first created. Here, the text fields
     * are turned numeric and the choice box options are added.
     * 
     * @param url variable not used. Internally passed.
     * @param rb variable not used. Internally passed.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validation.createNumeric(calorieTextField);
        Validation.createNumeric(servingSizeTextField);
        
        unitChoiceBox.getItems().addAll(units);
    }
    
    /**
     * Method for controller to access its own stage. 
     * 
     * @param stage the GUI window. 
     */
    public void injectStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Button event handler which creates the aliment and adds it to the aliment.data
     * file. First validation checks are in place to ensure the correct data is
     * being entered. The created aliment is added to the data file using
     * FileManager.addToAlimentDataFile() Once it is added, the method closes the
     * window.
     */
    @FXML
    private void add() {
        Aliment aliment = new Aliment();
        List<Meal.TENDENCY> mealTendency = new ArrayList<>();
        
        if (Validation.isTxtFldEmpty(nameTextField) ||
            Validation.isTxtFldEmpty(servingSizeTextField) ||
            Validation.isTxtFldEmpty(fatTextField) ||
            Validation.isTxtFldEmpty(proteinTextField) ||
            Validation.isTxtFldEmpty(calorieTextField) ||
            Validation.isTxtFldEmpty(carbTextField)) {
            return;
        }
        
        if (unitChoiceBox.getSelectionModel().getSelectedItem() == null) {
            Validation.showWarningAlert("هیچ واحدی انتخاب نشده است");
            return;
        }
        
        if (breakfastChckBx.isSelected()) {
            mealTendency.add(Meal.TENDENCY.BREAKFAST);
        }
        if (lunchChckBx.isSelected()) {
            mealTendency.add(Meal.TENDENCY.LUNCH);
        }
        if (snackChckBx.isSelected()) {
            mealTendency.add(Meal.TENDENCY.SNACK);
        }
        if (dinnerChckBx.isSelected()) {
            mealTendency.add(Meal.TENDENCY.DINNER);
        }
        
        if (mealTendency.size() == 0) {
            Validation.showWarningAlert("این خوراکی مناسب چه وعده ها ای هست؟");
            return;
        }
        
        try {
            int calorieValue = Integer.parseInt(calorieTextField.getText());
            float proteinValue = Float.parseFloat(proteinTextField.getText());
            float carbValue = Float.parseFloat(carbTextField.getText());
            float fatValue = Float.parseFloat(fatTextField.getText());
            float calorieSum = proteinValue * 4f + carbValue * 4f + fatValue * 9f;
            // since each gram of protein has 4 calories
            // and each gram of carb has 4 calories
            // and each gram of fat has 9 calories

            // check whether the calorie goal matches the sum of the calories
            // of the macronutrients
            float calorieVariation = Math.abs( calorieValue - calorieSum ) / (float) calorieValue;

            if (calorieVariation > 0.1) {
                Validation.showWarningAlert("کالری پروتئین، کربوهیدرات و چربی وارد شده با هدف کالری وارد شده مطابقت ندارد!");
                return;
            }
            
            
            aliment.setName(nameTextField.getText());
            aliment.setCaloriePerServing(calorieValue);
            aliment.setServingSize(Float.parseFloat(servingSizeTextField.getText()));
            aliment.setServingUnit(unitChoiceBox.getSelectionModel().getSelectedItem());
            aliment.setCarbPerServing(carbValue);
            aliment.setFatPerServing(fatValue);
            aliment.setProteinPerServing(proteinValue);
            
            for (Meal.TENDENCY tendency : mealTendency) {
                aliment.addMealTendency(tendency);
            }
            
            
            FileManager.addToAlimentDataFile(aliment);
            stage.close();
            
        } catch (NumberFormatException e) {
            Validation.showWarningAlert("فقط ورودی اعداد مجاز است!");
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
    }
}
