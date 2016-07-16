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
 * @author adventure-ro
 */
public class Report {
    private SimulatedAnnealing sa;
    private CandidateSolution smSolution;
    private Hashtable<String, CandidateAssignment> finalSaMap; 
    private PreferenceTable pref;
    
    public Report(SimulatedAnnealing simAn, PreferenceTable prefTable){
        this.sa = simAn;
        this.pref = prefTable;
        
        smSolution = sa.getModifiedBestSolution();
        finalSaMap=smSolution.getCandidateAssignmentsMap();
        Set<String> keySet = finalSaMap.keySet();
        java.util.Iterator<String> it = keySet.iterator();
        while(it.hasNext()){
            String studentName = it.next();
            CandidateAssignment cand = finalSaMap.get(studentName);          
            
            System.out.println("student 			: "+ cand.getStudentEntry().getStudentName());
            System.out.println("project 			: "+ cand.getAssignedProject());
            
            StudentEntry student = cand.getStudentEntry();
            int rank = student.getRanking(cand.getAssignedProject());
            int prefCount = student.getNumberOfPreferencedProjects();
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
        
        
    }
        
}
