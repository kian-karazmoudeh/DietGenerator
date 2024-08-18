package GeneticAlgorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents an aliment, also known as food item
 *
 * @author kxg708
 */
public class Aliment implements Serializable, Cloneable {
    /**
     * Number of calories in each serving of the aliment
     */
    private int caloriePerServing;
    /**
     * Amount of protein (in grams) in each serving of the aliment
     */
    private float proteinPerServing;
    /**
     * Amount of carbohydrate (in grams) in each serving of the aliment
     */
    private float carbPerServing;
    /**
     * Amount of fat (in grams) in each serving of the aliment
     */
    private float fatPerServing;
    /**
     * The magnitude or size of each serving
     */
    private float servingSize;
    /**
     * The measurement unit of each serving
     * - grams
     * - milliliters
     * - teaspoons
     * - tablespoons
     * - glasses
     */
    private String servingUnit;
    /**
     * The number of servings of the aliment
     */
    private float numOfServings;
    /**
     * A list of meal tendencies in which the aliment is suitable to be used. It is a
     * list since one aliment may be suitable for many meals.
     */
    private List<Meal.TENDENCY> mealTendency = new ArrayList<>();
    /**
     * Name of the aliment
     */
    private String name;
    
    
    /**
     * Getter for aliment name
     * 
     * @return aliment name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for aliment name
     * 
     * @param name the new name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Getter for calories per serving of the aliment
     * 
     * @return calories per serving of the aliment
     */
    public int getCaloriePerServing() {
        return caloriePerServing;
    }

    /**
     * Setter for calories per serving of the aliment
     * 
     * @param caloriePerServing the new calories per serving of the aliment
     */
    public void setCaloriePerServing(int caloriePerServing) {
        this.caloriePerServing = caloriePerServing;
    }

    /**
     * Getter for protein per serving of the aliment
     * 
     * @return the protein per serving of the aliment
     */
    public float getProteinPerServing() {
        return proteinPerServing;
    }

    /**
     * Setter for protein per serving of the aliment
     * 
     * @param proteinPerServing the new protein per serving of the aliment
     */
    public void setProteinPerServing(float proteinPerServing) {
        this.proteinPerServing = proteinPerServing;
    }

    /**
     * Getter for carbohydrate per serving of the aliment
     * 
     * @return carbohydrate per serving of the aliment
     */
    public float getCarbPerServing() {
        return carbPerServing;
    }

    /**
     * Setter for carbohydrate per serving of the aliment
     * 
     * @param carbPerServing the new carbohydrate per serving of the aliment
     */
    public void setCarbPerServing(float carbPerServing) {
        this.carbPerServing = carbPerServing;
    }
    
    /**
     * Getter for fat per serving of the aliment
     * 
     * @return the fat per serving of the aliment
     */
    public float getFatPerServing() {
        return fatPerServing;
    }

    /**
     * Setter for fat per serving of the aliment
     * 
     * @param fatPerServing the new fat per serving of the aliment 
     */
    public void setFatPerServing(float fatPerServing) {
    this.fatPerServing = fatPerServing;
    }
    /**
     * Getter for meal tendency list
     * 
     * @return the current meal tendency list
     */
    public List<Meal.TENDENCY> getMealTendency() {
        return mealTendency;
    }

    /**
     * Add a new meal tendency to the existing list
     * 
     * @param mealTendency the new meal tendency to be added
     */
    public void addMealTendency(Meal.TENDENCY mealTendency) {
        this.mealTendency.add(mealTendency);
    }

    /**
     * Getter for serving size of the aliment
     * 
     * @return the serving size of the aliment
     */
    public float getServingSize() {
        return servingSize;
    }
    /**
     * Setter for serving size of the aliment
     * 
     * @param servingSize the new serving size of the aliment
     */
    public void setServingSize(float servingSize) {
        this.servingSize = servingSize;
    }

    /**
     * Getter for serving unit of the aliment
     * 
     * @return the serving unit of the aliment
     */
    public String getServingUnit() {
        return servingUnit;
    }

    /**
     * Setter for serving unit of the aliment
     * 
     * @param servingUnit the new serving unit of the aliment
     */
    public void setServingUnit(String servingUnit) {
        this.servingUnit = servingUnit;
    }
    /**
     * Getter for number of servings of the aliment
     * 
     * @return the number of servings of the aliment
     */
    public float getNumOfServings() {
        return numOfServings;
    }

    /**
     * Setter for number of servings of the aliment
     * 
     * @param numOfServings the new number of servings of the aliment
     */
    public void setNumOfServings(float numOfServings) {
        this.numOfServings = numOfServings;
    }
    
    /**
     * turns the aliment to a string representation by returning the name
     * of the aliment.
     * 
     * @return the name of the aliment
     */
    @Override
    public String toString() {
        return name; 
    }
    
    /**
     * clones the aliment object into another object with a different
     * address in memory, so that the clone can be modified.
     * 
     * @return cloned object
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
	    return super.clone();
    }
    
    /**
     * Checks whether the current aliment is equal to another aliment
     * After safety checks, the comparison is done by checking aliment names.
     * 
     * @param object is the object being compared.
     * @return a Boolean representing whether the two objects are equal.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        
        Aliment aliment = (Aliment) object;
        return name.equals(aliment.getName());
    }
    
}
