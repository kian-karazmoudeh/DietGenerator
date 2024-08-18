package GeneticAlgorithm;

import java.util.Random;

/**
 * A genetic algorithm static utility class which contains methods for mutating an array of
 * chromosomes. Mutation is implemented using the random swap algorithm.
 * 
 * @author kxg708
 */
public class Mutator {
    /**
     * The probability with which to perform mutation. set to 15%, but can easily be 
     * modified.
     */
    private static final int MUTATION_PROBABILITY = 15;
    
    /**
     * A small 5% probability with which to add a guided gene in the swap method.
     */
    private static final int GUIDED_GENE_PERCENTAGE = 5;
    
    /**
     * A static Random object in order to generate random numbers
     */
    private static final Random RANDOM = new Random();
    
    /**
     * This is a utility method which returns true with a probability specified. it is
     * implemented by checking whether a random number between 1 and 100 is less than
     * the probability specified.
     * 
     * @param probability the probability of returning true
     * @return true with a certain probability. Otherwise false.
     */
    public static boolean chance(int probability) {
        // Generates a RANDOM number between 1 and 100
        int randomNumber = RANDOM.nextInt(100) + 1;

        return randomNumber <= probability;
    }
    
    /**
     * This method is the mutation algorithm. it replaces a random gene from the specified
     * chromosome with a random gene, using the aliments global variable.
     * 
     * @param chromosome the chromosome on which mutation should be run
     */
    private static void swap(Chromosome chromosome) {
        int randomIndexRemoved = RANDOM.nextInt(chromosome.getSize());
        Integer[] keys = chromosome.getKeyset();
        chromosome.removeGene(keys[randomIndexRemoved]);
        
        if (Mutator.chance(GUIDED_GENE_PERCENTAGE)) {
            PopulationInitializer.addGuidedGene(chromosome);
        } else {
            PopulationInitializer.addRandomGene(chromosome);
        }
    }
    
    /**
     * This method iterates through each chromosome of a population and mutates them with a
     * probability of MUTATION_PROBABILITY 
     * 
     * @param genotype the population of chromosomes on which mutation should be run
     */
    public static void mutate(Chromosome[] genotype) {
        for (Chromosome chromosome : genotype) {
            if (chance(MUTATION_PROBABILITY)) {
                swap(chromosome);
            }
        }
    }
}
