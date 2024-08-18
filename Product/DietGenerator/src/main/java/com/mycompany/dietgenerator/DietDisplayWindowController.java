package com.mycompany.dietgenerator;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import FileManager.FileManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import GeneticAlgorithm.MealOption;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import GeneticAlgorithm.Diet;
import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * FXML Controller class for diet display window.
 * 
 * @author kxg708
 */
public class DietDisplayWindowController implements Initializable {
    /**
     * The stage representing the GUI window.
     */
    private Stage stage;

    /**
     * The Diet object being displayed
     */
    private Diet diet;
    
    /**
     * ListView for breakfast meal options
     */
    @FXML private ListView<MealOption> breakfast;

    /**
     * ListView for snack meal options
     */
    @FXML private ListView<MealOption> snack;

    /**
     * ListView for lunch meal options
     */
    @FXML private ListView<MealOption> lunch;

    /**
     * ListView for dinner meal options
     */
    @FXML private ListView<MealOption> dinner;

    /**
     * Button for saving diet file. It is disabled if the diet has been newly
     * generated and has not been previously saved before.
     */
    @FXML private MenuItem saveBtn;
    
    /**
     * This method is run when the Window is first created. Here, the meal
     * list views are set to cell wrap, and a JavaFX context menu is added
     * to them.
     * 
     * @param url variable not used. Internally passed.
     * @param rb variable not used. Internally passed.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        setCellWrap(breakfast);
        setCellWrap(snack);
        setCellWrap(lunch);
        setCellWrap(dinner);
        
        addContextMenu(breakfast);
        addContextMenu(snack);
        addContextMenu(lunch);
        addContextMenu(dinner);
    }

    /**
     * Utility method which takes in a ListView and adds a JavaFX context menu to it.
     * 
     * @param listView the ListView object to which the context menu should be added.
     */
    private void addContextMenu(ListView<MealOption> listview) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("ویرایش");
        editItem.setOnAction(e -> {
            try {
                MealOption selectedMealOption = listview.getSelectionModel().getSelectedItem();
                Stage mealOptionEditorWindow = new Stage();
                FXMLLoader window = App.getWindow("mealOptionEditorWindow");
                Scene scene = new Scene(window.load());
                
                
                MealOptionEditorWindowController controller = window.getController();
                controller.injectMealOption(selectedMealOption, listview);
                
                mealOptionEditorWindow.setScene(scene);
                mealOptionEditorWindow.show();
            } catch (Exception exception) {
                Validation.showErrorAlert(exception);
            }
            
        });
        
        MenuItem refreshItem = new MenuItem("بازسازی");
        refreshItem.setOnAction(e -> {
            try {
                MealOption selectedMealOption = listview.getSelectionModel().getSelectedItem();
                
                selectedMealOption.run();
                listview.refresh();
                
            } catch (Exception exception) {
                Validation.showErrorAlert(exception);
            }
            
        });

        MenuItem deleteItem = new MenuItem("حذف");
        deleteItem.setOnAction(e -> {
            MealOption selectedAliment = listview.getSelectionModel().getSelectedItem();
            listview.getItems().remove(selectedAliment);
        });

        contextMenu.getItems().addAll(editItem, deleteItem, refreshItem);

        listview.setContextMenu(contextMenu);
    }
    
    /**
     * Method to make the cells of a ListView to wrap when the text overflows.
     * 
     * AI (ChatGPT) generated:
     * prompt: I have a listView in JavaFX, and I need a way to make the cells
     * wrap to the next line when the text overflows
     * 
     * @param listView the listView to on which to set this property
     */
    private void setCellWrap(ListView<MealOption> listView) {
        listView.setCellFactory(param -> new ListCell<MealOption>() {
            private Text text;
            {
                text = new Text();
                text.wrappingWidthProperty().bind(listView.widthProperty().subtract(50));
            }
            @Override
            protected void updateItem(MealOption item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    text.setText(item.toString());
                    setGraphic(text);                    
                }
            }
        });
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
     * Method to inject the diet that is being displayed, into controller. 
     * 
     * @param diet diet being displayed by the window. 
     */
    public void injectDiet(Diet diet) {
        this.diet = diet;
        breakfast.getItems().addAll(diet.getBreakfast());
        snack.getItems().addAll(diet.getSnack());
        lunch.getItems().addAll(diet.getLunch());
        dinner.getItems().addAll(diet.getDinner());
        
        updateWindowOnDietSave();
    }
    
    /**
     * Method for updating the window title and toggling the saveBtn availability
     */
    private void updateWindowOnDietSave() {
        if (diet.filename.equals("undefined")) {
            stage.setTitle("diet display window: undefined");
            saveBtn.setDisable(true);
        } else {
            stage.setTitle("diet display window: " + diet.filename);
            saveBtn.setDisable(false);
        }
    }
    
    /**
     * Button event handler which closes the window
     */
    @FXML
    private void close() {
        stage.close();
    }
    
    /**
     * Button event handler which saves the diet in a new diet file.
     */
    @FXML
    private void saveAsDiet() {
        FileManager.saveAsDietFile(diet);
        updateWindowOnDietSave();
    }
    
    /**
     * Button event handler which saves modifications to a previously
     * saved diet file.
     */
    @FXML
    private void save() {
        try {
            File file = new File(diet.path);
            FileManager.saveDietFile(file, diet);
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
        
    }
    
    /**
     * Button event handler which saves the diet as a PDF file.
     */
    @FXML
    private void saveAsPDF() {
        FileManager.saveAsPDF(diet);
    }
}
