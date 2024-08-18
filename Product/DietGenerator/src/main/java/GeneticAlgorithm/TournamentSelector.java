package GeneticAlgorithm;

import java.io.Serializable;
import java.util.Random;

/**
 * A genetic algorithm utility class which acts as the superclass for the SurvivorSelector
 * and TournamentSelector subclasses. This class contains shared methods for implementing
 * a K-way tournament selector algorithm which is utilized for the  parent and
 * survivor selection operations in the genetic algorithm.
 * 
 * @author kxg708
 */
public class TournamentSelector implements Serializable {
    /**
     * It is the population object within the genetic algorithm.
     */
    protected Chromosome[] population;

    /**
     * The number of solutions to select in each round of the tournament selection
     * algorithm.
     */
    private int k;
    
    /**
     * The constructor of the TournamentSelector algorithm, which is called by
     * the subclasses. It takes in the population object and K (the number of
     * selected solutions each round) to implement the tournament selector. These
     * values are passed to the superclass.
     * 
     * @param population the population object on which parent selection is done each generation.
     * @param k the number of selected solutions each round of the tournament selector algorithm.
     */
    public TournamentSelector(Chromosome[] population, int k) {
        this.population = population;
        this.k = k;
    }
    
    /**
     * This method selects k solutions from the population.
     * 
     * @return an array of k selected chromosomes.
     */
    protected Chromosome[] selectKChromosomes() {
        Chromosome[] selected = new Chromosome[k];
        Random random = new Random();
        
        int rand; 
        for (int i = 0;i < k;i++) {
            rand = random.nextInt(population.length);
            selected[i] = population[rand];
        }
        
        return selected;
    }
}
