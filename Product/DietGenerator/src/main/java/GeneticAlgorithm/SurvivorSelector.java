package GeneticAlgorithm;

import com.mycompany.dietgenerator.Validation;
import java.io.Serializable;

/**
 * A subclass of the TournamentSelector class, implementing the methods for survivor
 * selection operations. These operations include, finding the loser of each round of
 * the tournament algorithm and replacing the losers with the array of offspring.
 * 
 * @author kxg708
 */
public class SurvivorSelector extends TournamentSelector implements Serializable {
    /**
     * Constructor for the SurvivorSelector class which takes in the population
     * object and K (the number of selected solutions each round) to implement the
     * tournament selector. These values are passed to the superclass.
     * 
     * @param population the population object on which parent selection is done each generation.
     * @param k the number of selected solutions each round of the tournament selector algorithm.
     */
    public SurvivorSelector(Chromosome[] population, int k) {
        super(population, k);
    }
    
    /**
     * This method finds the loser of one round of the tournament selection algorithm.
     * This is done by finding the least fit solution in the given array of chromosomes.
     * 
     * @param chromosomes the array of k chromosomes being checked.
     * @return a single chromosome object which has the least fitness
     */
    private Chromosome findLoser(Chromosome[] chromosomes) {
        if (chromosomes == null) return null;
        
        int i = 0;
        Chromosome leastFit = chromosomes[i];
        while (i < chromosomes.length) {
            if (chromosomes[i].getFitness() < leastFit.getFitness()) {
                leastFit = chromosomes[i];
            }
            i++;
        }
        
        return leastFit;
    }
    
    /**
     * This method takes an array of offspring and runs the tournament selector
     * algorithm on the population to find the weakest chromosomes. then it replaces
     * those "loser" chromosomes with the offspring, using the setEqualTo(); method.
     * 
     * @param offSprings the of offspring to be replaced in population.
     */
    public void replace(Chromosome[] offSprings) {
        Chromosome[] selected;
        Chromosome loser;
        
        for (Chromosome offSpring : offSprings) {
            selected = selectKChromosomes();
            loser = findLoser(selected);
            if (loser.getFitness() < offSpring.getFitness()) {
                try {
                    loser.setEqualTo(offSpring);
                }catch (Exception e) {
                    Validation.showErrorAlert(e);
                }
            }
        }
    }
}
