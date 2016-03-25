package model;

import java.awt.geom.Line2D;

/**
 * The Seagrass class for the Halophila johnsonii individual based model
 * This class represents the attributes that a seagrass individual would have
 * as well as some basic functionality specific to seagrass. This class could
 * be extended in the future to include multiple types of seagrass with different
 * specifics.
 * 
 * @author Vincent Scavetta
 * @version 2/11/16
 *
 * TODO:
 *
 */
public class Seagrass {
	
	//public static variables 
	public static final PrimaryAxis PRIMARYAXIS = new PrimaryAxis();
	public static final SecondaryAxis SECONDARYAXIS = new SecondaryAxis();
	
	private final int ID;							//id number assigned to each node when created
	private int age;								//age in days since creation of each node
	private final int dayBorn;						//day a node is "born"
	private int dayDied;							//day a node "dies"
	private Location location;						//Location where the seagrass is located
//	private double xCords;							//distance in meters in x direction from left bottom corner of grid
//	private double yCords;							//distance in meters in y direction from left bottom corner of grid
	private boolean isApical;						//true if node is on the apical stem
	private boolean hasBranched;					//true if has branched
	private final double angleOfCreation;			//angle in radians of node when created - 90 degrees = 1.57 radians
	private double distanceForNewNode;				//distance according to growth that the new node will be from the parent node
	private final int motherID;						//id of the mother
	private double developmentProgress;				//progress until a new node is created
	//private Line2D.Double parentalConnection;		//connection to its parent
	private Location childLocation;					//location of its apical child
	private Location branchChildLocation;			//location of its branched child
	private GrowthAxis growthAxis;					//the axis of growth for this node 
	private int patchID;							//the ID of its patch


	/**
	 * @param id Seagrass ID
	 * @param dayBorn Date the seagrass was born
	 * @param location Location the seagrass resides
	 * @param isApical true if the seagrass node is apical
	 * @param angleOfCreation the angle of creation relative to its parent's angle
	 * @param motherID ID of the plants mother
	 */
	public Seagrass(int ID, int dayBorn, Location location, boolean isApical, double angleOfCreation, int motherID, GrowthAxis growthAxis) {
		super();
		this.ID = ID;
		this.dayBorn = dayBorn;
		this.location = location;
		this.isApical = isApical;
		this.angleOfCreation = angleOfCreation;
		this.motherID = motherID;
		
		this.distanceForNewNode = 0.0;
		this.dayDied = Integer.MIN_VALUE;
		this.hasBranched = false;
		this.developmentProgress = 0.0;
		this.age = 0;
		//this.parentalConnection = null;
		this.growthAxis = growthAxis;
	}
	
	public void growth(double lightValueForDay){
		double daysToNode = develop(lightValueForDay);
		developmentProgress = developmentProgress + (1/daysToNode);
		
		double mmAdded = distance(lightValueForDay);
		distanceForNewNode = distanceForNewNode + (mmAdded/1000.0);
		age++;
	}
	
	/**
	 * Attempting to refactor
	 * @return newly created Seagrass
	 */
	public Seagrass createChild(int XLENGTH, int YLENGTH, int runningIDCounter, int dayCounter){
		Seagrass child = null;
		double theta = growthAxis.getTheta(isApical, angleOfCreation);
		
		double adj = Math.cos(theta) * distanceForNewNode;		//distance on the X axis
		double opp = Math.sin(theta) * distanceForNewNode;		//distance on the Y axis
		
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
		
		//Creates a new location object for the child
		Location newLoc = new Location(newNodeX, newNodeY);
		
		//declare the GrowthAxis object and ensure the child will have the correct GrowthAxis
		GrowthAxis childGrowthAxis;
		if(growthAxis instanceof SecondaryAxis){
			childGrowthAxis = growthAxis;
		} else {
			childGrowthAxis = growthAxis.getNextAxis();
		}
		
		//create the seagrass object
		child = new Seagrass(runningIDCounter, dayCounter, newLoc, true, theta, ID, childGrowthAxis);
		
		//Modifies the current status of the seagrass and assigns the child's location for GUI use.
		if(isApical){
			isApical = false;
			childLocation = newLoc;
		} else {
			hasBranched = true;
			branchChildLocation = newLoc;
		}
		
		//sets the development progress back to 0 to start the process over again
		developmentProgress = 0.0;

		return child;
	}
	
//	/**
//	 * Depreciated as of 3/18/2016 - refactored age++ into growth method
//	 * Increments the age of the node.
//	 * @param availableLight
//	 * @return
//	 */
//	public void incrementAge(){
//		age++;
//	}
	
	private double develop(double availableLight){
//		  This function relates development time towards producing a
//		      new node to the light level in a cell
//		
//		 TableCurve D:\seagrass\days.f90 Dec 18, 2007 3:52:04 PM 
//		  
//		 X=  
//		 Y=  
//		 Eqn# 8002  Exponential(a,b,c) 
//		 r2=0.999851595076407D0 
//		 r2adj=0.999554785229221D0 
//		 StdErr=0.0693848378347615D0 
//		 Fval=3368.660455695716D0 
//		 a= 8.151507772603294D0 
//		 b= 6.849203199969538D0 
//		 c= 14.26707601933104D0 
//		 Constraint: c<>0 
//		-----------------------------------------------------------
//		  REAL*8 x,y
//		  y=8.151507772603294D0+6.849203199969538D0*DEXP(-x/&
//		   &14.26707601933104D0) 
//		  develop=y
//		  RETURN
//		END
		double y = 8.151507772603294 + 6.849203199969538 * Math.exp((-availableLight)/14.26707601933104);
		return y;
	
	}


	private double distance(double availableLight){
//		!-----------------------------------------------------------
//		!    This function calculates the internode distance when a
//		!        new node is created.  Its value is relative to light
//		!         in the cell
//		!
//		! TableCurve D:\seagrass\distance.f90 Dec 18, 2007 4:13:25 PM 
//		!  
//		! X=  
//		! Y=  
//		! Eqn# 22  lny=a+bx 
//		! r2=0.9856678844740406D0 
//		! r2adj=0.9570036534221219D0 
//		! StdErr=0.3665563845520483D0 
//		! Fval=137.5467400731912D0 
//		! a= 2.715230365615488D0 
//		! b= 0.003246713178956122D0 
//		!-----------------------------------------------------------
//		  REAL*8 x,y
//		  y=2.715230365615488D0+x*(0.003246713178956122D0) 
//		  y=DEXP(y) 
//		  distance=y
//		  RETURN
		
		double y = 2.715230365615488 + availableLight * 0.003246713178956122;
		return y;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Seagrass [id=" + ID + ", age=" + age + ", dayBorn=" + dayBorn + ", dayDied=" + dayDied + ", location="
				+ location + ", isApical=" + isApical + ", hasBranched=" + hasBranched + ", angleOfCreation="
				+ angleOfCreation + ", distanceForNewNode=" + distanceForNewNode + ", motherID=" + motherID
				+ ", developmentProgress=" + developmentProgress + "]";
	}
	
	/***********************Setters and getters***********************/
	
	
	/**
	 * @return the branchChildLocation
	 */
	public Location getBranchChildLocation() {
		return branchChildLocation;
	}

	/**
	 * @param branchChildLocation the branchChildLocation to set
	 */
	public void setBranchChildLocation(Location branchChildLocation) {
		this.branchChildLocation = branchChildLocation;
	}

	/**
	 * @return the childLocation
	 */
	public Location getChildLocation() {
		return childLocation;
	}

	/**
	 * @param childLocation the childLocation to set
	 */
	public void setChildLocation(Location childLocation) {
		this.childLocation = childLocation;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}


	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}


	/**
	 * @return the dayDied
	 */
	public int getDayDied() {
		return dayDied;
	}


	/**
	 * @param dayDied the dayDied to set
	 */
	public void setDayDied(int dayDied) {
		this.dayDied = dayDied;
	}


	/**
	 * @return the isApical
	 */
	public boolean isApical() {
		return isApical;
	}


	/**
	 * @param isApical the isApical to set
	 */
	public void setApical(boolean isApical) {
		this.isApical = isApical;
	}


	/**
	 * @return the hasBranced
	 */
	public boolean isHasBranched() {
		return hasBranched;
	}


	/**
	 * @param hasBranced the hasBranced to set
	 */
	public void setHasBranched(boolean hasBranched) {
		this.hasBranched = hasBranched;
	}


	/**
	 * @return the distanceFromMother
	 */
	public double getDistanceFromMother() {
		return distanceForNewNode;
	}


	/**
	 * @param distanceFromMother the distanceFromMother to set
	 */
	public void setDistanceFromMother(double distanceFromMother) {
		this.distanceForNewNode = distanceFromMother;
	}


	/**
	 * @return the developmentProgress
	 */
	public double getDevelopmentProgress() {
		return developmentProgress;
	}


	/**
	 * @param developmentProgress the developmentProgress to set
	 */
	public void setDevelopmentProgress(double developmentProgress) {
		this.developmentProgress = developmentProgress;
	}


	/**
	 * @return the id
	 */
	public int getID() {
		return ID;
	}


	/**
	 * @return the dayBorn
	 */
	public int getDayBorn() {
		return dayBorn;
	}


	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}


	/**
	 * @return the angleOfCreation
	 */
	public double getAngleOfCreation() {
		return angleOfCreation;
	}


	/**
	 * @return the motherID
	 */
	public int getMotherID() {
		return motherID;
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

}
