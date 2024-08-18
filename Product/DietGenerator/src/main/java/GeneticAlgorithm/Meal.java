package GeneticAlgorithm;

import com.mycompany.dietgenerator.Validation;
import java.util.ArrayList;


/**
 * The superclass for Breakfast, Snack, Lunch, and Dinner classes. An instantiation
 * of this class or subclasses represents a meal, which contains meal options, and
 * calls on the meal options run() method on each mealOption to generate the diet.
 * 
 * @author kxg708
 */
public class Meal implements Runnable {
    /**
     * A static enumerator for specifying meal type. In different parts of the code.
     */
    public static enum TENDENCY {
        /**
         * Represents the breakfast meal.
         */
        BREAKFAST,
        /**
         * Represents the lunch meal.
         */
        LUNCH,
        /**
         * Represents the snack meal.
         */
        SNACK,
        /**
         * Represents the dinner meal.
         */
        DINNER};

    /**
     * A static variable for the number of meal options a meal should have. Can be
     * easily changed by other developers.
     */
    private static int numOfMealOptions = 5;
    
    /**
     * List of options in the meal. same size as numOfMealOptions.
     */
    private ArrayList<MealOption> mealOptions;

    /**
     * A fitnessCalculator object with the correct constraints for each meal. It is
     * assigned in the subclasses.
     */
    protected FitnessCalculator fitnessCalc;
    
    /**
     * The constructor for the Meal Class. Assigns the mealOptions list to an
     * empty arrayList. This constructor is called by subclasses.
     */
    public Meal() {
        this.mealOptions = new ArrayList<>();
    }
    
    /**
     * This method is internally called in {@link com.mycompany.dietgenerator.DietCreationWindowController} 
     * when thread.start(); is called.
     * 
     * It creates 5 separate threads for each mealOption, and by calling thread.start(),
     * the run() method of {@link MealOption} is called.
     * 
     * In this way 5 mealOptions are generated concurrently.
     */
    @Override
    public void run() {
        try {
            ArrayList<Thread> threads = new ArrayList<>();
            
            Thread newThread;
            MealOption option;
            
            for (int i = 0; i < numOfMealOptions; i++) {
                option = new MealOption(fitnessCalc);
                newThread = new Thread(option);
                newThread.start();
                
                threads.add(newThread);
                mealOptions.add(option);
            }
            
            for (Thread thread : threads) {
                thread.join();
            }
        } catch(Exception e) {
            Validation.showErrorAlert(e);
        }        
    }
    
    /**
     * Getter for list of options in the meal.
     * 
     * @return list of options in the meal.
     */
    public ArrayList<MealOption> getMealOptions() {
        return mealOptions;
    }
    
    /**
     * Getter for number of options in the meal.
     * 
     * @return number of options in the meal.
     */
    public int getNumOfMealOptions() {
        return numOfMealOptions;
    }
    
    /**
     * Setter for number of options in the meal.
     * 
     * @param numOfMealOptions the new number of options in the meal.
     */
    public void setNumOfMealOptions(int numOfMealOptions) {
        this.numOfMealOptions = numOfMealOptions;
    }
    
}
