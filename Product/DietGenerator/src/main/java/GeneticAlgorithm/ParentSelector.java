package GeneticAlgorithm;

import java.io.Serializable;

/**
 * A subclass of the TournamentSelector class, implementing the methods for parent
 * selection operations. These operations include, finding the winner of each round
 * of the tournament algorithm.
 * 
 * @author kxg708
 */
public class ParentSelector extends TournamentSelector implements Serializable {
    /**
     * Constructor for the ParentSelector class which takes in the population
     * object and K (the number of selected solutions each round) to implement the
     * tournament selector. These values are passed to the superclass.
     * 
     * @param population the population object on which parent selection is done each generation.
     * @param k the number of selected solutions each round of the tournament selector algorithm.
     */
    public ParentSelector(Chromosome[] population, int k) {
        super(population, k);
    }
    
    /**
     * This method finds the winner of one round of the tournament selection algorithm.
     * This is done by finding the fittest solution in the given array of chromosomes.
     * 
     * @param chromosomes the array of k chromosomes being checked.
     * @return a single chromosome object which has the highest fitness
     */
    private Chromosome findWinner(Chromosome[] chromosomes) {
        if (chromosomes == null) return null;
        
        int i = 0;
        Chromosome fittest = chromosomes[i];
        while (i < chromosomes.length) {
            if (chromosomes[i].getFitness() > fittest.getFitness()) {
                fittest = chromosomes[i];
            }
            i++;
        }
        
        return fittest;
    }
    
    /**
     * This method runs the tournament selector algorithm a number of times to get a specified
     * number of winners. These winners are the fittest solutions in the population and are the
     * parents based off which offspring are made.
     * 
     * @param numOfWinners the number of winners (parents) the algorithm should return
     * @return an array of winners with same length as numOfWinners.
     */
    public Chromosome[] getWinners(int numOfWinners) {
        Chromosome[] selected;
        Chromosome[] winners = new Chromosome[numOfWinners];
        
        for (int i = 0; i < numOfWinners; i++) {
            selected = selectKChromosomes();
            winners[i] = findWinner(selected);
        }
        
        return winners;
    }
}
