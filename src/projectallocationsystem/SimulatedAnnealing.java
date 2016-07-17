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
    private double temperature = 2;
    private double coolingTemperature = 1;
    private int startingEnergy;
    private CandidateSolution bestSoluteion;
    
    public SimulatedAnnealing(CandidateSolution cand){
        this.currentSolution = cand;
        this.bestSoluteion = cand;
        doAnealing();
    }
    
    public CandidateSolution getModifiedBestSolution() {
        return this.bestSoluteion;
    }
    
    private void doAnealing() {
        startingEnergy = currentSolution.getEnergy();
        System.out.println("");
        
        bestSoluteion = new CandidateSolution(currentSolution);
        
        while(temperature > 0) {
            System.out.println("temperature                 :" + temperature);
            
            CandidateSolution newBetterSolution = new CandidateSolution(currentSolution);     //go and see the method. you have to fill it
            newBetterSolution.updateToBetterSolution();
            
            int currentEnergy   = currentSolution.getEnergy();
            int newEnergy       = newBetterSolution.getEnergy();
            
            System.out.println("currentEnergy at start      :" + currentEnergy);
            System.out.println("newEnergy                   :" + newEnergy);
            
            boolean newSolAcceptedAsCurrentSol = false;
            double acceptanceProb   = getAccepProbability(currentEnergy, newEnergy, temperature);
            double mathRand         = Math.random();
            System.out.println("acceptanceProb              :" + acceptanceProb);
            System.out.println("mathRand                    :" + mathRand);
            // Decide if we should accept the neighbour
            if (acceptanceProb > mathRand) {
                currentSolution = new CandidateSolution(newBetterSolution);
                newSolAcceptedAsCurrentSol = true;
            }
            
            currentEnergy   = currentSolution.getEnergy();
            System.out.println("newSolAcceptedAsCurrentSol  :" + newSolAcceptedAsCurrentSol);
            System.out.println("currentEnergy after newSol  :" + currentEnergy);
            System.out.println("");
            
            boolean currentSolAcceptedAsBestSol = false;
            System.out.println("bestSoluteion at start      :" + bestSoluteion.getEnergy());
            if (currentSolution.getEnergy() < bestSoluteion.hashCode()) { // i don't know whether this condition is correct or wrong. let's put it that way just for now.
                bestSoluteion = new CandidateSolution(currentSolution);
                currentSolAcceptedAsBestSol = true;
            }

            System.out.println("currentSolAcceptedAsBestSol :" + currentSolAcceptedAsBestSol);
            System.out.println("bestSoluteion at after      :" + bestSoluteion.getEnergy());
            System.out.println("");
            System.out.println("");
            System.out.println("");
            
            temperature -= coolingTemperature;
        }
        System.out.println("startingEnergy  : " + startingEnergy);
        System.out.println("bestEnergy      : " + bestSoluteion.getEnergy());
    }
    
    public double getAccepProbability(int energy, int newEnergy, double currentTemperature) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp((energy - newEnergy) / currentTemperature);
    }
}
