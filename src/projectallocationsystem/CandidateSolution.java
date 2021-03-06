/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

import java.util.Hashtable;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Olympians
 */
public class CandidateSolution {
    private Vector<CandidateAssignment> assignments				= new Vector<CandidateAssignment>(); 
    private Hashtable<String, CandidateAssignment> candidateAssignmentsMap 	= new Hashtable<String, CandidateAssignment>();
    private Hashtable<String, Integer> noOfStdAssinedToProject                  = new Hashtable<String, Integer>();
    private final int paneltyConstant                                           = 1000;
    private int panelty 													= 0;
    private int energy 														= 0;	
    PreferenceTable preferenceTable;

    private Random rnd = new Random();
    private static int maxFitness = 0;

    public CandidateSolution(CandidateSolution another) {
        this.assignments                = another.assignments;
        this.candidateAssignmentsMap    = another.candidateAssignmentsMap;
        this.noOfStdAssinedToProject    = another.noOfStdAssinedToProject;
        this.panelty                    = another.panelty;
        this.energy                     = another.energy;
        this.preferenceTable            = another.preferenceTable;
    }

    public CandidateSolution(PreferenceTable pref){
            this.preferenceTable 			= pref;
            Vector<StudentEntry> studentEntries 	= preferenceTable.getAllStudentEntries();
            java.util.Iterator<StudentEntry> itrRow     = studentEntries.iterator();
            while(itrRow.hasNext()) {
                    StudentEntry student 		= itrRow.next();
                    CandidateAssignment cand            = new CandidateAssignment(student);

                    if(noOfStdAssinedToProject.containsKey(cand.getAssignedProject().intern())) {
                            noOfStdAssinedToProject.put(cand.getAssignedProject().intern(), noOfStdAssinedToProject.get(cand.getAssignedProject().intern()) + 1);   
                            panelty += paneltyConstant;
                    } else {
                            noOfStdAssinedToProject.put(cand.getAssignedProject().intern(),1);
                    }

                    candidateAssignmentsMap.put(student.getStudentName(), cand);
                    assignments.addElement(cand);

                    energy += cand.getEnergy();
            }		
    }

    public void updateToBetterSolution() {
        //iterate and get each candidateAssignment instance of the solution
        //call updateToBetterAssignment method in CandidateAssignment from the instance
        //candAss.updateToBetterAssignment()
        //after you iterate all the students now you have a better version of cand solution
        assignments				= new Vector<CandidateAssignment>(); //reseting
        noOfStdAssinedToProject 		= new Hashtable<String, Integer>(); //reseting
        energy                                  = 0;
        panelty                                 = 0;
        
        Set<String> keySet = candidateAssignmentsMap.keySet();
        java.util.Iterator<String> it           = keySet.iterator();
        while(it.hasNext()){
            String studentName                  = it.next();
            CandidateAssignment cand            = candidateAssignmentsMap.get(studentName);            
            cand.updateToBetterAssignment();
            if(noOfStdAssinedToProject.containsKey(cand.getAssignedProject().intern())) {
                    noOfStdAssinedToProject.put(cand.getAssignedProject().intern(), noOfStdAssinedToProject.get(cand.getAssignedProject().intern()) + 1);   
                    panelty += paneltyConstant;
            } else {
                    noOfStdAssinedToProject.put(cand.getAssignedProject().intern(),1);
            }

            candidateAssignmentsMap.put(studentName, cand);
            assignments.addElement(cand);

            energy += cand.getEnergy();
        }
    }
    
     public void updateSolution() {
        assignments				= new Vector<CandidateAssignment>(); //reseting
        noOfStdAssinedToProject 		= new Hashtable<String, Integer>(); //reseting
        energy                                  = 0;
        panelty                                 = 0;
        
        Set<String> keySet = candidateAssignmentsMap.keySet();
        java.util.Iterator<String> it = keySet.iterator();
        while(it.hasNext()){
            String studentName = it.next();
            CandidateAssignment cand = candidateAssignmentsMap.get(studentName);            
            if(noOfStdAssinedToProject.containsKey(cand.getAssignedProject().intern())) {
                    noOfStdAssinedToProject.put(cand.getAssignedProject().intern(), noOfStdAssinedToProject.get(cand.getAssignedProject().intern()) + 1);   
                    panelty += paneltyConstant;
            } else {
                    noOfStdAssinedToProject.put(cand.getAssignedProject().intern(),1);
            }
            assignments.addElement(cand);
            energy += cand.getEnergy();
        }
    }

    public CandidateAssignment getRandomAssignment() {
            int index = rnd.nextInt(candidateAssignmentsMap.size()) + 0;
            return assignments.get(index);
    }

    public CandidateAssignment getAssignmentFor(String sName) {
            return candidateAssignmentsMap.get(sName);
    }

    public int getEnergy() {
            return energy + panelty;
    }

    public int getFitness() {
            return (-1) * getEnergy();
    }

    static public int getMaxFitness() {
        return maxFitness;
    }

    public Hashtable<String, CandidateAssignment> getCandidateAssignmentsMap() {
        return this.candidateAssignmentsMap;
    }

    public CandidateAssignment getGene(String StudentName) {
        return this.candidateAssignmentsMap.get(StudentName);
    }

    void setGene(String studentName, CandidateAssignment cand) {
        candidateAssignmentsMap.put(studentName, cand);
        updateSolution();
    }
}
