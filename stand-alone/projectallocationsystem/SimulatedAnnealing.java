/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

/**
 *
 * @author Olympians
 */
public class SimulatedAnnealing {
    private CandidateSolution currentSolution;
    private double temperature          = 10;
    private double coolingTemperature   = 1;
    private int startingEnergy;
    private CandidateSolution bestSoluteion;
    
    public SimulatedAnnealing(CandidateSolution cand){
        this.currentSolution            = cand;
        this.bestSoluteion              = cand;
        doAnealing();
    }
    
    public CandidateSolution getModifiedBestSolution() {
        return this.bestSoluteion;
    }
    
    private void doAnealing() {
        startingEnergy              = currentSolution.getEnergy();
        System.out.println("====================================== Simulated Annealing Execution ======================================");
        bestSoluteion = new CandidateSolution(currentSolution);
        
        while(temperature > 0) {
            CandidateSolution newBetterSolution = new CandidateSolution(currentSolution);
            newBetterSolution.updateToBetterSolution();
            
            int currentEnergy       = currentSolution.getEnergy();
            int newEnergy           = newBetterSolution.getEnergy();
            
//            System.out.println("currentEnergy at start      :" + currentEnergy);
//            System.out.println("newEnergy                   :" + newEnergy);
            
            boolean newSolAcceptedAsCurrentSol = false;
            double acceptanceProb   = getAccepProbability(currentEnergy, newEnergy, temperature);
            double mathRand         = Math.random();
            
//            System.out.println("acceptanceProb              :" + acceptanceProb);
//            System.out.println("mathRand                    :" + mathRand);
            
            // Decide if we should accept the newBetterSolutions
            if (acceptanceProb > mathRand) {
                currentSolution             = new CandidateSolution(newBetterSolution);
                newSolAcceptedAsCurrentSol  = true;
            }
            
            currentEnergy                   = currentSolution.getEnergy();
//            System.out.println("newSolAcceptedAsCurrentSol  :" + newSolAcceptedAsCurrentSol);
//            System.out.println("currentEnergy after newSol  :" + currentEnergy);
            
            boolean currentSolAcceptedAsBestSol = false;
//            System.out.println("bestSoluteion at start      :" + bestSoluteion.getEnergy());
            if (currentSolution.getEnergy() < bestSoluteion.hashCode()) {
                bestSoluteion = new CandidateSolution(currentSolution);
                currentSolAcceptedAsBestSol = true;
            }

//            System.out.println("currentSolAcceptedAsBestSol :" + currentSolAcceptedAsBestSol);
            System.out.println("Temperature : " + temperature + ", Energy :" + bestSoluteion.getEnergy());
            
            temperature -= coolingTemperature;
        }
        System.out.println("");
        System.out.println("Starting Energy  : " + startingEnergy);
        System.out.println("Best Energy      : " + bestSoluteion.getEnergy());
        System.out.println("");
    }
    
    public double getAccepProbability(int energy, int newEnergy, double currentTemperature) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp((energy - newEnergy) / currentTemperature);
    }
}
