package controller;

import java.awt.Dimension;
import java.awt.geom.Line2D;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import model.Cell;
import model.Field;
import model.Location;
import model.Patch;
import model.Seagrass;
import model.SimulationOptions;
import view.SimulationGUI;
import view.SimulationView;

/**
 * The Simulation class for the Halophila johnsonii individual based model
 * Much of this model has been based upon the existing FORTRAN code provided 
 * and written by Dr. Richmond and Dr. Rose.
 * 
 * @author Vincent Scavetta
 * @version 2/11/16
 *
 *TODO: Think about removing day and year counter datafields
 *		Continue working on simulation
 *
 */
public class Simulation {
	
	//Attributes that are set from SimulationOptions class
	private final int XLENGTH;						//Distance along the shoreline 	(was NROW)
	private final int YLENGTH;						//Distance from the shoreline	(was NCOL)
	private final int MAX_NODES;					//Maximum number of nodes for the simulation
	private final int numberOfRecuits;				//number of starting nodes
	
	//Attributes that are hard coded
	//private final double SEAFLOOR_SLOPE = 0.01;		//The slope of the seafloor from the shore **IF CELL IS A METER^2**
	//private final double SEAFLOOR_SLOPE = 0.0001;		//The slope of the seafloor from the shore **IF CELL IS CENTIMETER^2**
	private final double SEAFLOOR_SLOPE = 0.001;		//The slope of the seafloor from the shore **IF CELL IS DECIMETER^2**
	public static final double CELL_AREA = 1;			//The area of each cell (1^2 decimeter)
	private double LIGHT_ATTENUATION = 0.02;			//light attenuation coefficient due to everything but shading by other seagrasses
	
	//file writers
	private PrintStream LIGHTOUTPUT;
	private PrintStream NODEOUTPUT;
	private PrintStream ELEVATIONOUTPUT;
	private PrintStream BIOMASSOUTPUT;
	private PrintStream DEPTHOUTPUT;
	private PrintStream DAILYOUTPUT;
	private PrintStream STATISTICSOUTPUT;
	
	//Data structures
	private ArrayList<Seagrass> population;			//population of seagrass
	private ArrayList<Seagrass> perishedPopulation;	//dead population of seagrass
	private int[] nodesCreateOnDay;					//int array that holds the number of nodes born on the given day(index)
	//private Cell[][] field;
	private Field field;							//Field of cells for the simulation
	
	//counters
	private int dayCounter;							//counts the days of the simulation
	private int yearCounter;						//counts the years of the simulation
	private int runningIDCounter;					//holds the value that represents the next available id 
	private int numNodesCreatedToday;				//Counts the number of nodes that were created on the day
	
	//Extra utilities required
	private SimulationGUI simGUI;					//simulation view that is needed to repaint the nodes
	private SimulationOptions simOptions;			//holds the changeable options for this run of the simulation
	private final Random rng = new Random();		//Random number generator
	
	public Simulation(SimulationOptions simOptions) {
		this.simOptions = simOptions;
		
		//Setup Counters
		dayCounter = 0;
		yearCounter = 0;
		numNodesCreatedToday = 0;
		
		//Setup Variable Options
		numberOfRecuits = 	simOptions.getNumRecruits();
		XLENGTH 		= 	simOptions.getxLength();				
		YLENGTH 		= 	simOptions.getyLength();				
		MAX_NODES 		= 	simOptions.getMaxNodes();	
		
		//setup field and population arrays
		field = new Field(XLENGTH,YLENGTH);
		population = new ArrayList<Seagrass>(MAX_NODES);
		perishedPopulation = new ArrayList<Seagrass>();
	
		setPrintStreams();
	}
	
	public void setSimGUI(SimulationGUI simGUI){
		this.simGUI = simGUI;
	}
	
	private void setPrintStreams() {
		try {
			LIGHTOUTPUT = new PrintStream(new File("Light.txt"));			
			NODEOUTPUT = new PrintStream(new File("Nodes.txt"));			
			ELEVATIONOUTPUT = new PrintStream(new File("Elevation.txt"));			
			BIOMASSOUTPUT = new PrintStream(new File("biomass.txt"));
			DEPTHOUTPUT = new PrintStream(new File("Depth.txt"));
			DAILYOUTPUT = new PrintStream(new File("Daily.txt"));
			STATISTICSOUTPUT = new PrintStream(new File("Statistics.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Runs the simulation with the length of time set by the SimulationOptions class
	 * @throws Exception Throws an Exception if the user wants to stop the simulation, stopping the thread
	 */
	public void runSimulation() throws Exception{
		int numberYears = simOptions.getNumberYears();
		int numberDays = simOptions.getNumberDays();
		runSimulation(numberYears, numberDays);
	}

	/**
	 * This method will run the simulation for the amount of time designated by the user.
	 * @throws Exception Throws an Exception if the user wants to stop the simulation, stopping the thread
	 */
	public void runSimulation(int years, int days) throws Exception{
		printHeaders();
		setupGrid();
		setupInitialConditions();
		
		//converts years into days 
		int daysToRun = days + (years * 365);
		
		//creates an array 
		nodesCreateOnDay = new int[daysToRun+1];
		
		while(dayCounter <= daysToRun){			
			//resets the node counter to zero
			numNodesCreatedToday = 0;
			
			//updates the cell conditions for the day
			updateCellsForDay();
			
			//simulate the population given the current conditions
			simulatePopulation();
			
			//print current population every month
			if(dayCounter % 30 == 0){
				dailyOut();
			}
			
			//prints the current year, day, population size, perished population size, and number of nodes created today.
//			DAILYOUTPUT.println("Current Year: " + yearCounter + " Current day: " + dayCounter + " Population Size: " + population.size() 
//			+ " Dead Population " + perishedPopulation.size() + " Nodes Created Today: " + numNodesCreatedToday);
			DAILYOUTPUT.println(yearCounter + "\t" + dayCounter + "\t" + population.size() + "\t\t" + perishedPopulation.size() + "\t\t" + numNodesCreatedToday);
			
//			System.out.println("Current Year: " + yearCounter + " Current day: " + dayCounter + " Population Size: " + population.size() 
//								+ " Dead Population " + perishedPopulation.size() + " Nodes Created Today: " + numNodesCreatedToday);
			
			//increment the day
			//System.out.println(dayCounter);
			simGUI.repaintView();
			simGUI.updateDayLabel(dayCounter);
			simGUI.updatePopulationLabel(population.size());
			dayCounter++;
			//Thread.sleep(100);
			
			
		}
		
		//calculate density and report for each recruit
		printDensityForEachPatch();
		System.out.println("All Done");
		
	}

	private void printHeaders() {
		String printString = "CurrentDay \t Xlocation \t YLocation ";
		
		NODEOUTPUT.println("Year\tDay\tNode ");
		ELEVATIONOUTPUT.println("X\tY\tElevation");
		DAILYOUTPUT.println("Year\tDay\tPopulation\tPerishedSize\tNodes Created");
		BIOMASSOUTPUT.println(printString + "\tBioMass");
		LIGHTOUTPUT.println(printString + "\tLightLevel");
		DEPTHOUTPUT.println(printString + "\tWaterDepth");
		STATISTICSOUTPUT.println("PatchID\tPop\tDensity Mean\t\tDensity Stdev\t\tLight Mean\t\tLight Stdev\t\tDepth mean\t\tDepth Stdev");
		
	}

	/**
	 * Based upon subroutine 'initial'
	 * 
	 * Set up the simulation with a few nodes in the population
	 */
	private void setupInitialConditions() {
		for(int i = 0; i < numberOfRecuits; i++){
			//generate a random location for the seagrass to grow
			int randomX = (int) (rng.nextDouble()*XLENGTH);
			int randomY = (int) (rng.nextDouble()*YLENGTH);
			
			Location randLoc = new Location(randomX, randomY);
	
										//ID, age, Location, isApical, angle, MotherID)
			population.add(new Seagrass(runningIDCounter, 1, randLoc, true, 0.0, runningIDCounter, Seagrass.PRIMARYAXIS, runningIDCounter));
			runningIDCounter++;
		}
		
		//for testing zoom
		Location loc = new Location(5, 5);
		population.add(new Seagrass(runningIDCounter, 1, loc, true, 0.0, runningIDCounter, Seagrass.PRIMARYAXIS, runningIDCounter));
		runningIDCounter++;
		
	}

	/**
	 * TODO:
	 * 
	 * 
	 * 		Notes
	 * @throws Exception 
	 * 
	 */
	private void simulatePopulation() throws Exception {
		//create a queue that will hold the nodes created today
		LinkedBlockingQueue<Seagrass> newNodesForTheDay = new LinkedBlockingQueue<Seagrass>(); 
		LinkedBlockingQueue<Seagrass> perishedNodesForTheDay = new LinkedBlockingQueue<Seagrass>(); 
		int populationSize = population.size();
		
		for(int i = 0; i < populationSize; i++){
			//Get the current node to work with and its location
			Seagrass node = population.get(i);
			
			//get the cell the node is located in
			Cell nodesCell = field.getCellFromLocation(node.getLocation());
			
			//call the nodes growth method and pass it the cell's light
			node.growth(nodesCell.getSeaFloorlight());
			
			//check to see if the development progress has reached completion.  >= 1 is complete
			if(node.getDevelopmentProgress() >= 1 && (node.isApical() || !node.isHasBranched())){
				
				//if the population reaches the max, stop the adding process
				if(populationSize >= MAX_NODES){
					System.out.println("reached Max Nodes");
					throw new Exception();
				} else {
					//this will determine if the node is branching or continuing on its axis
					//If branching, there is a chance it may not occur
					if(!node.isApical()){
						if(rng.nextInt(100) < 25){
							//newNodesForTheDay.add(node.createChild(XLENGTH, YLENGTH, runningIDCounter, dayCounter));
							
							Seagrass newNode = node.createChild(XLENGTH, YLENGTH, runningIDCounter, dayCounter);
							newNodesForTheDay.add(newNode);
							field.getCellFromLocation(node.getLocation()).addSeagrass();
							runningIDCounter++;
							numNodesCreatedToday++;
						} else {
							node.setDevelopmentProgress(0);
							node.setDistanceFromMother(0);
						}
					} else {
						//newNodesForTheDay.add(node.createChild(XLENGTH, YLENGTH, runningIDCounter, dayCounter));
						
						Seagrass newNode = node.createChild(XLENGTH, YLENGTH, runningIDCounter, dayCounter);
						newNodesForTheDay.add(newNode);
						field.getCellFromLocation(node.getLocation()).addSeagrass();
						runningIDCounter++;
						numNodesCreatedToday++;
					}
				}
			}
			
			//if the node is older than 50 days old, the node dies of old age
			if(node.getAge() > 50){
				node.setDayDied(dayCounter);
				field.getCellFromLocation(node.getLocation()).removeSeagrass();
				perishedNodesForTheDay.add(node);
			} else {
				//node.incrementAge();
			}
		}
		
		//removes the nodes that perished from the overall population
		int numberOfParishedNodes = perishedNodesForTheDay.size();
		for(int i = 0; i < numberOfParishedNodes; i++){
			Seagrass perishedNode = perishedNodesForTheDay.remove();
			population.remove(perishedNode);
			perishedPopulation.add(perishedNode);
		}
		
		//drainTo returns an integer value that represents the number of nodes created this day.
		nodesCreateOnDay[dayCounter] = newNodesForTheDay.drainTo(population);
		
	}
	
	/**
	 * Based upon subroutine 'dailyOut'
	 * 
	 * Ironical named as it only prints once a month
	 */
	private void dailyOut(){
		int populationSize = population.size();
		
		//run through the population
		for(int i = 0; i < populationSize; i++){
			//System.out.println(yearCounter + "\t" + dayCounter + "\t" + population.get(i).toString());
			NODEOUTPUT.println(yearCounter + "\t" + dayCounter + "\t" + population.get(i).toString());
		}
		
		//run through the cells
		for(int currentY = 0; currentY < YLENGTH; currentY++){
			
			for(int currentX = 0; currentX < XLENGTH; currentX++){
				Cell currentCell = field.getCellFromCords(currentX, currentY);
				String printString = dayCounter + "\t\t" + currentX + "\t\t" + currentY;
				
				BIOMASSOUTPUT.println(printString + "\t\t" + currentCell.getBioMass());
				LIGHTOUTPUT.println(printString + "\t\t" + currentCell.getSeaFloorlight());
				DEPTHOUTPUT.println(printString + "\t\t" + currentCell.getWaterDepth());
				
				//Old printing Style
//				String printString = "CurrentDay: " + dayCounter + " Xlocation: " + currentX + " YLocation: " + currentY;
//				
//					BIOMASSOUTPUT.println(printString + " BioMass: " + currentCell.getBioMass());
//					LIGHTOUTPUT.println(printString + " LightLevel: " + currentCell.getSeaFloorlight());
//					DEPTHOUTPUT.println(printString + " WaterDepth: " + currentCell.getWaterDepth());
			}
		}
		
	}

	/**
	 * Method will loop through the matrix of cells and give it new attributes for the current day.
	 * 
	 * The cells along the shoreline are updated first until XLENGTH number of cells have been created 
	 * in the first row. The same process is then repeated for the second row of cells from the shoreline. 
	 * This process continues until all cells have been updated.
	 */
	private void updateCellsForDay() {
		
		for(int currentY = 0; currentY < YLENGTH; currentY++){
			
			for(int currentX = 0; currentX < XLENGTH; currentX++){
				Cell cell = field.getCellFromCords(currentX, currentY);
				
				//assigns environmental factors for the current day
				cell.setWaterLevel(generateWaterLevel());		//FORTRAN: call waterdepth
				cell.generateWaterDepth();
				assignOtherSpecies(cell);
				assignCellLight(cell);
				
//				if(dayCounter == 0){
//					LIGHTOUTPUT.println("Xlocation: " + currentX + " YLocation: " + currentY + " LightLevel: " + cell.getSeaFloorlight());
//					LIGHTOUTPUT.flush();
//				}
				
			}
		}
	}

	/**
	 * Based upon subroutine 'setupgrid' with some expansions.
	 * Method will create and assign a cell for each coordinate in the field matrix
	 * 
	 * This loop within a loop will generate the cells for our grid. The cells along the shoreline
	 * are created first until XLENGTH number of cells have been created in the first row. The same
	 * process is then repeated for the second row of cells from the shoreline. This process continues
	 * until all cells have been created.
	 */
	private void setupGrid(){
		
		for(int currentY = 0; currentY < YLENGTH; currentY++){
			
			for(int currentX = 0; currentX < XLENGTH; currentX++){
				//creates the cell object
				Cell cell = new Cell();
				
				//assigns environmental factors
				cell.setElevation((currentY+1)*SEAFLOOR_SLOPE);
				cell.setWaterLevel(generateWaterLevel());
				cell.generateWaterDepth();
				
				//adds the cell to the field matrix
				field.addCell(cell, currentX, currentY);
				
				//print to output file
				ELEVATIONOUTPUT.println(currentX + "\t" + currentY + "\t" + cell.getElevation());
			}
		}
	}

	/**
	 * Based upon function 'wlevel'
	 * TODO: implement more functionality
	 * 
	 * @return the waterlevel for the current day
	 */
	private double generateWaterLevel() {
		return 0.5;
	}
	
	/**
	 * Based upon subroutine 'otherspecies'
	 * TODO: implement more functionality
	 * @param cell The cell that will be modified
	 */
	private void assignOtherSpecies(Cell cell){
		cell.setBioMass(1);
	}
	
	/**
	 * Based upon subroutine 'light'
	 * The surfacelight for the cell is calculated using the private method calculateSurfaceLight.
	 *
	 * TODO: implement more functionality
	 * @param cell The cell that will be modified
	 */
	private void assignCellLight(Cell cell){
		double surfaceLight = calculateSurfaceLight(dayCounter);		// from function surlight in FORTRAN code
		double seaFloorLight = surfaceLight * Math.exp(-LIGHT_ATTENUATION*cell.getWaterDepth()) * cell.getBioMass();
		if(seaFloorLight < 0.0){
			seaFloorLight = 0.0;
		}
		cell.setSeaFloorlight(seaFloorLight);
	}
	
	/**
	 * Based upon function 'surlight'
	 * As of 2/11 function will return the same value each time.
	 * TODO: implement more functionality
	 * @param day The day to calculate sunlight for
	 */
	private double calculateSurfaceLight(int day){
		return 0.9;
	}
	
	/**
	 * Function will assign the light attenuation (for the passed in day)??
	 * TODO: implement more functionality
	 * Ideas: 	have the attenuation fluctuate around a particular value and increase or decrease minimally
	 * 			have rare days where the attenuation will increase drastically to simulate when cedar water is released
	 * 
	 */
	private void assignLightAttenuationForDay(int day){
		//does something to k
	}
	
	/**
	 * 
	 */
	private void printDensityForEachPatch(){
		HashMap<Integer, Patch> patchIDtoPatchCollectionMap = getPatchesFromPopulation();
		System.out.println(patchIDtoPatchCollectionMap);
		for(Integer patchID: patchIDtoPatchCollectionMap.keySet()){
			//calculate the data for each patch
			Patch currentPatch = patchIDtoPatchCollectionMap.get(patchID);
			currentPatch.calculateMeansAndStdevsForPatch(field);
			
			//Print information about the patch
			STATISTICSOUTPUT.println(currentPatch);
		}
	}
	
	/**
	 * Returns an array of patches from the population
	 * TODO How?
	 * 		Run though population
	 * 			put every seagrass into map with patchID as key and queue as the value
	 * 				add the seagrass to the queue
	 */
	private HashMap<Integer, Patch> getPatchesFromPopulation(){
		HashMap<Integer, Patch> patchIDtoPatchCollectionMap = new HashMap<Integer, Patch>();
		
		for(Seagrass seagrass: population){
			Integer patchID = seagrass.getPatchID();
			if(patchIDtoPatchCollectionMap.containsKey(patchID)){
				patchIDtoPatchCollectionMap.get(patchID).add(seagrass);
			} else {
				Patch patch = new Patch(patchID);
				patch.add(seagrass);
				patchIDtoPatchCollectionMap.put(patchID, patch);
			}
		}
		
		return patchIDtoPatchCollectionMap;
	}
	
	/***********************Setters and getters***********************/

	public Dimension getDimension() {
		// TODO Auto-generated method stub
		return new Dimension(XLENGTH, YLENGTH);
	}

	public ArrayList<Seagrass> getPopulation() {
		return population;
	}
	
//	private void setOutputFile(String fileName){
//		try {
//			System.setOut(new PrintStream(new File(fileName)));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

}
