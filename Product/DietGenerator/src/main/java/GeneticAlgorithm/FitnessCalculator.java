package GeneticAlgorithm;

import com.mycompany.dietgenerator.App;
import java.io.Serializable;

/**
 * A genetic algorithm utility class which includes functions for calculating the
 * fitness of a chromosome. Objects of this class take in the calorie, protein,
 * carbohydrate, and fat constraints and finds the variation between the constraints
 * and the chromosome.
 *
 * @author kxg708
 */
public class FitnessCalculator implements Serializable {
    /**
     * Coefficient for adding weight to calorie constraint. value obtained from
     * Trial and error, and can easily be modified.
     */
    private static final float CALORIE_COEFF = 5f;

    /**
     * Coefficient for adding weight to protein constraint. value obtained from
     * Trial and error, and can easily be modified.
     */
    private static final float PROTEIN_COEFF = 10f;

    /**
     * Coefficient for adding weight to fat constraint. value obtained from Trial
     * and error, and can easily be modified.
     */
    private static final float FAT_COEFF = 10f;

    /**
     * Coefficient for adding weight to carbohydrate constraint. value obtained
     * from Trial and error, and can easily be modified.
     */
    private static final float CARB_COEFF = 10f;

    /**
     * Coefficient for adding weight to preference constraint. value obtained from
     * Trial and error, and can easily be modified.
     */
    private static final float PREFERENCE_COEFF = 10f;

    /**
     * Coefficient for adding weight to meal tendency constraint. value obtained
     * from Trial and error, and can easily be modified.
     */
    private static final float MEAL_TENDENCY_COEFF = 100f;
    
    /**
     * An arbitrary step value to increase the fitness or decrease the fitness
     * based on preference.
     */
    private static final int PREFERENCE_STEP = 5;
    
    /**
     * An arbitrary step value to increase the decrease the fitness based on meal
     * tendency suitability of the chromosome.
     */
    private static final int MEAL_TENDENCY_STEP = 5;
    
    /**
     * The meal that this object is being used for. For instance if the algorithm
     * is generating a breakfast, this would be Meal.BREAKFAST
     */
    private Meal.TENDENCY mealTendency;

    /**
     * The number of calories each meal option should be
     */
    private int calorieConstraint;

    /**
     * The amount of protein (in grams) each meal option should be
     */
    private float proteinConstraint;

    /**
     * The amount of carbohydrates (in grams) each meal option should be
     */
    private float carbConstraint;

    /**
     * The amount of fat (in grams) each meal option should be
     */
    private float fatConstraint;
    
    /**
     * The Constructor of the FitnessCalculator class. In this constructor
     * the constraints are passed in and assigned to the appropriate data
     * members.
     * 
     * @param calorieConstraint the calorie constraint
     * @param proteinConstraint the protein constraint
     * @param carbConstraint the carbohydrate constraint
     * @param fatConstraint the fat constraint
     * @param mealTendency the meal tendency constraint
     */
    public FitnessCalculator(int calorieConstraint, float proteinConstraint, float carbConstraint, float fatConstraint, Meal.TENDENCY mealTendency) {
        this.calorieConstraint = calorieConstraint;
        this.proteinConstraint = proteinConstraint;
        this.carbConstraint = carbConstraint;
        this.fatConstraint = fatConstraint;
        this.mealTendency = mealTendency;
    }
    
    /**
     * Calculates the difference (distance) between the protein of the aliments
     * of a meal option and the specified protein constraints
     * 
     * @param aliments the array of aliments being checked
     * @return negative float showing the difference, which is weighted by coefficient
     */
    private float calculateProteinVariation(Aliment[] aliments) {
        float proteinSum = 0;
        for (Aliment aliment : aliments) {
            proteinSum += aliment.getProteinPerServing() * aliment.getNumOfServings();
        }
        
        return -Math.abs(proteinConstraint - proteinSum) * PROTEIN_COEFF;
    }
    
    /**
     * Calculates the difference (distance) between the fat of the aliments of a
     * meal option and the specified fat constraints
     * 
     * @param aliments the array of aliments being checked
     * @return negative float showing the difference, which is weighted by coefficient
     */
    private float calculateFatVariation(Aliment[] aliments) {
        float fatSum = 0;
        for (Aliment aliment : aliments) {
            fatSum += aliment.getFatPerServing() * aliment.getNumOfServings();
        }
        
        return -Math.abs(fatConstraint - fatSum) * FAT_COEFF;
    }
    
    /**
     * Calculates the difference (distance) between the carbohydrate of the aliments
     * of a meal option and the specified carbohydrate constraints
     * 
     * @param aliments the array of aliments being checked
     * @return negative float showing the difference, which is weighted by coefficient
     */
    private float calculateCarbVariation(Aliment[] aliments) {
        float carbSum = 0;
        for (Aliment aliment : aliments) {
            carbSum += aliment.getCarbPerServing() * aliment.getNumOfServings();
        }
        
        return -Math.abs(carbConstraint - carbSum) * CARB_COEFF;
    }
    
    /**
     * Calculates the difference (distance) between the calorie of the aliments
     * of a meal option and the specified calorie constraints
     * 
     * @param aliments the array of aliments being checked
     * @return negative float showing the difference, which is weighted by coefficient
     */
    private float calculateCalorieVariation(Aliment[] aliments) {
        float calorieSum = 0;
        for (Aliment aliment : aliments) {
            calorieSum += aliment.getCaloriePerServing() * aliment.getNumOfServings();
        }
        
        return -Math.abs(calorieConstraint - calorieSum) * CALORIE_COEFF;
    }
    
    /**
     * Calculates the variation between the meal tendencies of the aliments of a
     * meal option and the specified meal tendency constraint. If the aliment is
     * not suitable for the specified meal, then a negative score is added.
     * 
     * @param aliments the array of aliments being checked
     * @return negative float showing the variation, which is weighted by coefficient
     */
    private float calculateMealTendencyVariation(Aliment[] aliments) {
        float mealTendencySum = 0;
        
        for (Aliment aliment : aliments) {
            if(!aliment.getMealTendency().contains(mealTendency)) {
                mealTendencySum -= MEAL_TENDENCY_STEP;
            }
        }
        
        return mealTendencySum * MEAL_TENDENCY_COEFF;
    }
    
    /**
     * Calculates the variation between user preferences and the aliments int the
     * meal option. for every aliment that the user likes, a positive 3 points are
     * added and for every disliked food, a 3 points are deducted.
     * 
     * @param aliments the array of aliments being checked
     * @return negative float showing the variation, which is weighted by coefficient
     */
    private float calculatePreferenceVariation(Aliment[] aliments) {
        float variation = 0;
        for (Aliment aliment : aliments) {
            if(App.likes.contains(aliment)) variation += PREFERENCE_STEP;
            if (App.dislikes.contains(aliment)) variation -= PREFERENCE_STEP;
        }
        
        return variation * PREFERENCE_COEFF;
    }
    
    /**
     * A method which gets the variations of all the constraints. And combines them
     * to create a final fitness score for the given chromosome.
     * 
     * @param chromosome the chromosome for which a fitness is being calculated
     * @return a float representing the fitness value.
     */
    public float calculateFitness(Chromosome chromosome) {
        Aliment[] aliments = chromosome.decode();
        float calorieVar = calculateCalorieVariation(aliments);
        float fatVar = calculateFatVariation(aliments);
        float proteinVar = calculateProteinVariation(aliments);
        float carbVar = calculateCarbVariation(aliments);
        float preferenceVar = calculatePreferenceVariation(aliments);
        float mealTendencyVar = calculateMealTendencyVariation(aliments);
        
        return (preferenceVar + calorieVar + fatVar + proteinVar + carbVar + mealTendencyVar);
    }
    
}
