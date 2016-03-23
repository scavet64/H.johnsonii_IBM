package seagrass_Model_V1.Model;

public class Location {
	
	private double xLocation;
	private double yLocation;
	
	
	/**
	 * @param xlocation
	 * @param ylocation
	 */
	public Location(double xLocation, double yLocation) {
		super();
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}
	
    /**
     * Implement content equality.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return xLocation == other.getxLocation() && yLocation == other.getyLocation();
        }
        else {
            return false;
        }
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Location [xLocation=" + xLocation + ", yLocation=" + yLocation + "]";
	}

	/**
	 * @return the xLocation
	 */
	public double getxLocation() {
		return xLocation;
	}

	/**
	 * @param xLocation the xLocation to set
	 */
	public void setxLocation(double xLocation) {
		this.xLocation = xLocation;
	}

	/**
	 * @return the yLocation
	 */
	public double getyLocation() {
		return yLocation;
	}

	/**
	 * @param yLocation the yLocation to set
	 */
	public void setyLocation(double yLocation) {
		this.yLocation = yLocation;
	}
	
	/**
	 * returns the integer version of the x location
	 * @return Integer version of x location
	 */
	public int getIntegerX(){
		return (int) xLocation;
	}
	
	/**
	 * Returns the integer version of the y location
	 * @return Integer version of Y location
	 */
	public int getIntegerY(){
		return (int) yLocation;
	}
    
    



}
