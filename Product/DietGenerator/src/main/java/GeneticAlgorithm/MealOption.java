package GeneticAlgorithm;

import java.io.Serializable;
import java.util.List;
import java.util.Arrays;

/**
 * An instance of this class represents a single option in a meal. This class
 * contains methods for running the genetic algorithm to create the meal option,
 * 
 * @author kxg708
 */
public class MealOption implements Serializable, Runnable {
    /**
     * The population size for the genetic algorithm.
     */
    public static int POPULATION_SIZE = 30;

    /**
     * The number of solutions which should be compared in each round of the K-way
     * tournament selector.
     */
    public static int K = 4;
     
    /**
     * Array containing all chromosomes of the population.
     */
    private Chromosome[] genotype;

    /**
     * A list containing the aliments selected by the genetic algorithm. Once the best
     * chromosome is found, the chromosome.decode() method is run to turn it into an aliment
     * list which is a physical representation of the chromosome.
     */
    private List<Aliment> aliments;
    
    /**
     * Object for calculating the fitness of each chromosome. 
     */
    private FitnessCalculator fitnessCalc;

    /**
     * Object for selecting the parents of each generation of the algorithm.
     */
    private ParentSelector parentSelector;

    /**
     * Object for finding and replacing the least fit solutions with the generated array
     * of offspring.
     */
    private SurvivorSelector survivorSelector;
    
    /**
     * The maximum number of generations the algorithm should go through. This is to prevent
     * an infinite loop. 
     */
    private final int maxIterations = 10000;
    
    /**
     * Constructor for the MealOption class. It takes in a fitness calculator, which is set
     * with the correct constraints for the meal.
     * 
     * @param fitnessCalc the fitness calculator object with which to evaluate the chromosomes.
     */
    public MealOption(FitnessCalculator fitnessCalc) {
        this.fitnessCalc = fitnessCalc;
    }
    
    /**
     * This method is internally called in {@link Meal} when thread.start(); is called. It runs
     * the genetic algorithm and saves the generated meal option in the aliments list.
     */
    @Override
    public void run() {
        Chromosome[] parents; // temporary storage of the parents of each generation
        Chromosome[] offSprings; // temporary storage of the offsprings of each generation
        
        genotype = PopulationInitializer.populate(POPULATION_SIZE);
        this.parentSelector = new ParentSelector(genotype, K);
        this.survivorSelector = new SurvivorSelector(genotype, K);
        
        Chromosome fittest = genotype[0]; // holds the fittest meal option in each generation
        float fitness; // temporary variable for holding fitness values

        int iteration = 0;
        do {
            // assign fitness of the generation
            for (Chromosome chromosome : genotype) {
                fitness = fitnessCalc.calculateFitness(chromosome);
                chromosome.setFitness(fitness);
                // find fittest meal option
                if (fitness < fittest.getFitness()) fittest = chromosome;
            }
            
            // select the top individuals as parents
            parents = parentSelector.getWinners(6);

            // create offsprings based on the chosen parents.
            offSprings = Crossover.generateOffsprings(parents);
            
            // assign a fitness to the offspring
            for (Chromosome chromosome : offSprings) {
                fitness = fitnessCalc.calculateFitness(chromosome);
                chromosome.setFitness(fitness);
            }

            // replace the worst performers in the population with the offsprings
            survivorSelector.replace(offSprings);

            // randomly mutate the population
            Mutator.mutate(genotype);
            
            iteration++;
        } while (iteration < maxIterations && fittest.getFitness() < -50);
        
        aliments = Arrays.asList(fittest.decode());
    }
    
    /**
     * Getter for the aliments list.
     * 
     * @return the aliment list.
     */
    public List<Aliment> getAliments() {
        return aliments;
    }
    
    /**
     * replaces the aliments list with another aliments list. It is used in
     * {@link com.mycompany.dietgenerator.MealOptionEditorWindowController},
     * to manually modify the meal option.
     * 
     * @param aliments the new aliments list which should be added.
     */
    public void addAll(List<Aliment> aliments) {
        this.aliments = aliments;
    }
    
    /**
     * Method for converting the mealOption to string. by joining the name of each
     * aliment and its amount (serving size * number of servings) with a + sign.
     * This is used in diet display window to display the meal options in the tables.
     */
    @Override
    public String toString() {
        String result = "";
        
        for (int i = 0; i < aliments.size(); i++) {
            result += aliments.get(i).getName() + " " + Math.round(aliments.get(i).getNumOfServings()*aliments.get(i).getServingSize()) + " " + aliments.get(i).getServingUnit();
            if (i != aliments.size() - 1) {
                result += " + ";
            }
        }
        
        return result;
    }
}
