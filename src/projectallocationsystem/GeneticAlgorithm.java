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
    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    
    private PreferenceTable preferenceTable;
    private Population population;
    
    public GeneticAlgorithm(PreferenceTable pref){
        this.preferenceTable = pref;
        // Create an initial population
        this.population = new Population(10, preferenceTable);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (population.getFittest().getFitness() < CandidateSolution.getMaxFitness()) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + population.getFittest().getFitness());
//            myPop = Algorithm.evolvePopulation(myPop);
        }
    }

    /* Public methods */
    
    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), pop.prefTable);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            CandidateSolution sol1 = tournamentSelection(pop);
            CandidateSolution sol2 = tournamentSelection(pop);
            CandidateSolution newSol = crossover(sol1, sol2);
            newPopulation.saveIndividual(i, newSol);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getSolution(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static CandidateSolution crossover(CandidateSolution sol1, CandidateSolution sol2) {
//        CandidateSolution newSol = new CandidateSolution();
//        // Loop through genes
//        for (int i = 0; i < sol1.size(); i++) {
//            // Crossover
//            if (Math.random() <= uniformRate) {
//                newSol.setGene(i, sol1.getGene(i));
//            } else {
//                newSol.setGene(i, sol2.getGene(i));
//            }
//        }
        return sol1;
    }

    // Mutate an individual
    private static void mutate(CandidateSolution sol) {
        // Loop through genes
        for (int i = 0; i < sol.getCandidateAssignmentsMap().size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                byte gene = (byte) Math.round(Math.random());   //get randome assignment
//                sol.setGene(i, gene);                         //assign random assignment to student in sol
            }
        }
    }

    // Select individuals for crossover
    private static CandidateSolution tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, pop.prefTable);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getSolution(randomId));
        }
        // Get the fittest
        CandidateSolution fittest = tournament.getFittest();
        return fittest;
    }
    
}
