package model;

import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import controller.Simulation;

public class Patch {
	
	private Queue<Seagrass> patch;
	private int patchID;
	
	private double densityMean;
	private double densityStdev;
	
	private double lightMean;
	private double lightStdev;
	
	private double depthMean;
	private double depthStdev;
	
	
	public Patch(int patchID){
		patch = new LinkedBlockingQueue<Seagrass>();
		this.patchID = patchID;
	}
	
	public Patch(Queue<Seagrass> patch){
		this.patch = patch;
	}
	
	public boolean add(Seagrass seagrass){
		return patch.add(seagrass);
	}
	
	public Seagrass remove(){
		return patch.remove();
	}
	
	public int getSize(){
		return patch.size();
	}
	
	private HashSet<Cell> getCellsFromPatch(Field field){
		HashSet<Cell> patchCells = new HashSet<Cell>();
		for(Seagrass seagrass: patch){
			patchCells.add(field.getCellFromLocation(seagrass.getLocation()));
		}
		return patchCells;
	}
	
	public void calculateMeansAndStdevsForPatch(Field field){
		
		//first get the cells corresponding to the patch
		HashSet<Cell> patchCells = getCellsFromPatch(field);
		int numberOfCellsForPatch = patchCells.size();
		
		//create the arrays to hold the cell's respected data
		//then iterate over collection of cells and calculate means and stdev in each cell
		double[] patchDensities = new double[numberOfCellsForPatch];
		double[] patchLight = new double[numberOfCellsForPatch];
		double[] patchDepth = new double[numberOfCellsForPatch];
		
		double densitySum = 0.0;
		double lightSum = 0.0;
		double depthSum = 0.0;
		
		int index = 0;
		
		//System.out.print("PATCH: Density Array =");
		//System.out.println("PATCH: CellArea = " + Simulation.CELL_AREA);
		for(Cell cell: patchCells){
			//density = number of nodes / area of cell
			double density = cell.getNumberOfNodes() / Simulation.CELL_AREA;
			
			//System.out.print(density + " " + "(" + cell.getNumberOfNodes() + ") ,");
			
			double light = cell.getSeaFloorlight();
			double depth = cell.getWaterDepth();
			
			//add density, light, and depth to respected sums
			densitySum += density;
			lightSum += light;
			depthSum += depth;
			
			//add  to collection to be processed later
			patchDensities[index] = density;
			patchLight[index] = light;
			patchDepth[index] = depth;
			
			index++;
		}
		//System.out.println();
		
		
		
		//assign each mean
		densityMean = densitySum/numberOfCellsForPatch;
		lightMean = lightSum/numberOfCellsForPatch;
		depthMean = depthSum/numberOfCellsForPatch;
		
		//calculate stdev
		double runningDensityNumeratorSum = 0;
		double runningLightNumeratorSum = 0;
		double runningDepthNumeratorSum = 0;
		double difference = 0;
		for(int i = 0; i < patchDensities.length; i++){
			//density
			difference = patchDensities[i] - densityMean;
			runningDensityNumeratorSum += (difference*difference);
			
			//light
			difference = patchLight[i] - lightMean;
			runningLightNumeratorSum += (difference*difference);
			
			//Depth
			difference = patchDepth[i] - depthMean;
			runningDepthNumeratorSum += (difference*difference);
			
		}
		
		//assign the stdev for each attribute
		densityStdev = Math.sqrt(runningDensityNumeratorSum/patchDensities.length);
		lightStdev = Math.sqrt(runningLightNumeratorSum/patchDensities.length);
		depthStdev = Math.sqrt(runningDepthNumeratorSum/patchDensities.length);
	}
	
	@Override
	public String toString(){
		String s = patchID + "\t" + patch.size()
					+ "\t" + densityMean + "\t" + densityStdev 
					+ "\t" + lightMean + "\t" + lightStdev
					+ "\t" + depthMean + "\t" + depthStdev;
		
		return s;
		
	}
	
	/***********************Setters and getters***********************/

	/**
	 * @return the patch population's size
	 */
	public int getPatchPopulationSize(){
		return patch.size();
	}
	
	/**
	 * @return the patch
	 */
	public Queue<Seagrass> getPatch() {
		return patch;
	}

	/**
	 * @param patch the patch to set
	 */
	public void setPatch(Queue<Seagrass> patch) {
		this.patch = patch;
	}

	/**
	 * @return the patchID
	 */
	public int getPatchID() {
		return patchID;
	}

	/**
	 * @param patchID the patchID to set
	 */
	public void setPatchID(int patchID) {
		this.patchID = patchID;
	}

	/**
	 * @return the densityMean
	 */
	public double getDensityMean() {
		return densityMean;
	}

	/**
	 * @param densityMean the densityMean to set
	 */
	public void setDensityMean(double densityMean) {
		this.densityMean = densityMean;
	}

	/**
	 * @return the densityStdev
	 */
	public double getDensityStdev() {
		return densityStdev;
	}

	/**
	 * @param densityStdev the densityStdev to set
	 */
	public void setDensityStdev(double densityStdev) {
		this.densityStdev = densityStdev;
	}

	/**
	 * @return the lightMean
	 */
	public double getLightMean() {
		return lightMean;
	}

	/**
	 * @param lightMean the lightMean to set
	 */
	public void setLightMean(double lightMean) {
		this.lightMean = lightMean;
	}

	/**
	 * @return the lightStdev
	 */
	public double getLightStdev() {
		return lightStdev;
	}

	/**
	 * @param lightStdev the lightStdev to set
	 */
	public void setLightStdev(double lightStdev) {
		this.lightStdev = lightStdev;
	}

	/**
	 * @return the depthMean
	 */
	public double getDepthMean() {
		return depthMean;
	}

	/**
	 * @param depthMean the depthMean to set
	 */
	public void setDepthMean(double depthMean) {
		this.depthMean = depthMean;
	}

	/**
	 * @return the depthStdev
	 */
	public double getDepthStdev() {
		return depthStdev;
	}

	/**
	 * @param depthStdev the depthStdev to set
	 */
	public void setDepthStdev(double depthStdev) {
		this.depthStdev = depthStdev;
	}
	
	
}
