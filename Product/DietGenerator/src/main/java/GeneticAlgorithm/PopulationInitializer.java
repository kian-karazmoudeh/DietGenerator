package GeneticAlgorithm;
import com.mycompany.dietgenerator.App;
import java.util.ArrayList;
import java.util.Random;

/**
 * A genetic algorithm utility class which handles population related operations.
 * The main one being, creating an initial population of chromosomes.
 * 
 * @author kxg708
 */
public class PopulationInitializer {
    /**
     * A static Random object in order to generate random numbers
     */
    private static final Random RANDOM = new Random();
    
    /**
     * A 20% probability with which to add a guided gene in the create chromosome
     * method.
     */
    private static final int GUIDED_GENE_PROBABILITY = 20;
    
    /**
     * A method which takes the number of individuals in the population and creates
     * that many chromosomes. It is used to initialize the population.
     * 
     * @param amount number of individuals in population.
     * @return an array of chromosomes representing the created population.
     */
    public static Chromosome[] populate(int amount) {
        Chromosome[] population = new Chromosome[amount];
        
        for (int i = 0; i < amount; i++) {
            population[i] = createChromosome();
        }
        
        return population;
    }
    
    /**
     * A method which creates a semi random new chromosome. It uses two utility
     * methods {@link  #addRandomGene(GeneticAlgorithm.Chromosome)} and
     * {@link #addGuidedGene(GeneticAlgorithm.Chromosome)} for adding genes. The
     * number of genes added is the same as chromosome size.
     * 
     * @return the created chromosome.
     */
    private static Chromosome createChromosome() {
        Chromosome chromosome = new Chromosome();
        
        for (int i = 0; i < Chromosome.CHROMOSOME_SIZE; i++) {
            if (Mutator.chance(GUIDED_GENE_PROBABILITY)) {
                addGuidedGene(chromosome);
            } else {
                addRandomGene(chromosome);
            }
        }
        
        return chromosome;
    }
    
    /**
     * This method adds a single random gene to the specified chromosome. It
     * generates a random aliment index which is not already in the chromosome
     * and adds it. A do while loop is implemented to find another index, if the
     * one chosen is already in the chromosome.
     * 
     * @param chromosome the chromosome to which the gene should be added
     */
    public static void addRandomGene(Chromosome chromosome) {
        int randomIndexAdded;
        float randomFloat = RANDOM.nextFloat() * 5;
        
        do {
            randomIndexAdded = RANDOM.nextInt(App.aliments.size());
        } while (chromosome.containsKey(randomIndexAdded));
        
        chromosome.addGene(randomIndexAdded, randomFloat);
    }
    
    /**
     * This method adds a single gene to the specified chromosome, which is taken
     * from the user's liked aliments list (App.likes). If the preference list is
     * empty or the chromosome already contains all the liked  aliments, then a
     * random gene is added.
     * 
     * 
     * @param chromosome the chromosome to which the gene should be added
     */
    public static void addGuidedGene(Chromosome chromosome) {
        int randomAvailableIndex;
        int randomIndex;
        int randomIndexAdded;
        float randomFloat = RANDOM.nextFloat() * 5;
        
        ArrayList<Integer> availableIndices = new ArrayList<>();

        for(int i = 0; i < App.likes.size(); i++) {
            availableIndices.add(i);
        }

        do {
            // if App.likes is empty or the chromosome
            // already contains all liked aliments
            if (availableIndices.isEmpty()) {
                addRandomGene(chromosome);
                return;
            }
            
            randomAvailableIndex = RANDOM.nextInt(availableIndices.size());
            randomIndex = availableIndices.get(randomAvailableIndex);
            randomIndexAdded = App.aliments.indexOf(App.likes.get(randomIndex));
              
            availableIndices.remove(randomAvailableIndex);
        } while (chromosome.containsKey(randomIndexAdded));

        chromosome.addGene(randomIndexAdded, randomFloat);
    }
}
