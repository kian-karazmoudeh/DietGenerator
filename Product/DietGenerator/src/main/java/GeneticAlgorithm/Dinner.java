package GeneticAlgorithm;

/**
 * A subclass of the Meal class, which takes in the calorie and macro-nutrient
 * goals and calculates 15% of each, and creates the appropriate fitness object
 * for Dinner.
 * 
 * @author kxg708
 */
public class Dinner extends Meal {
    /**
     * A percentage constant of 15%, for getting 15% of the total goals.
     */
    public static final float PERCENTAGE_DIVIDER = 0.15f;
    
    /**
     * The constructor which takes in the calorie and macro-nutrient goals
     * specified in the diet creation window, and considers only 15% of
     * each constraint for the Dinner meal. It then creates a fitness
     * calculator object based off those values, and passes it to the super
     * class.
     * 
     * @param calorieGoal the total calorie goal of the diet
     * @param proteinGoal the total protein goal (in grams) of the diet
     * @param carbGoal the total carbohydrate goal (in grams) of the diet
     * @param fatGoal the total fat goal (in grams) of the diet
     */
    public Dinner(int calorieGoal, float proteinGoal, float carbGoal, float fatGoal) {
        super();
        
        int calorieConstraint = Math.round(calorieGoal * PERCENTAGE_DIVIDER);
        float proteinConstraint = proteinGoal * PERCENTAGE_DIVIDER;
        float carbConstraint = carbGoal * PERCENTAGE_DIVIDER;
        float fatConstraint = fatGoal * PERCENTAGE_DIVIDER;
        Meal.TENDENCY mealTendency = Meal.TENDENCY.DINNER;
        
        super.fitnessCalc = new FitnessCalculator(calorieConstraint, proteinConstraint, carbConstraint, fatConstraint, mealTendency);
    }
}
