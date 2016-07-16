/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

import java.util.Set;

/**
 *
 * @author adventure-ro
 */
public class GeneticAlgorithm {
    /* GA parameters */
    private static final double uniformRate     = 0.5;
    private static final double mutationRate    = 0.015;
    private static final int tournamentSize     = 5;
    private static final boolean elitism        = true;
    private static final int optimisedGenNum    = 20;
    
    private PreferenceTable preferenceTable;
    private int populationSize;
    private Population population;
    
    public GeneticAlgorithm(int populationSize, PreferenceTable pref){
        this.preferenceTable    = pref;
        this.populationSize     = populationSize;
        // Create an initial population
        this.population         = new Population(this.populationSize, preferenceTable);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (generationCount < optimisedGenNum) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest fittness: " + population.getFittest().getFitness());
            population = evolvePopulation(population);
        }
    }

    /* Public methods */
    
    // Evolve a population
    private Population evolvePopulation(Population pop) {
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
            CandidateSolution sol1      = tournamentSelection(pop);
            CandidateSolution sol2      = tournamentSelection(pop);
            CandidateSolution newSol    = crossover(sol1, sol2);
            newPopulation.saveIndividual(i, newSol);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getSolution(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private CandidateSolution crossover(CandidateSolution sol1, CandidateSolution sol2) {
        CandidateSolution newSol = new CandidateSolution(preferenceTable);
        // Loop through candAssignments
        Set<String> keySet = newSol.getCandidateAssignmentsMap().keySet();
        java.util.Iterator<String> it = keySet.iterator();
        while(it.hasNext()){
            String studentName = it.next();
//            candidateAssignmentsMap.put(studentName, cand);
            if (Math.random() <= uniformRate) {
                CandidateAssignment cand = sol1.getGene(studentName);
                newSol.setGene(studentName, cand);
            } else {
                CandidateAssignment cand = sol2.getGene(studentName);
                newSol.setGene(studentName, cand);
            }
        }
        return newSol;
    }

    // Mutate an individual
    private void mutate(CandidateSolution sol) {
        // Loop through genes        
        Set<String> keySet = sol.getCandidateAssignmentsMap().keySet();
        java.util.Iterator<String> it = keySet.iterator();
        while(it.hasNext()){
            String studentName = it.next();
            if (Math.random() <= mutationRate) {
                // Create random gene
                CandidateAssignment cand = sol.getGene(studentName);
                cand.randomizeAssignment();
                sol.setGene(studentName, cand);
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
