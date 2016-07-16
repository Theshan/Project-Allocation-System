/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

/**
 *
 * @author Nadeesha
 */
public class ProjectAllocationSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filename                             = "src/projectallocationsystem/projectAllocationData.tsv";
        PreferenceTable preferenceTable             = new PreferenceTable(filename);
        preferenceTable.fillPreferencesOfAll(10);
        CandidateSolution sol                       = new CandidateSolution(preferenceTable);
//        System.out.println("Sol Energy              : "+ sol.getEnergy());
//        System.out.println("Sol Fitness             : "+ sol.getFitness());
        
        SimulatedAnnealing annealedSolution = new SimulatedAnnealing(sol);
        Report rp = new Report(annealedSolution, preferenceTable);
    }
    
}
