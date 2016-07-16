/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

/**
 *
 * @author adventure-ro
 */
public class GeneticAlgorithm {
    private PreferenceTable preferenceTable;
    private Population population;
    
    public GeneticAlgorithm(PreferenceTable pref){
        this.preferenceTable = pref;
        // Create an initial population
        Population myPop = new Population(10, preferenceTable);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < CandidateSolution.getMaxFitness()) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
//            myPop = Algorithm.evolvePopulation(myPop);
        }
    }
     
    
    
    
    
}
