package com.mycompany.dietgenerator;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import GeneticAlgorithm.Aliment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

/**
 * FXML Controller class for viewing aliment information window.
 * 
 * @author kxg708
 */
public class ViewAlimentInfoWindowController {
    /**
     * Pie chart for showing aliment macro-nutrients.
     */
    @FXML private PieChart pieChart;

    /**
     * Label for showing aliment carbohydrates (in grams) per serving
     */
    @FXML private Label carbLbl;

    /**
     * Label for showing aliment protein (in grams) per serving
     */
    @FXML private Label proteinLbl;

    /**
     * Label for showing aliment fat (in grams) per serving
     */
    @FXML private Label fatLbl;

    /**
     * Label for showing aliment name
     */
    @FXML private Label nameLbl;

    /**
     * Label for showing size of each serving of aliment
     */
    @FXML private Label servingSizeLbl;
    
    /**
     * Label for showing aliment calorie per serving
     */
    @FXML private Label caloriesPerServingLbl;
    
    /**
     * Method for controller to access the aliment whose information is being displayed. 
     * 
     * @param aliment the aliment whose information is being displayed.
     */
    public void injectAliment(Aliment aliment) {
        // safety check
        if (aliment == null) return;
        
        updateLabels(aliment);
        updatePieChart(aliment);
    }
    
    /**
     * Updates the pie chart data given an aliment
     * 
     * @param aliment the aliment whose information the pie chart shows.
     */
    private void updatePieChart(Aliment aliment) {
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(
                new PieChart.Data("پروتئین", aliment.getProteinPerServing() * 4),
                new PieChart.Data("کرب", aliment.getCarbPerServing() * 4),
                new PieChart.Data("چربی", aliment.getFatPerServing() * 9)
        );
        
        pieChart.setData(chartData);
    }
    
    /**
     * Updates the labels with the given aliment information.
     * 
     * @param aliment the aliment whose information the labels should show.
     */
    private void updateLabels(Aliment aliment) {
        nameLbl.setText("اسم خراکی: " + aliment.getName());
        proteinLbl.setText("پروتئین: " + String.valueOf(aliment.getProteinPerServing()) + " گرم");
        fatLbl.setText("چربی" + String.valueOf(aliment.getFatPerServing()) + " گرم");
        carbLbl.setText("کرب: " + String.valueOf(aliment.getCarbPerServing()) + " گرم");
        servingSizeLbl.setText("اندازه هر واحد: " + String.valueOf(aliment.getServingSize())
                                + " " + aliment.getServingUnit());
        caloriesPerServingLbl.setText("کالری هر واحد: " + aliment.getCaloriePerServing());
    }
}
