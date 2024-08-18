package com.mycompany.dietgenerator;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import GeneticAlgorithm.Aliment;
import GeneticAlgorithm.Meal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class for aliment selector window. When the window is created,
 * a list object is passed to this controller. The selected aliment is then added
 * to that list. Examples include the likes list view and the dislikes list view.
 * 
 * @author kxg708
 */
public class AlimentSelectorWindowController implements Initializable {
    /**
     * ListView for search results
     */
    @FXML private ListView<Aliment> searchList;

    /**
     * Text Field for search input
     */
    @FXML private TextField searchField;

    /**
     * Text Field for number of servings input (only visible when servingSelectable
     * is true)
     */
    @FXML private TextField numOfServingsTxtField;

    /**
     * CheckBox for filtering breakfast aliments
     */
    @FXML private CheckBox breakfastFilterChckBx;

    /**
     * CheckBox for filtering dinner aliments
     */
    @FXML private CheckBox dinnerFilterChckBx;

    /**
     * CheckBox for filtering lunch aliments
     */
    @FXML private CheckBox lunchFilterChckBx;

    /**
     * CheckBox for filtering snack aliments
     */
    @FXML private CheckBox snackFilterChckBx;

    /**
     * GUI Label (only visible when servingSelectalbe is true.
     */
    @FXML private Label servingSizeLbl;

    
    /**
     * The list which contains the contents of searchList.
     */
    private ObservableList<Aliment> observableList;

    /**
     * The stage representing the GUI window.
     */
    private Stage stage;

    /**
     * A flag representing whether the selector can specify number of servings or
     * not. It is true when manually modifying the meal options.
     */
    private boolean servingSelectable;
    
    /**
     * This method is run when the Window is first created. Here, the global variable
     * App.aliments is added as the contents of the search list view. However, once
     * search and filter takes place, the observable list will be used. Moreover, a
     * context menu is added to the search list view and the servingSelectable flag is
     * automatically set to false.
     * 
     * @param url variable not used. Internally passed.
     * @param rb variable not used. Internally passed.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.servingSelectable = false;
        searchList.getItems().addAll(App.aliments);
        addContextMenu(searchList);
    }
    
    /**
     * Utility method which takes in a ListView and adds a JavaFX context menu to it.
     * 
     * @param listView the ListView object to which the context menu should be added.
     */
    private void addContextMenu(ListView<Aliment> listview) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("حذف");
        deleteItem.setOnAction(e -> {
            Aliment selectedAliment = listview.getSelectionModel().getSelectedItem();
            listview.getItems().remove(selectedAliment);
            FileManager.FileManager.removeFromAlimentDataFile(selectedAliment);
        });
        
        MenuItem addItem = new MenuItem("خوراکی جدید");
        addItem.setOnAction(e -> {
            swtichToAddNewAliment();
        });
        
        MenuItem viewItemInfo = new MenuItem("اطلاعات خوراکی");
        viewItemInfo.setOnAction(e -> {
            try {
                Aliment selectedAliment = listview.getSelectionModel().getSelectedItem();
                
                Stage stage = new Stage();
                FXMLLoader window = App.getWindow("viewAlimentInfoWindow");
                
                Scene scene = new Scene(window.load());
                stage.setScene(scene);
                
                ViewAlimentInfoWindowController controller = window.getController();
                controller.injectAliment(selectedAliment);
                stage.show();
                
            } catch (Exception error) {
                Validation.showErrorAlert(error);
            }
        });
        
        contextMenu.getItems().addAll(deleteItem, addItem, viewItemInfo);

        listview.setContextMenu(contextMenu);
    }
    
    /**
     * Method which takes in a search and list of foods, and returns another list
     * which contains the matching aliments. This search code was inspired by:
     * https://youtu.be/VUVqamT8Npc?feature=shared
     * 
     * @param searchWords the search prompt.
     * @param listOfFoods the list of aliments to search.
     * @return a list of aliments which match the search prompt.
     */
    private List<Aliment> searchList(String searchWords, List<Aliment> listOfFoods) {
        List<String> searchWordsArray = Arrays.asList(searchWords.split(" "));
        
        ArrayList<Meal.TENDENCY> filters = new ArrayList<>();
        if (breakfastFilterChckBx.isSelected()) filters.add(Meal.TENDENCY.BREAKFAST);
        if (lunchFilterChckBx.isSelected()) filters.add(Meal.TENDENCY.LUNCH);
        if (snackFilterChckBx.isSelected()) filters.add(Meal.TENDENCY.SNACK);
        if (dinnerFilterChckBx.isSelected()) filters.add(Meal.TENDENCY.DINNER);
        
        return listOfFoods.stream().filter(inputs -> {
            if (filters.isEmpty()) {
                return searchWordsArray.stream().allMatch(
                        word -> inputs.getName().toLowerCase().contains(word.toLowerCase()));
            }
            for (Meal.TENDENCY filter : filters) {
                if (inputs.getMealTendency().contains(filter)) {
                    return searchWordsArray.stream().allMatch(
                        word -> inputs.getName().toLowerCase().contains(word.toLowerCase()));
                }
            }
            return false;
        }).collect(Collectors.toList());
    }
    
    /**
     * Method to access the list to which the selected aliment should be added.
     * For instance, the list from likesListView or dislikesListView.
     * 
     * @param list the list which is being injected.
     */
    public void injectObservableList(ObservableList<Aliment> list) {
        this.observableList = list;
    }
    
    /**
     * Method to set servingSelectable true or false. It also determines the
     * visibility of servingSizeLbl and numOsServingsTxtField
     * 
     * @param selectable value of the servingSelectable Boolean.
     */
    public void isServingSelectable(boolean selectable) {
        this.servingSelectable = selectable;
        numOfServingsTxtField.setVisible(selectable);
        servingSizeLbl.setVisible(selectable);
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
     * Key press event handler which runs the search method and updates the searchList.
     * This method gets run each time a new key in the search prompt is pressed.
     */
    @FXML
    private void search() {
        searchList.getItems().clear();
        searchList.getItems().addAll(searchList(searchField.getText(), App.aliments));
    }
    
    /**
     * Button event handler which opens an add new aliment window.
     */
    @FXML
    private void swtichToAddNewAliment() {
        try {
            FXMLLoader loader = App.getWindow("addNewAlimentWindow");
            stage.getScene().setRoot(loader.load());
            AddNewAlimentWindowController controller = loader.getController();
            controller.injectStage(stage);
            
            
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
    }
    
    /**
     * Button event handler which takes the selected aliment and adds it to the
     * injected observableList.
     */
    @FXML
    private void selectAliment() throws CloneNotSupportedException {
        Aliment selectedAliment = searchList.getSelectionModel().getSelectedItem();
        
        if (selectedAliment == null) {
            // translation: no aliment selected!
            Validation.showWarningAlert("هیچ خوراکی انتخاب نشده!");
        } else if (servingSelectable && numOfServingsTxtField.getText().isBlank()) {
            // translation: please enter the number of servings.
            Validation.showWarningAlert("لطفا تعداد وعده ها را وارد کنید!");
        } else {
            selectedAliment = (Aliment) selectedAliment.clone();
            if (servingSelectable) {
                try {
                    selectedAliment.setNumOfServings(Float.parseFloat(numOfServingsTxtField.getText()));
                } catch (NumberFormatException e) {
                    // translation: only number inputs are allowed.
                    Validation.showWarningAlert("فقط ورودی اعداد مجاز است!");
                    return;
                } catch (Exception e) {
                    Validation.showErrorAlert(e);
                    return;
                }
            }
            observableList.add(selectedAliment);

            stage.close();
        }
    }
    
}
