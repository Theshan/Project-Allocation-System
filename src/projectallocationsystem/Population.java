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
public class Population {
    int populationSize = 10;
    CandidateSolution[] solutions;

    public Population(int populationSize, PreferenceTable pref) {
        solutions = new CandidateSolution[this.populationSize];
        
        for (int i = 0; i < size(); i++) {
            CandidateSolution newSol = new CandidateSolution(pref);          
            saveIndividual(i, newSol);
        }
    }

    public CandidateSolution getSolution(int index) {
        return solutions[index];
    }

    public CandidateSolution getFittest() {
        CandidateSolution fittest = solutions[0];
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getSolution(i).getFitness()) {
                fittest = getSolution(i);
            }
        }
        return fittest;
    }

    public int size() {
        return solutions.length;
    }

    public void saveIndividual(int index, CandidateSolution sol) {
        solutions[index] = sol;
    }
    
}
