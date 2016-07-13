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
    private CandidateSolution cand;
    public SimulatedAnnealing(CandidateSolution cand){
        this.cand = cand;
    }
    
    public CandidateSolution getModifiedBestSolution() {
        
        return this.cand;
    }
}
