/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Nadeesha
 */
public class StudentEntry {
    private String studentName;
	private boolean preAssigned;
	private String preAssignedProject           = "";
	private Vector<String> preferredProjects    = new Vector<String>();
	private Random rnd                          = new Random();
	private int preferredProjecsCount;
	
	StudentEntry(String studentName) {
		this.studentName                    = studentName;
		this.preAssigned                    = false;
		this.preferredProjecsCount          = 0;
	}
	
	String getStudentName() {
		return this.studentName;
	}
	
	Vector<String> getOrderedPreferences() {
		return this.preferredProjects;
	}
	
	void addPreassignedProject(String pName){
		this.preAssignedProject     = pName;
		this.preferredProjecsCount  = 0;
		this.preAssigned            = true;
	}
	
	void addPreferedProjects(Vector<String> projects){
		this.preferredProjects      = projects;
		this.preferredProjecsCount  = projects.size();
		this.preAssigned            = false;
	}
	
	Boolean hasPreassignedProject() {
		return this.preAssigned;
	}
	
	int getNumberOfPreferencedProjects() {
		return this.preferredProjecsCount;
	}
	
	void addProject(String pName) {
		this.preferredProjects.add(pName);
	}
	
	public String getRandomPreference() {
		int preferenceCount 		= preferredProjects.size();
		if (preferenceCount > 0) {
			int index 		= rnd.nextInt(preferenceCount) + 0;
			String randomProject 	= preferredProjects.elementAt(index).intern();
			return randomProject;
		} else {
			return null;
		}	
	}
	
	public boolean hasPreference(String preference) {
		return preferredProjects.contains(preference.intern());
	}
	
	public String toString() {
		String returnString = "";
		returnString += "student name  	    : " + this.studentName + "\n";
		returnString += "*** prefered or preferenced projects ****\n";
		if (this.preAssigned) {
			returnString += this.preAssignedProject;
		} else {
			Vector<String> tableRow = this.preferredProjects;
			java.util.Iterator<String> itrRow = tableRow.iterator();
			int i = 0;
			while(itrRow.hasNext()) {
				String token = itrRow.next();
				returnString += i+". "+token + "\n";
				i++;
			}
		}
		return returnString;
	}
	
	public int getRanking(String project) {
		if (this.hasPreference(project)) {
			return this.preferredProjects.indexOf(project.intern());
		} else {
			return -1;
		}
	}
    
}
