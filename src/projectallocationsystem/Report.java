/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author Olympians
 */
public class Report {
    private SimulatedAnnealing sa;
    private CandidateSolution smSolution;
    private Hashtable<String, CandidateAssignment> finalSaMap;
    private GeneticAlgorithm ga;
    private CandidateSolution gaSolution;
    private Hashtable<String, CandidateAssignment> finalGaMap;
    
    public Report(SimulatedAnnealing simAn, GeneticAlgorithm genAll){
        this.sa = simAn;
        this.ga = genAll;
    }
    
    public void geneticAllgorithmReport(){
        System.out.println("");
        System.out.println("====================================== Genetic Algorithm Report ======================================");
        
        gaSolution                      = ga.getFittestSolution();
        finalGaMap                      = gaSolution.getCandidateAssignmentsMap();
        
        Set<String> keySet              = finalGaMap.keySet();
        java.util.Iterator<String> it   = keySet.iterator();
        while(it.hasNext()){
            String studentName          = it.next();
            CandidateAssignment cand    = finalGaMap.get(studentName);          
            
            System.out.println("student 			: "+ cand.getStudentEntry().getStudentName());
            System.out.println("project 			: "+ cand.getAssignedProject());
            
            StudentEntry student    = cand.getStudentEntry();
            int rank                = student.getRanking(cand.getAssignedProject());
            int prefCount           = student.getNumberOfPreferencedProjects();
            if(rank == -1){
                System.out.println("Project not on Prefered List");
            }
            else if(rank > prefCount){
                System.out.println("Project not on Prefered List");
            }
            else{
                System.out.println("Project Rank   :" +rank);
            }
            System.out.println("");
        }
        System.out.println("The Final Fittness    :" +gaSolution.getFitness());
    }
    
    public void simulatedAnnealingReport(){
        System.out.println("");
        System.out.println("====================================== Simulated Annealing Report ======================================");
        
        smSolution                      = sa.getModifiedBestSolution();
        finalSaMap                      = smSolution.getCandidateAssignmentsMap();
        
        Set<String> keySet              = finalSaMap.keySet();
        java.util.Iterator<String> it   = keySet.iterator();
        while(it.hasNext()){
            String studentName          = it.next();
            CandidateAssignment cand    = finalSaMap.get(studentName);          
            
            System.out.println("student 			: "+ cand.getStudentEntry().getStudentName());
            System.out.println("project 			: "+ cand.getAssignedProject());
            
            StudentEntry student        = cand.getStudentEntry();
            int rank                    = student.getRanking(cand.getAssignedProject());
            int prefCount               = student.getNumberOfPreferencedProjects();
            if(rank == -1){
                System.out.println("Project not on Prefered List");
            }
            else if(rank > prefCount){
                System.out.println("Project not on Prefered List");
            }
            else{
                System.out.println("Project Rank   :" + rank);
            }
            System.out.println("");
        }
        System.out.println("The Final Energy    :" + smSolution.getEnergy());
    }
}
