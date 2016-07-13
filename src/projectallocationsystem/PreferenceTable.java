/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectallocationsystem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author Nadeesha
 */
public class PreferenceTable {
    private Vector<Vector<String>> studentEntriesLoadedFromFile 	= new Vector<Vector<String>>();				//holds vectors of tokens retrieve from each line of the data file
	private Vector<StudentEntry> studentEntries 			= new Vector<StudentEntry>(); 				//holds StudentEntries
	private Hashtable<String, StudentEntry> studentLookup 		= new Hashtable<String, StudentEntry>();	//HashTable that maps StudentEntry objects to student names
	private Random rnd 												= new Random();

	PreferenceTable() {	
	}
	
	public PreferenceTable(String fileName){
		loadContentFromFile(fileName);
	}
	
	private void loadContentFromFile(String fileName) {
		try {
			FileInputStream stream 	= new FileInputStream(fileName);
			BufferedReader input 	= new BufferedReader(new InputStreamReader(stream));
			try {
				int studentCount    = 0;
				String line         = input.readLine();
				while(line != null) {													//iterate each line of data table 
					if (studentCount != 0) {
						StringTokenizer tokens = new StringTokenizer(line, "\t");		//tokenize each string with the delemeter '\t'
						
						int i 						= 0;
						Vector<String> tableRow 	= new Vector<String>();				//holds tokens of the table row as a vector
						Vector<String> preferences 	= new Vector<String>();				//holds preferences of each student
						String preferredProject 	= "";								//holds preferred project if that student is pre-arranged
						String name 			= "";								//student name
						String prearrangedString 	= "";								//stores "Yes" or "No" value for later user
						Boolean prearranged 		= false;							//boolean value of above variable 
						
						while(tokens.hasMoreTokens()) {
							
							String token = tokens.nextToken();							//get each cell of the table row as token
							tableRow.add(token);										//stores tokens table row as a vector
							
							if (i == 0) {
								name = token;
							} else if (i == 1) {
								prearrangedString 	= token;
								if (prearrangedString.equalsIgnoreCase("No")) {			//check whether pre-arranged or not
									prearranged 	= false;								
								} else {
									prearranged 	= true;
								}
							} else {
								if (prearranged) {
									preferredProject = token;							//stores preferences of each student if pre-arranged
								} else {
									preferences.add(token);								//stores preferences of each student if not pre-arranged
								}
							}
							studentEntriesLoadedFromFile.add(tableRow);					//store table row vectors
							i++;
						}
						StudentEntry student = new StudentEntry(name);					//create SudentEntries
						if (prearranged) {												//add preferred project or preferences
							student.addPreassignedProject(preferredProject);			
						} else {
							student.addPreferedProjects(preferences);
						}
						studentEntries.addElement(student);								//Stores StudentEntries
						studentLookup.put(student.getStudentName(), student);			//map StudentEntries to student names
					}
					studentCount++;
					line = input.readLine();
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	Vector<StudentEntry> getAllStudentEntries() {		//returns StudentEntries vector
		return this.studentEntries;
	}
	
	StudentEntry getEntryFor(String sName) {			//returns StudentEntry for specific student name searched from the HashTable
		StudentEntry entry = (StudentEntry)this.studentLookup.get(sName);
		if (entry != null) {
			return entry;
		} else {
			return null;
		}
	}
	
	void printStudentEntriesLoadedFromFile() {			//print all the tokens
		java.util.Iterator<Vector<String>> itrTable = studentEntriesLoadedFromFile.iterator();
		while(itrTable.hasNext()) {
			Vector<String> tableRow             = itrTable.next();
			java.util.Iterator<String> itrRow   = tableRow.iterator();
			while(itrRow.hasNext()) {
				String token                = itrRow.next();
				System.out.println("iterate token : "+ token);
			}
		}
	}
	
	public StudentEntry getRandomStudent() {
		int index	 	= rnd.nextInt(studentEntries.size()) + 0;
		StudentEntry student 	= studentEntries.elementAt(index);
		return student;		
	}
	
	public String getRandomPreference() {
		StudentEntry student 			= getRandomStudent();
		int preferenceCount 			= student.getNumberOfPreferencedProjects();
		if (preferenceCount > 0) {
			int index                       = rnd.nextInt(preferenceCount) + 0;
			Vector<String> preferences 	= student.getOrderedPreferences();
			String randomProject 		= preferences.elementAt(index).intern();
			return randomProject;
		} else {
			return null;
		}		
	}
	
	public void fillPreferencesOfAll(int maxPrefs) {
		java.util.Iterator<StudentEntry> itrRow = studentEntries.iterator();
		while(itrRow.hasNext()) {
			StudentEntry student    = itrRow.next();
			int prefCount           = student.getNumberOfPreferencedProjects();
			while (prefCount < maxPrefs) {
				String newPref 	= getRandomPreference();
				if(newPref != null){
					Vector<String> studentPrefs = student.getOrderedPreferences();
					if (student.hasPreassignedProject() == false) {
						if (!student.hasPreference(newPref.intern())) {
							student.addProject(newPref.intern());
						}
					} else {
						if (!student.hasPreference(newPref.intern())) {
							student.addProject(newPref.intern());
						}
					}
				}
				prefCount = student.getOrderedPreferences().size();
			}
			System.out.println("after filling projects to preferences of this student***");
			System.out.println(student.toString());
		}
	}	
}
