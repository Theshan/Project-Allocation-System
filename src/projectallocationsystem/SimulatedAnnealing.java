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
    private double coolingTemperature = 1;
    
    public SimulatedAnnealing(CandidateSolution cand){
        this.currentSolution = cand;
        doAnealing();
    }
    
    public CandidateSolution getModifiedBestSolution() {
        return this.currentSolution;
    }
    
    private void doAnealing() {
        System.out.println("currentSolution.getEnergy() 1: " + currentSolution.getEnergy());
        System.out.println("");
        
        CandidateSolution bestSoluteion = new CandidateSolution(currentSolution);
        
        while(temperature > 0) {
            CandidateSolution newBetterSolution = new CandidateSolution(currentSolution);     //go and see the method. you have to fill it

            int newEnergy = newBetterSolution.getEnergy();
            int currentEnergy = currentSolution.getEnergy();            
            
            boolean newEnergyAccepted = false;
            double acceptanceProb = getAccepProbability(currentEnergy, newEnergy, temperature);
            
            // Decide if we should accept the neighbour
            if (acceptanceProb > Math.random()) {
                currentSolution = new CandidateSolution(newBetterSolution);
            }
            
            
            
            if (currentSolution.getEnergy() < bestSoluteion.hashCode()) { // i don't know whether this condition is correct or wrong. let's put it that way just for now.
                bestSoluteion = new CandidateSolution(currentSolution);
                newEnergyAccepted = true;
            }            
                        
            System.out.println("temperature         :" + temperature);
            System.out.println("currentEnergy       :" + currentEnergy);
            System.out.println("newEnergy           :" + newEnergy);
            System.out.println("acceptanceProb      :" + acceptanceProb);
            System.out.println("newEnergyAccepted   :" + newEnergyAccepted);
            System.out.println("");
            
            temperature -= coolingTemperature;
        }
        System.out.println("currentSolution.getEnergy() 2: " + currentSolution.getEnergy());
    }
    
    public double getAccepProbability(int energy, int newEnergy, double currentTemperature) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp((energy - newEnergy) / currentTemperature);
    }
}
