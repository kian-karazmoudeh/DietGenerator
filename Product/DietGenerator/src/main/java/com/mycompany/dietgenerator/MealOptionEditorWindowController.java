package com.mycompany.dietgenerator;

import GeneticAlgorithm.MealOption;
import GeneticAlgorithm.Aliment;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class for meal option editor window. Meal option modification
 * is done by modifying a table of aliments, and then adding that table's items
 * to the meal option whenever the table is modified.
 * 
 * @author kxg708
 */
public class MealOptionEditorWindowController implements Initializable {
    /**
     * The meal option being edited. 
     */
    private MealOption mealOption;

    /**
     * The ListView from which the meal option came from.
     */
    private ListView<MealOption> mealOptionListView;

    /**
     * Table which shows the information of each aliment in the meal option.
     */
    @FXML private TableView<Aliment> table;

    /**
     * Calorie per serving column in table
     */
    @FXML private TableColumn<Aliment, Integer> calorieColumn;

    /**
     * Fat per serving (in grams) column in table
     */
    @FXML private TableColumn<Aliment, Float> fatColumn;

    /**
     * Name column in table
     */
    @FXML private TableColumn<Aliment, Float> nameColumn;

    /**
     * Number of servings column in table
     */
    @FXML private TableColumn<Aliment, Float> numOfServingsColumn;

    /**
     * Protein per serving (in grams) column in table
     */
    @FXML private TableColumn<Aliment, Float> proteinColumn;

    /**
     * Carbohydrate per serving (in grams) column in table
     */
    @FXML private TableColumn<Aliment, Float> carbColumn;

    /**
     * Label showing total calories of Meal Option
     */
    @FXML private Label totalCaloriesLabel;

    /**
     * Label showing total carbohydrates (in grams) of Meal Option
     */
    @FXML private Label totalCarbLabel;

    /**
     * Label showing total fat (in grams) of Meal Option
     */
    @FXML private Label totalFatLabel;

    /**
     * Label showing total protein (in grams) of Meal Option
     */
    @FXML private Label totalProteinLabel;

    /**
     * This method is run when the Window is first created. In this method
     * the table columns are set using setCellValueFactory
     * 
     * @param url variable not used. Internally passed.
     * @param rb variable not used. Internally passed.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        calorieColumn.setCellValueFactory(new PropertyValueFactory<>("caloriePerServing"));
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("proteinPerServing"));
        fatColumn.setCellValueFactory(new PropertyValueFactory<>("fatPerServing"));
        numOfServingsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfServings"));
        carbColumn.setCellValueFactory(new PropertyValueFactory<>("carbPerServing"));
    }
    
    /**
     * Method which injects the meal option being edited and the list view from which
     * the meal option was taken, so that the list view can be updated as well.
     * 
     * @param mealOption the MealOption object being manually modified.
     * @param mealOptionListView the ListView from which the meal option was taken.
     */
    public void injectMealOption(MealOption mealOption, ListView<MealOption> mealOptionListView) {
        this.mealOption = mealOption;
        this.mealOptionListView = mealOptionListView;
        table.getItems().addAll(mealOption.getAliments());
        
        updateTotalLabels();
        
        // AI (ChatGPT) generated
        table.getItems().addListener((ListChangeListener.Change<? extends Aliment> change) -> {
            updateTotalLabels();
            updateMealOption();
        });
    }
    
    /**
     * Method which calculates the sum of the calorie, protein, carbohydrate, and fat
     * contents of the meal option by multiplying the number of servings of each aliment
     * by the values per serving, and updates the labels with these values.
     */
    private void updateTotalLabels() {
        double calorieSum = 0;
        double proteinSum = 0;
        double carbSum = 0;
        double fatSum = 0;
        
        for (Aliment aliment : table.getItems()) {
            calorieSum += aliment.getCaloriePerServing() * aliment.getNumOfServings();
            proteinSum += aliment.getProteinPerServing() * aliment.getNumOfServings();
            carbSum += aliment.getCarbPerServing() * aliment.getNumOfServings();
            fatSum += aliment.getFatPerServing() * aliment.getNumOfServings();
        }
        
        totalCaloriesLabel.setText("کالری کل: " + Math.round(calorieSum));
        totalProteinLabel.setText("پروتئین کل: " + Math.round(proteinSum) + " گرم");
        totalCarbLabel.setText("کربوهیدرات کل: " + Math.round(carbSum) + " گرم");
        totalFatLabel.setText("چربی کل: " + Math.round(fatSum) + " گرم");
    }
    
    /**
     * Button event handler which removes an aliment from the meal option.
     */
    @FXML
    private void removeAliment() {
        Aliment selectedAliment = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedAliment);
    }

    /**
     * Button event handler which opens an aliment selector with the serving selectable
     * flag set to true, and adds the selected aliment to the meal option. It also passes
     * the table.getItems() list to the aliment selector controller.
     */
    @FXML
    private void addAliment() throws IOException {
        FXMLLoader window = App.getWindow("alimentSelectorWindow");
        Stage stage = new Stage();
        Scene scene = new Scene(window.load());
        stage.setScene(scene);
        AlimentSelectorWindowController controller = window.getController();
        controller.isServingSelectable(true);
        controller.injectObservableList(table.getItems());
        controller.injectStage(stage);
        stage.show();
    }
    
    /**
     * Utility function which takes the modified list of aliment, and adds it to the meal option.
     * It also refreshes the mealOption list view so changes are displayed.
     */
    private void updateMealOption() {
        mealOption.addAll(new ArrayList<>(table.getItems()));
        mealOptionListView.refresh();
    }
    
}
