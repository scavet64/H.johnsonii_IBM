package seagrass_Model_V1.Model;

/**
 * The Cell class for the Halophila Johnsonii individual based model.
 * This class represents what characteristics a cell will have in our model.
 * In this model, a cell represents a 1m x 1m square that can have 
 * variable environmental factors such as water level and seafloor light.
 * 
 * @author Vincent Scavetta
 * @version 2/11/16
 *
 */
public class Cell {
	
	private double elevation;						//elevation in meters of each cell
	private double waterLevel;						//water level each day in meters relative to some elevation?
	private double seaFloorlight;					//fraction of incident light at the bottom
	private double surfaceLight;					//light at the surface of the grid - even with the shoreline (1.0)
	private double temperature;						//water temperature for entire grid in degree C
	private int numberOfNodes;						//Number of nodes within the cell
	private double bioMass;							//normalized (0 to 1) effect on light hitting the bottom
	
	private double waterDepth;						//water depth in each cell each day from water level minus elevation


	public Cell(){
		this.elevation = 0;
		this.waterDepth = 0;
		this.seaFloorlight = 0;
		this.temperature = 0;
		this.numberOfNodes = 0;
		this.bioMass = 0;
		this.waterLevel = 0;
	}

	/**
	 * Generates the cell's water depth using the elevation and water level.
	 * This method must only be called after elevation and waterLevel have been set.
	 */
	public void generateWaterDepth() {
		this.waterDepth = this.elevation - this.waterLevel;
	}

	/**
	 * @return the elevation
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	/**
	 * @return the waterLevel
	 */
	public double getWaterLevel() {
		return waterLevel;
	}

	/**
	 * @param waterLevel the waterLevel to set
	 */
	public void setWaterLevel(double waterLevel) {
		this.waterLevel = waterLevel;
	}

	/**
	 * @return the seaFloorlight
	 */
	public double getSeaFloorlight() {
		return seaFloorlight;
	}

	/**
	 * @param seaFloorlight the seaFloorlight to set
	 */
	public void setSeaFloorlight(double seaFloorlight) {
		this.seaFloorlight = seaFloorlight;
	}

	/**
	 * @return the surfaceLight
	 */
	public double getSurfaceLight() {
		return surfaceLight;
	}

	/**
	 * @param surfaceLight the surfaceLight to set
	 */
	public void setSurfaceLight(double surfaceLight) {
		this.surfaceLight = surfaceLight;
	}

	/**
	 * @return the temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the numberOfNodes
	 */
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	/**
	 * @param numberOfNodes the numberOfNodes to set
	 */
	public void setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

	/**
	 * @return the bioMass
	 */
	public double getBioMass() {
		return bioMass;
	}

	/**
	 * @param bioMass the bioMass to set
	 */
	public void setBioMass(double bioMass) {
		this.bioMass = bioMass;
	}

	/**
	 * @return the waterDepth
	 */
	public double getWaterDepth() {
		return waterDepth;
	}

	
	

}
