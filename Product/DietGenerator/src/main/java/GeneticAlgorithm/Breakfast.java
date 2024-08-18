package GeneticAlgorithm;

/**
 * A subclass of the Meal class, which takes in the calorie and macro-nutrient
 * goals and calculates 30% of each, and creates the appropriate fitness object
 * for Breakfast.
 * 
 * @author kxg708
 */
public class Breakfast extends Meal {
    /**
     * A percentage constant of 30%, for getting 30% of the total goals.
     */
    public static final float PERCENTAGE_DIVIDER = 0.3f;
    
    /**
     * The constructor which takes in the calorie and macro-nutrient goals
     * specified in the diet creation window, and considers only 30% of
     * each constraint for the Breakfast meal. It then creates a fitness
     * calculator object based off those values, and passes it to the super
     * class.
     * 
     * @param calorieGoal the total calorie goal of the diet
     * @param proteinGoal the total protein goal (in grams) of the diet
     * @param carbGoal the total carbohydrate goal (in grams) of the diet
     * @param fatGoal the total fat goal (in grams) of the diet
     */
    public Breakfast(int calorieGoal, float proteinGoal, float carbGoal, float fatGoal) {
        super();
        
        int calorieConstraint = Math.round(calorieGoal * PERCENTAGE_DIVIDER);
        float proteinConstraint = proteinGoal * PERCENTAGE_DIVIDER;
        float carbConstraint = carbGoal * PERCENTAGE_DIVIDER;
        float fatConstraint = fatGoal * PERCENTAGE_DIVIDER;
        Meal.TENDENCY mealTendency = Meal.TENDENCY.BREAKFAST;
        
        super.fitnessCalc = new FitnessCalculator(calorieConstraint, proteinConstraint, carbConstraint, fatConstraint, mealTendency);
    }
    
}
