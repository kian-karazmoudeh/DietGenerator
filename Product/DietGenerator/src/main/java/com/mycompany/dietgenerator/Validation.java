package com.mycompany.dietgenerator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;


/**
 * Static class containing methods for validation of inputs and error handling.
 * 
 * @author kxg708
 */
public class Validation {
    
    /**
     * Method which creates a pop up which displays error message
     * 
     * @param e the Exception object which contains stackTrace and message.
     */
    public static void showErrorAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("error");
        alert.setHeaderText("خطایی رخ داد");
        alert.setContentText(e.getMessage());
            
        e.printStackTrace();
        alert.showAndWait();
    }
    
    /**
     * Method which creates a warning pop up with custom message
     * 
     * @param message the custom message to be displayed
     */
    public static void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("warning");
        alert.setHeaderText("هشدار!");
        alert.setContentText(message);
        
        alert.showAndWait();
    }
    
    /**
     * Method which takes a TextField and adds a property which only allows numeric input.
     * Taken from source: https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
     * 
     * @param txtFld the TextField to which the property is being added
     */
    public static void createNumeric(TextField txtFld) {
        txtFld.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtFld.setText(newValue.replaceAll("[^\\d*]", ""));
                }
            }
        });
    }
    
    /**
     * Method which takes a Text Field and checks whether it is empty. If it is, a
     * warning is displayed.
     * 
     * @param txtFld The Text Field being checked.
     * @return true if empty, and false if contains text.
     */
    public static boolean isTxtFldEmpty(TextField txtFld) {
        if (txtFld.getText().isBlank()) {
            // translation of text: please fill out all fields
            showWarningAlert("لطفا تمام ورودی ها را پر کنید!");
            return true;
        }
        return false;
    }
    
    /**
     * Method which takes a Text Field and checks whether it contains numeric values.
     * If it isn't, a warning is displayed.
     * 
     * @param txtFld The Text Field being checked.
     * @return true if numeric, and false if contains text.
     */
    public static boolean isTxtFldNumeric(TextField txtFld) {
        if (!txtFld.getText().matches("\\d*")) {
            // translation of text: only numbers must be entered
            showWarningAlert("فقط ورودی اعداد مجاز است!");
            return false;
        }
        
        return true;
    }
    
    /**
     * Method which checks if a radio button from an inputted Toggle Group has
     * been selected or not. If no button is selected a warning is displayed.
     * 
     * @param group Toggle Group being checked
     * @return true if a button is selected, and false if no radio button is selected
     */
    public static boolean isRdBtnSelected(ToggleGroup group) {
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        if (selected == null) {
            // translation of text: please select an option!
            showWarningAlert("لطفا یک گزینه را انتخاب کنید!");
            return false;
        }
        return true;
    }
}
