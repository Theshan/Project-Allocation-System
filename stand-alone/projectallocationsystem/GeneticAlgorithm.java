/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

import java.util.Set;

/**
 *
 * @author Olympians
 */
public class GeneticAlgorithm {
    private static final double uniformRate     = 0.5;
    private static final double mutationRate    = 0.015;
    private static final int cullSize           = 5;
    private static final boolean elitism        = true;
    private static final int optimisedGenNum    = 100;
    
    private PreferenceTable preferenceTable;
    private int populationSize;
    private Population population;
    private int startingFitness;
    
    public GeneticAlgorithm(int populationSize, PreferenceTable pref){
        this.preferenceTable    = pref;
        this.populationSize     = populationSize;
        this.population         = new Population(this.populationSize, preferenceTable);
        this.startingFitness    = population.getFittest().getFitness();
        System.out.println("====================================== Genetic Algorithm Execute ======================================");
        
        int generationCount     = 0;
        while (generationCount < optimisedGenNum) {
            generationCount++;
            System.out.println("Generation: " + generationCount + ", Fittest fittness: " + population.getFittest().getFitness());
            population          = evolvePopulation(population);
        }
        
        System.out.println("");
        System.out.println("Starting Fitness    : " + startingFitness);
        System.out.println("Best Fitness        : " + population.getFittest().getFitness());
        System.out.println("");
    }

    private Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), pop.prefTable);

        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        for (int i = elitismOffset; i < pop.size(); i++) {
            CandidateSolution sol1      = cullPopulation(pop);
            CandidateSolution sol2      = cullPopulation(pop);
            CandidateSolution newSol    = crossover(sol1, sol2);
            newPopulation.saveIndividual(i, newSol);
        }

        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getSolution(i));
        }

        return newPopulation;
    }

    private CandidateSolution crossover(CandidateSolution sol1, CandidateSolution sol2) {
        CandidateSolution newSol            = new CandidateSolution(preferenceTable);

        Set<String> keySet                  = newSol.getCandidateAssignmentsMap().keySet();
        java.util.Iterator<String> it       = keySet.iterator();
        while(it.hasNext()){
            String studentName              = it.next();
            if (Math.random() <= uniformRate) {
                CandidateAssignment cand    = sol1.getGene(studentName);
                newSol.setGene(studentName, cand);
            } else {
                CandidateAssignment cand    = sol2.getGene(studentName);
                newSol.setGene(studentName, cand);
            }
        }
        return newSol;
    }

    private void mutate(CandidateSolution sol) {
        Set<String> keySet                  = sol.getCandidateAssignmentsMap().keySet();
        java.util.Iterator<String> it       = keySet.iterator();
        while(it.hasNext()){
            String studentName              = it.next();
            if (Math.random() <= mutationRate) {
                CandidateAssignment cand    = sol.getGene(studentName);
                cand.randomizeAssignment();
                sol.setGene(studentName, cand);
            }
        }
    }

    private static CandidateSolution cullPopulation(Population pop) {
        Population tournament               = new Population(cullSize, pop.prefTable);
 
        for (int i = 0; i < cullSize; i++) {
            int randomId                    = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getSolution(randomId));
        }

        CandidateSolution fittest           = tournament.getFittest();
        return fittest;
    }
    
    public CandidateSolution getFittestSolution(){
        return this.population.getFittest();
    }
    
}
