package seagrass_Model_V1.Model;

public class Field {

	private Cell[][] field;
	private int xlength;
	private int ylength;
	
	/**
	 * Default field constructor.
	 */
	public Field() {
		this.xlength = 50;
		this.ylength = 150;
		field = new Cell[xlength][ylength];
	}
	
	/**
	 * Field constructor that will create a 2D array of cells
	 * 
	 * @param xlength Distance from the shore
	 * @param ylength Distance along the shoreline
	 */
	public Field(int xlength, int ylength) {
		this.xlength = xlength;
		this.ylength = ylength;
		field = new Cell[xlength][ylength];
	}
	
	public void addCell(Cell cellToAdd, int xlength, int ylength){
		field[xlength][ylength] = cellToAdd;
	}
	
	public Cell getCellFromCords(int xlength, int ylength){
		return field[xlength][ylength];
	}
	
	public Cell getCellFromLocation(Location location){
		return getCellFromCords(location.getIntegerX(), location.getIntegerY());
	}

}
