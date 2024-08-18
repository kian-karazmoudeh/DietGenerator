package com.mycompany.dietgenerator;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class goals calculator window. The class takes in
 * the goals text fields and populates them with the calculated values.
 * 
 * for sedentary: little to no exercise multiply by a factor of 1.2
 * for light exercise multiply by a factor of 1.375
 * for moderate exercise multiply by a factor of 1.46
 * for active exercise multiply by a factor of 1.55
 * for very active multiply by a factor of 1.75
 * for extra active multiply by a factor of 1.9
 * 
 * 
 * for mild weight gain: +250 calories
 * for weight gain: +500 calories
 * for extreme weight gain: +1000 calories
 * 
 * for mild weight loss: -250 calories
 * for weight loss: -500 calories
 * for extreme weight loss: -1000 calories
 * 
 * @author kxg708
 */
public class GoalsCalculatorWindowController implements Initializable {
    /**
     * TextField for height.
     */
    @FXML private TextField heightTextField;

    /**
     * TextField for age.
     */
    @FXML private TextField ageTextField;

    /**
     * TextField for weight.
     */
    @FXML private TextField weightTextField;
    
    /**
     * RadioButton for light activity level.
     */
    @FXML private RadioButton rBtnActivityLvlLight;

    /**
     * RadioButton for moderate activity level.
     */
    @FXML private RadioButton rBtnActivityLvlModerate;

    /**
     * RadioButton for active activity level.
     */
    @FXML private RadioButton rBtnActivityLvlActive;
    
    /**
     * RadioButton for female gender
     */
    @FXML private RadioButton rBtnFemale;

    /**
     * RadioButton for male gender
     */
    @FXML private RadioButton rBtnMale;
    
    /**
     * ToggleGroup for activity.
     */
    @FXML private ToggleGroup activity;

    /**
     * ToggleGroup for gender.
     */
    @FXML private ToggleGroup gender;

    /**
     * ToggleGroup for goals.
     */
    @FXML private ToggleGroup goals;
    
    /**
     * RadioButton for extreme weight loss goal.
     */
    @FXML private RadioButton rBtnExtremeWeightLoss;

    /**
     * RadioButton for mild weight loss goal.
     */
    @FXML private RadioButton rBtnMildWeightLoss;

    /**
     * RadioButton for weight loss goal.
     */
    @FXML private RadioButton rBtnWeightLoss;

    /**
     * RadioButton for weight maintenance goal.
     */
    @FXML private RadioButton rBtnWeightMaintenance;

    /**
     * RadioButton for extreme weight gain goal.
     */
    @FXML private RadioButton rBtnExtremeWeightGain;

    /**
     * RadioButton for mild weight gain goal.
     */
    @FXML private RadioButton rBtnMildWeightGain;

    /**
     * RadioButton for weight gain goal.
     */
    @FXML private RadioButton rBtnWeightGain;
    
    /**
     * The stage representing the GUI window.
     */
    private Stage stage;

    /**
     * TextField for calorie input to be populated.
     */
    private TextField calorieTextField;

    /**
     * TextField for carbohydrate input to be populated.
     */
    private TextField carbTextField;

    /**
     * TextField for protein input to be populated.
     */
    private TextField proteinTextField;

    /**
     * TextField for fat input to be populated.
     */
    private TextField fatTextField;
    
    /**
     * Variable for calculated calorie goal.
     */
    private int calorieGoal;

    /**
     * Variable for calculated protein goal.
     */
    private float proteinGoal;

    /**
     * Variable for calculated carbohydrate goal.
     */
    private float carbGoal;

    /**
     * Variable for calculated fat goal.
     */
    private float fatGoal;
    
    /**
     * This method is run when the Window is first created. Here,
     * the height, age, and weight text fields are turned numeric.
     * 
     * @param url variable not used. Internally passed.
     * @param rb variable not used. Internally passed.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Validation.createNumeric(heightTextField);
        Validation.createNumeric(ageTextField);
        Validation.createNumeric(weightTextField);
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
     * Method for controller to access goal Text Fields. 
     * 
     * @param calorieTextField the calorie Text Field to be populated. 
     * @param proteinTextField the protein Text Field to be populated. 
     * @param carbTextField the carbohydrate Text Field to be populated. 
     * @param fatTextField the fat Text Field to be populated.
     */
    public void injectFields(TextField calorieTextField, TextField proteinTextField, TextField carbTextField, TextField fatTextField) {
        this.calorieTextField = calorieTextField;
        this.proteinTextField = proteinTextField;
        this.carbTextField = carbTextField;
        this.fatTextField = fatTextField;
    }
    
    /**
     * Method which uses specified text and radio button
     * inputs and calculates the calorie goal, and saves it
     * in this.calorieGoal.
     */
    private void calcCalorieGoal() {
        float bmrConst, activityConst, goalConst;
        
        if (rBtnMale.isSelected()) {
            bmrConst = 5;
        } else if (rBtnFemale.isSelected()) {
            bmrConst = -161;
        } else {
            // translation: please fill out all fields!
            Validation.showWarningAlert("لطفا تمام اطلاعات را پر کنید!");
            return;
        }
        
        // activity constants obtained from https://www.calculator.net/calorie-calculator.html
        if (rBtnActivityLvlLight.isSelected()) {
            activityConst = 1.375f;
        } else if (rBtnActivityLvlModerate.isSelected()) {
            activityConst = 1.46f;
        } else if (rBtnActivityLvlActive.isSelected()) {
            activityConst = 1.55f;
        } else {
            // translation: please fill out all fields!
            Validation.showWarningAlert("لطفا تمام اطلاعات را پر کنید!");
            return;
        }
        
        // goal constants obtained from https://www.calculator.net/calorie-calculator.html
        if (rBtnExtremeWeightLoss.isSelected()) {
            goalConst = -1000;
        } else if (rBtnWeightLoss.isSelected()) {
            goalConst = -500;
        } else if (rBtnMildWeightLoss.isSelected()) {
            goalConst = -250;
        } else if (rBtnWeightMaintenance.isSelected()) {
            goalConst = 0;
        } else if (rBtnMildWeightGain.isSelected()) {
            goalConst = 250;
        } else if (rBtnWeightGain.isSelected()) {
            goalConst = 500;
        } else if (rBtnExtremeWeightGain.isSelected()) {
            goalConst = 1000;
        } else {
            // translation: please fill out all fields!
            Validation.showWarningAlert("لطفا تمام اطلاعات را پر کنید!");
            return;
        }
            
        float height = Float.parseFloat(heightTextField.getText());
        float age = Float.parseFloat(ageTextField.getText());
        float weight = Float.parseFloat(weightTextField.getText());
          
        float BMR = 10.0f*weight + 6.25f*height - 5.0f*age + bmrConst;
        
        float dailyCalorieIntake = BMR * activityConst;
        calorieGoal = Math.round(dailyCalorieIntake + goalConst);
    }
    
    /**
     * Method which uses specified text and radio button inputs and calculates
     * the macro-nutrient goals, and saves it in the appropriate data members.
     */
    private void calcMacroGoals() {
        float weight = Float.valueOf(weightTextField.getText());
        if (rBtnExtremeWeightLoss.isSelected()) {
            proteinGoal = weight * 2.2f * 1.2f;
        } else if (rBtnMildWeightLoss.isSelected()) {
            proteinGoal = weight * 2.2f * 1.2f;
        } else if (rBtnWeightLoss.isSelected()) {
            proteinGoal = weight * 2.2f * 1f;
        } else if (rBtnWeightMaintenance.isSelected()) {
            proteinGoal = weight * 2.2f * 1f;
        } else if (rBtnMildWeightGain.isSelected()) {
            proteinGoal = weight * 2.2f * 0.9f;
        } else if (rBtnWeightGain.isSelected()) {
            proteinGoal = weight * 2.2f * 0.8f;
        } else if (rBtnExtremeWeightGain.isSelected()) {
            proteinGoal = weight * 2.2f * 0.8f;
        }
        fatGoal = weight * 2.2f * 0.6f;  
        carbGoal = (calorieGoal - proteinGoal*4f - fatGoal*9f) / 4f;
    }

    /**
     * Button event handler which first validates the inputs and then gets
     * the calculated goals, and populates the injected Text Fields. It then
     * closes the window.
     */
    @FXML
    private void calculate() throws IOException {
        if (Validation.isTxtFldEmpty(heightTextField) ||
            Validation.isTxtFldEmpty(ageTextField) ||
            Validation.isTxtFldEmpty(weightTextField) ||
            !Validation.isTxtFldNumeric(heightTextField) ||
            !Validation.isTxtFldNumeric(ageTextField) ||
            !Validation.isTxtFldNumeric(weightTextField) ||
            !Validation.isRdBtnSelected(activity) ||
            !Validation.isRdBtnSelected(goals) ||
            !Validation.isRdBtnSelected(gender)) {
            return;
        }
         
        try {
            calcCalorieGoal();
            calcMacroGoals();
            
            calorieTextField.setText(String.valueOf(calorieGoal));
            proteinTextField.setText(String.valueOf(Math.round(proteinGoal)));
            fatTextField.setText(String.valueOf(Math.round(fatGoal)));
            carbTextField.setText(String.valueOf(Math.round(carbGoal)));
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
        
        stage.close();
    }
}