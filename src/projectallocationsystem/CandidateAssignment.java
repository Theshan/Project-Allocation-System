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
public class CandidateAssignment {
    private StudentEntry student;
	private String project;
	private String previousProject;
	
	CandidateAssignment(StudentEntry student) {
		this.student 		= student;
		this.project 		= "";
		this.previousProject 	= "";
		randomizeAssignment();
	}
	
	public void setProject(String project) {
		this.previousProject 	= this.project;
		this.project 		= project;
	}
	
	public String getAssignedProject() {
		return this.project;
	}
	
	public StudentEntry getStudentEntry() {
		return this.student;
	}
	
	public void randomizeAssignment() {
		String newPref = "";
		do{
			newPref = this.student.getRandomPreference();
			this.setProject(newPref);
		} while(newPref == null);
	}
	
	public void undoChange() {
		this.project = this.previousProject;
	}
	
	public int getEnergy() {
		int rank = this.student.getRanking(project);
		return (rank + 2) * (rank + 2);
	}
}
