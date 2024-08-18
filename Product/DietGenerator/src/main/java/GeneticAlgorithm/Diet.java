package GeneticAlgorithm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instantiation of this class represents a complete diet with all four meals
 * (Breakfast, Snack, Lunch, and Dinner). When the user saves a diet as an editable
 * file, an object of this class is saved.
 *
 * @author kxg708
 */
public class Diet implements Serializable {
    /**
     * A list of mealOptions in breakfast
     */
    public ArrayList<MealOption> breakfast;

    /**
     * A list of mealOptions in lunch
     */
    private ArrayList<MealOption> lunch;

    /**
     * A list of mealOptions in snack
     */
    private ArrayList<MealOption> snack;

    /**
     * A list of mealOptions in dinner
     */
    private ArrayList<MealOption> dinner;
    
    /**
     * The name of the editable file. When a new diet is generated, the initial
     * value for this variable is "undefined". it can modified once the user saves
     * it as an editable file. The file name is also displayed in the title of the
     * diet display window.
     */
    public String filename = "undefined";

    /**
     * The path of the editable file. When a new diet is generated, the initial
     * value for this variable is "undefined". it can modified once the user saves
     * it as an editable file.
     */
    public String path = "undefined";
    
    /**
     * The constructor of the Diet class. It takes in the meal options of each meal
     * and assigns them to the corresponding private data members.
     * 
     * @param breakfast a list of mealOptions in the breakfast meal.
     * @param lunch a list of mealOptions in the lunch meal.
     * @param snack a list of mealOptions in the snack meal.
     * @param dinner a list of mealOptions in the dinner meal.
     */
    public Diet(ArrayList<MealOption> breakfast, ArrayList<MealOption> lunch, ArrayList<MealOption> snack, ArrayList<MealOption> dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.snack = snack;
        this.dinner = dinner;
    }
    
    /**
     * Getter for breakfast mealOptions
     * 
     * @return the list of mealOptions in breakfast.
     */
    public ArrayList<MealOption> getBreakfast() {
        return breakfast;
    }

    /**
     * Setter for the breakfast mealOptions
     * 
     * @param breakfast the new list of meal options in breakfast.
     */
    public void setBreakfast(ArrayList<MealOption> breakfast) {
        this.breakfast = breakfast;
    }

    /**
     * Getter for lunch mealOptions
     * 
     * @return the list of mealOptions in lunch.
     */
    public ArrayList<MealOption> getLunch() {
        return lunch;
    }

    /**
     * Setter for the lunch mealOptions
     * 
     * @param lunch the new list of meal options in lunch.
     */
    public void setLunch(ArrayList<MealOption> lunch) {
        this.lunch = lunch;
    }

    /**
     * Getter for snack mealOptions
     * 
     * @return the list of mealOptions in snack.
     */
    public ArrayList<MealOption> getSnack() {
        return snack;
    }

    /**
     * Setter for the snack mealOptions
     * 
     * @param snack the new list of meal options in snack.
     */
    public void setSnack(ArrayList<MealOption> snack) {
        this.snack = snack;
    }

    /**
     * Getter for dinner mealOptions
     * 
     * @return the list of mealOptions in dinner.
     */
    public ArrayList<MealOption> getDinner() {
        return dinner;
    }

    /**
     * Setter for the dinner mealOptions
     * 
     * @param dinner the new list of meal options in dinner.
     */
    public void setDinner(ArrayList<MealOption> dinner) {
        this.dinner = dinner;
    }
}
