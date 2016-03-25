package model;

public class SimulationOptions {
	
	//Default Options
	private int xLength = 10;				//Distance along the shoreline 	(was NROW)
	private int yLength = 10;				//Distance from the shoreline	(was NCOL)
	private int numberDays = 200;			//number of days to run the simulation
	private int numberYears = 0;			//number of years to run the simulation
	private int numRecruits = 0;			//number of starting nodes
	private int maxNodes = 500000;			//Maximum number of nodes for the simulation
	private static SimulationOptions singleInstance;

	/**
	 * TODO: Think about using a shutdown hook to save the settings when finished so it would be possible to load on initialization
	 */
	private SimulationOptions(){
		
	}
	
	public static SimulationOptions getInstance(){
		if(singleInstance == null){
			singleInstance = new SimulationOptions();
		}
		return singleInstance;
	}
	
	public boolean applyModification(Attribute ea, double value){
		boolean success = true;
		switch(ea){
		case NumDays:
			this.numberDays = (int) value;
			break;
		case XLength:
			this.xLength = (int) value;
			break;
		case YLength:
			this.yLength = (int) value;
			break;
		case NumYears:
			this.numberYears = (int) value;
			break;
		case NumRecruits:
			this.numRecruits = (int) value;
			break;
		case MaxNodes:
			this.maxNodes = (int) value;
			break;
		default:
			success = false;
			break;
		}
		return success;
	}

	/**
	 * @return the xLength
	 */
	public int getxLength() {
		return xLength;
	}

	/**
	 * @param xLength the xLength to set
	 */
	public void setxLength(int xLength) {
		this.xLength = xLength;
	}

	/**
	 * @return the yLength
	 */
	public int getyLength() {
		return yLength;
	}

	/**
	 * @param yLength the yLength to set
	 */
	public void setyLength(int yLength) {
		this.yLength = yLength;
	}

	/**
	 * @return the numberDays
	 */
	public int getNumberDays() {
		return numberDays;
	}

	/**
	 * @param numberDays the numberDays to set
	 */
	public void setNumberDays(int numberDays) {
		this.numberDays = numberDays;
	}

	/**
	 * @return the numberYears
	 */
	public int getNumberYears() {
		return numberYears;
	}

	/**
	 * @param numberYears the numberYears to set
	 */
	public void setNumberYears(int numberYears) {
		this.numberYears = numberYears;
	}

	/**
	 * @return the numRecruits
	 */
	public int getNumRecruits() {
		return numRecruits;
	}

	/**
	 * @param numRecruits the numRecruits to set
	 */
	public void setNumRecruits(int numRecruits) {
		this.numRecruits = numRecruits;
	}

	/**
	 * @return the singleInstance
	 */
	public SimulationOptions getSingleInstance() {
		return singleInstance;
	}

	/**
	 * @param singleInstance the singleInstance to set
	 */
	public void setSingleInstance(SimulationOptions singleInstance) {
		this.singleInstance = singleInstance;
	}

	public int getMaxNodes() {
		return maxNodes;
	}

	
	
}
