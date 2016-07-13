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
public class SimulatedAnnealing {
    private CandidateSolution currentSolution;
    private double temperature = 100;
    private double coolingTemperature = 10;
    
    public SimulatedAnnealing(CandidateSolution cand){
        this.currentSolution = cand;
        doAnealing();
    }
    
    public CandidateSolution getModifiedBestSolution() {
        return this.currentSolution;
    }
    
    private void doAnealing() {
        
        while(temperature > 0) {
            CandidateSolution newSolution = new CandidateSolution(currentSolution);    
            newSolution.updateToBetterSolution();   //go and see the method. you have to fill it
            int newEnergy = newSolution.getEnergy();
            int currentEnergy = currentSolution.getEnergy();
            double acceptanceProb = getAccepProbability(currentEnergy, newEnergy, temperature);
            
            if (acceptanceProb >= 1.0) { // i don't know whether this condition is correct or wrong. let's put it that way just for now.
                currentSolution = newSolution;
            }
            
            
            temperature -= coolingTemperature;
        }
    }
    
    public double getAccepProbability(int energy, int newEnergy, double currentTemperature) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp((energy - newEnergy) / currentTemperature);
    }
}
