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
public class GeneticAlgorithm {
    private PreferenceTable preferenceTable;
    private Population population;
    
    
    public GeneticAlgorithm(PreferenceTable pref){
        this.preferenceTable = pref;
        generatePopulation();
    }
    
    public void generatePopulation(){
        
    }
}
