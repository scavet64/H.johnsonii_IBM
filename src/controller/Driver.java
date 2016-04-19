package controller;

import view.MainGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.SimulationOptions;


public class Driver {
	
	static BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		System.setProperty("sun.java2d.opengl", "true");
		//MainGUI menuGUI = new MainGUI();
		
		runTest();
		System.out.println("Finished Everything! :^)");
		//Simulation sim = new Simulation();
		//sim.runSimulation(0, 180);
	}

	private static void runTest() throws Exception {
		System.out.println("How many trials: ");
		String input = stdin.readLine().trim();
		int numberTrials = Integer.parseInt(input);
		
		SimulationOptions simOpt = SimulationOptions.getInstance();
		
		int[] freqArray = {1,3,5,7,10,15,30, 365, 9999};
		
		//simulate all storm intensities
		for(int i = 0; i < 4; i++){
			
			simOpt.setLowerQuartile(i);
			System.out.println("Setting Lower Quartile: " + i);
			
			//iterate through frequencies
			for(int j = 0; j < freqArray.length; j++){
				
				simOpt.setFrequencyOfStorms(freqArray[j]);
				System.out.println("Setting Frequency: " + freqArray[j]);
				
				//generate replicates of same treatment and frequency
				for(int k= 0; k < numberTrials; k++){
					System.out.print("Starting Simulation: " + k + " ~~~ Frequency: " + freqArray[j] + " ~~~ Intensity: " + i + " ... ");
					new Simulation(simOpt).runSimulation();
					
				}
				
			}
		}
		
		
	}

}
