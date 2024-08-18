package GeneticAlgorithm;

import java.io.Serializable;
import java.util.Random;

/**
 * A genetic algorithm static utility class, in charge of creating offspring based on an
 * even array of parent chromosomes. Offspring creation is implemented using the single
 * point cross over algorithm
 * 
 * @author kxg708
 */
public class Crossover implements Serializable {
    /**
     * Generates an array of offspring based on an even array of parents. This is done by
     * iterating the parents array in pairs (i, i+1), taking parent[i] as parent 1 and parent[i + 1]
     * as parent 2 and then performing single point cross over on them which returns 2 offspring 
     * 
     * @param parents the even array of parents from which the offspring are created.
     * @return an array of generated offspring, with the same length as the parents
     */
    public static Chromosome[] generateOffsprings(Chromosome[] parents) {
        if (parents.length % 2 != 0) {
            System.err.println("Number of parents must be even!");
            System.exit(1);
        }
        
        // number of offspring is the same as number of parents
        Chromosome[] offsprings = new Chromosome[parents.length];
        
        Chromosome[] temp;
        for (int i = 0; i + 1 < parents.length;i = i + 2) {
            temp = singlePointCrossOver(parents[i], parents[i + 1]);
            offsprings[i] = temp[0];
            offsprings[i + 1] = temp[1];
            
        }
        
        return offsprings;
    }
    
    /**
     * This method is an implementation of the single point cross over algorithm. It generates a random
     * crossover index, and swaps the content of each parent chromosome until that index. There are also
     * two loops at the end of the method, which are implemented to resolve a very specific bug causing
     * the chromosome sizes to shrink.
     * 
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @return an array of size 2 of the generated offspring.
     */
    private static Chromosome[] singlePointCrossOver(Chromosome parent1, Chromosome parent2) {
        // generate a crossover point
        Random random = new Random();
        int crossOverPoint = random.nextInt(Chromosome.CHROMOSOME_SIZE + 1);
        
        Integer[] parent1keys = parent1.getKeyset();
        Integer[] parent2keys = parent2.getKeyset();
        
        Chromosome offSpring1 = new Chromosome();
        Chromosome offSpring2 = new Chromosome();
        
        // add the keys of one parent until the crossover point is reached
        for (int i = 0; i < crossOverPoint; i++) {
            offSpring1.addGene(parent1keys[i], parent1.getGene(parent1keys[i]));
            offSpring2.addGene(parent2keys[i], parent2.getGene(parent2keys[i]));
        }
        // switch parents and add the genes of the other parent, in turn creating crossover
        for (int i = crossOverPoint; i < Chromosome.CHROMOSOME_SIZE; i++) {
            offSpring1.addGene(parent2keys[i], parent2.getGene(parent2keys[i]));
            offSpring2.addGene(parent1keys[i], parent1.getGene(parent1keys[i]));
        }
        
        // in rare occasions, when a key is present in both parents, it can be added
        // to the offspring twice. the second time overwrites the first time and this causes
        // the chromosome size to shrink. hence this check is necessary
        while (offSpring1.getSize() < Chromosome.CHROMOSOME_SIZE) {
            PopulationInitializer.addRandomGene(offSpring1);
        }
        while (offSpring2.getSize() < Chromosome.CHROMOSOME_SIZE) {
            PopulationInitializer.addRandomGene(offSpring2);
        }
        
        return new Chromosome[]{offSpring1, offSpring2};
    }
}
