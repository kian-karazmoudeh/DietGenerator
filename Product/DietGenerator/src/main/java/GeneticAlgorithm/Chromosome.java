package GeneticAlgorithm;

import java.util.HashMap;
import java.util.Map;
import com.mycompany.dietgenerator.App;
import com.mycompany.dietgenerator.Validation;
import java.io.Serializable;

/**
 * An instantiation of this class represents a chromosome in the genetic algorithm or in other words
 * a solution within computational space. The chromosome encoding is done in key/value pairs in a
 * map where the key represents the index of the aliment (phenotype representation) in the global
 * variable aliments and the value represents the number of servings of that aliment. The class also
 * contains a decode method for converting the solution from computational space to physical space.
 * 
 * @author kxg708
 */
public class Chromosome implements Serializable, Cloneable {
    /**
     * A static number for the size of the solution. In other words, how many aliments a solution should
     * contain. Here it is specified 4
     */
    public static int CHROMOSOME_SIZE = 4;

    /**
     * The map representing the key/value pairs of the chromosome. Here the key is the aliment index
     * and the value is the number of servings of that aliment.
     */
    private Map<Integer, Float> chromosome;

    /**
     * The fitness or the suitability of the solution. The fitness starts off as a large negative number
     * and approaches 0 as the Solution gets better.
     */
    private double fitness;
    
    /**
     * The constructor of the Chromosome class, which assigns the chromosome variable to an empty map.
     */
    public Chromosome() {
        chromosome = new HashMap<>();
    }
    
    /**
     * Sets the chromosome map of this object to another. It is used in Survivor selection operations for
     * replacing weak solutions.
     * 
     * @param otherChromosome the chromosome whose contents should be copied in this.
     */
    public void setEqualTo(Chromosome otherChromosome) {
        chromosome.clear();
        Integer[] keyset = otherChromosome.getKeyset();
        
        for (Integer key : keyset) {
            chromosome.put(key, otherChromosome.getGene(key));
        }
    }
    
    /**
     * Getter for each gene in the chromosome
     * 
     * @param key the index of the gene which should be returned.
     * @return the gene with the index of key.
     */
    public float getGene(int key) {
        return chromosome.get(key);
    }
    
    /**
     * Adds a new gene to the chromosome map. A gene can be considered as a key/value pair.
     * This is used in Genetic Algorithm operations such as mutation, where the chromosome
     * must be manipulated.
     * 
     * @param alimentIndex the index of the aliment, which is the key
     * @param numOfServings the number of servings of that aliment, which is the value.
     */
    public void addGene(int alimentIndex, float numOfServings) {
        chromosome.put(alimentIndex, numOfServings);
    }
    
    /**
     * removes a gene with the index or key specified.
     * 
     * @param alimentIndex the aliment index, or the key of the gene which should be removed.
     */
    public void removeGene(int alimentIndex) {
        chromosome.remove(alimentIndex);
    }
    
    /**
     * Getter for chromosome size
     * 
     * @return the size of the chromosome map.
     */
    public int getSize() {
        return chromosome.size();
    }
    
    /**
     * Checks whether an aliment with a certain index exists in the chromosome
     * 
     * @param alimentIndex the index of the aliment checked in the global variable aliments.
     * @return true if the chromosome does contain a key, and false if it doesn't.
     */
    public boolean containsKey(int alimentIndex) {
        return chromosome.containsKey(alimentIndex);
    }
    
    /**
     * Getter for the fitness of the chromosome.
     * 
     * @return the fitness value of the chromosome.
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Setter for fitness value of the chromosome
     * 
     * @param fitness the new fitness value of the chromosome.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    

    /**
     * Converts the chromosome map which is in computational space into a phenotype representation
     * in physical space. The phenotype representation is an array of aliment objects which have the
     * number of servings data member assigned.
     * 
     * @return an array of aliments with the number of servings set assigned.
     */
    public Aliment[] decode() {
        Aliment[] decoded = new Aliment[chromosome.size()];
        
        try {
            // loop through each gene and find its
            // representation in App.aliments
            Aliment aliment;
            int index = 0;
            for (Map.Entry<Integer, Float> gene : chromosome.entrySet()) {
                aliment = App.aliments.get(gene.getKey());
                aliment = (Aliment) aliment.clone();

                aliment.setNumOfServings(gene.getValue());
                decoded[index] = aliment;
                index++;
            }
        } catch (Exception e) {
            Validation.showErrorAlert(e);
        }
        
        return decoded;
    }
    
    /**
     * returns the set of aliment indices or keys of the chromosome map
     * 
     * @return an integer array of all the keys in the chromosome map.
     */
    public Integer[] getKeyset() {
        return chromosome.keySet().toArray(new Integer[0]);
    }
    
    /**
     * Method to be able to clone the chromosome object.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
	    return super.clone();
    }
}
