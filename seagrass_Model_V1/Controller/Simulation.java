package seagrass_Model_V1.Controller;

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
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import seagrass_Model_V1.Model.Cell;
import seagrass_Model_V1.Model.Field;
import seagrass_Model_V1.Model.Location;
import seagrass_Model_V1.Model.Seagrass;

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
	
	private final int XLENGTH = 150;				//Distance along the shoreline 	(was NROW)
	private final int YLENGTH = 50;					//Distance from the shoreline	(was NCOL)
	private final int MAX_NODES = 500000;			//Maximum number of nodes for the simulation
	private final double SEAFLOOR_SLOPE = 0.01;		//The slope of the seafloor from the shore
	private final double LIGHT_ATTENUATION = 0.035;	//light attenuation coefficient due to everything but shading by other seagrasses
	private final Random rng = new Random();		//Random number generator
	
	//file writers
	private PrintStream LIGHTOUTPUT;
	private PrintStream NODEOUTPUT;
	private PrintStream ELEVATIONOUTPUT;
	private PrintStream BIOMASSOUTPUT;
	private PrintStream DEPTHOUTPUT;
	private PrintStream DAILYOUTPUT;
	
	private ArrayList<Seagrass> population;			//population of seagrass
	private ArrayList<Seagrass> perishedPopulation;	//dead population of seagrass
	private int[] nodesCreateOnDay;					//int array that holds the number of nodes born on the given day(index)
	//private Cell[][] field;
	
	private int dayCounter;							//counts the days of the simulation
	private int yearCounter;						//counts the years of the simulation
	private int runningIDCounter;					//holds the value that represents the next available id 
	private int numberOfRecuits;					//number of starting nodes
	private Field field;							//Field that holds all of our individual nodes
	private int numNodesCreatedToday;				//
	

	
	
	public Simulation() {
		field = new Field(XLENGTH,YLENGTH);
		population = new ArrayList<Seagrass>();
		perishedPopulation = new ArrayList<Seagrass>();
		//field = new Cell[XLENGTH][YLENGTH];
		dayCounter = 0;
		yearCounter = 0;
		numberOfRecuits = 5;
		numNodesCreatedToday = 0;
	
		setPrintStreams();
		
		//System.setOut(new BufferedWriter(new FileWriter(new File("Output.txt"))));
			
//		try {
//			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt"))));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void setPrintStreams() {
		try {
			LIGHTOUTPUT = new PrintStream(new File("Light.txt"));			
			NODEOUTPUT = new PrintStream(new File("Nodes.txt"));			
			ELEVATIONOUTPUT = new PrintStream(new File("Elevation.txt"));			
			BIOMASSOUTPUT = new PrintStream(new File("biomass.txt"));
			DEPTHOUTPUT = new PrintStream(new File("Depth.txt"));
			DAILYOUTPUT = new PrintStream(new File("Daily.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will run the simulation for the amount of time designated by the user.
	 * TODO:Implement running of the simulation.
	 * 		Implement growth of node
	 * @throws Exception 
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
			dayCounter++;
		}
		
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
			population.add(new Seagrass(runningIDCounter, 1, randLoc, true, 0.0, runningIDCounter));
			runningIDCounter++;
		}
		
	}

	/**
	 * TODO:get cords for node
	 * 		get cell respective of cords
	 * 		get light for cell on this day
	 * 		pass light to the node's growth method
	 * 		if return true == call method that makes new node
	 * 		else continue
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
		
		for(int i = 0; i < population.size(); i++){
			//Get the current node to work with and its location
			Seagrass node = population.get(i);
			Location location = node.getLocation();
			
			//get the cell the node is located in
			Cell nodesCell = field.getCellFromLocation(node.getLocation());
			
			//call the nodes growth method and pass it the cell's light
			node.growth(nodesCell.getSeaFloorlight());
			
			//check to see if the development progress has reached completion.  >= 1 is complete
			if(node.getDevelopmentProgress() >= 1 && (node.isApical() || !node.isHasBranched())){
				
				
				//if the population reaches the max, stop the adding process
				if(population.size() >= MAX_NODES){
					System.out.println("reached Max Nodes");
					throw new Exception();
				} else {
					
					//time to make a new node!
					double theta;
					
					//if the node is apical, continue the path of growth
					if(node.isApical()){
						theta = node.getAngleOfCreation();
					} else {
						theta = rng.nextDouble() * 2.0 * Math.PI;
					}
					
					//get the adjacent and opposite lengths for theta using the distance from the mother.
					double adj = Math.cos(theta) * node.getDistanceFromMother();		//distance on the X axis
					double opp = Math.sin(theta) * node.getDistanceFromMother();		//distance on the Y axis
					
					if(dayCounter == 15){
						//print some information
					}
					
					//add the opp and adj to the mothers location to create the new nodes coordinates 
					double newNodeX = location.getxLocation() + adj;
					double newNodeY = location.getyLocation() + opp;
					
					//'bounce' the node away from the edge of the field.
					//unsure if this will change or stay in.
					if(newNodeX > XLENGTH || newNodeX < 0){
						newNodeX = newNodeX + (2 * (-adj));
					}
					if(newNodeY > YLENGTH || newNodeY < 0){
						newNodeY = newNodeY + (2 * (-opp));
					}
					
					Location newLoc = new Location(newNodeX, newNodeY);
					newNodesForTheDay.add(new Seagrass(runningIDCounter, dayCounter, newLoc, true, theta, node.getId()));
					
					if(node.isApical()){
						node.setApical(false);
					} else if(!node.isHasBranched()){
						node.setHasBranched(true);
					}
					node.setDevelopmentProgress(0.0);
					runningIDCounter++;
					numNodesCreatedToday++;
				}
			}
			
			//if the node is older than 50 days old, the node dies of old age
			if(node.getAge() > 50){
				node.setDayDied(dayCounter);
				perishedNodesForTheDay.add(node);
			} else {
				node.incrementAge();
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
				
				//old style of printing
//				System.out.println("CellX: " + currentX + "CellY: " + currentY + "Elevation: " + cell.getElevation() +
//						"WaterDepth: " + cell.getWaterDepth());
//				ELEVATIONOUTPUT.println("CellX: " + currentX + "CellY: " + currentY + "Elevation: " + cell.getElevation());
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
		cell.setBioMass(0.5);
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
	
//	private void setOutputFile(String fileName){
//		try {
//			System.setOut(new PrintStream(new File(fileName)));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
