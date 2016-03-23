package seagrass_Model_V1.Model;

public class SimulationOptions {
	
	private int xLength = 150;
	private int yLength = 50;
	private int numberDays = 100;
	private int numberYears = 0;
	private int numRecruits = 5;
	private static SimulationOptions singleInstance;

	private SimulationOptions(){
		
	}
	
	public static SimulationOptions getInstance(){
		if(singleInstance == null){
			singleInstance = new SimulationOptions();
		}
		return singleInstance;
	}
	
	public void applyModification(Attribute ea, double value){
		boolean success = true;
		System.out.println(ea);
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
		default:
			success = false;
			break;
		}
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

	
	
}
