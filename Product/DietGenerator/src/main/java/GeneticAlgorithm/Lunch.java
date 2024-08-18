package GeneticAlgorithm;

/**
 * A subclass of the Meal class, which takes in the calorie and macro-nutrient
 * goals and calculates 40% of each, and creates the appropriate fitness object
 * for Lunch.
 * 
 * @author kxg708
 */
public class Lunch extends Meal {
    /**
     * A percentage constant of 40%, for getting 40% of the total goals.
     */
    public static final float PERCENTAGE_DIVIDER = 0.4f;
    
    /**
     * The constructor which takes in the calorie and macro-nutrient goals
     * specified in the diet creation window, and considers only 40% of
     * each constraint for the Lunch meal. It then creates a fitness
     * calculator object based off those values, and passes it to the super
     * class.
     * 
     * @param calorieGoal the total calorie goal of the diet
     * @param proteinGoal the total protein goal (in grams) of the diet
     * @param carbGoal the total carbohydrate goal (in grams) of the diet
     * @param fatGoal the total fat goal (in grams) of the diet
     */
    public Lunch(int calorieGoal, float proteinGoal, float carbGoal, float fatGoal) {
        super();
        
        int calorieConstraint = Math.round(calorieGoal * PERCENTAGE_DIVIDER);
        float proteinConstraint = proteinGoal * PERCENTAGE_DIVIDER;
        float carbConstraint = carbGoal * PERCENTAGE_DIVIDER;
        float fatConstraint = fatGoal * PERCENTAGE_DIVIDER;
        Meal.TENDENCY mealTendency = Meal.TENDENCY.LUNCH;
        
        super.fitnessCalc = new FitnessCalculator(calorieConstraint, proteinConstraint, carbConstraint, fatConstraint, mealTendency);
    }
}
