package seagrass_Model_V1.Model;

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
 * Write growth method
 * implement aging
 *
 */
public class Seagrass {
	
	private int id;							//id number assigned to each node when created
	private int age;						//age in days since creation of each node
	private int dayBorn;					//day a node is "born"
	private int dayDied;					//day a node "dies"
	private Location location;				//Location where the seagrass is located
//	private double xCords;					//distance in meters in x direction from left bottom corner of grid
//	private double yCords;					//distance in meters in y direction from left bottom corner of grid
	private boolean isApical;				//true if node is on the apical stem
	private boolean hasBranched;			//true if has branched
	private double angleOfCreation;			//angle in radians of node when created - 90 degrees = 1.57 radians
	private double distanceForNewNode;		//distance according to growth that the new node will be from the parent node
	private int motherID;					//id of the mother
	private double developmentProgress;		//progress until a new node is created


	/**
	 * @param id
	 * @param dayBorn
	 * @param location
	 * @param isApical
	 * @param angleOfCreation
	 * @param motherID
	 */
	public Seagrass(int id, int dayBorn, Location location, boolean isApical, double angleOfCreation, int motherID) {
		super();
		this.id = id;
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
	}
	
	public void growth(double lightValueForDay){
		double daysToNode = develop(lightValueForDay);
		developmentProgress = developmentProgress + (1/daysToNode);
		
		double mmAdded = distance(lightValueForDay);
		distanceForNewNode = distanceForNewNode + (mmAdded/1000.0);
	}
	
	public void incrementAge(){
		age++;
	}
	
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
		return "Seagrass [id=" + id + ", age=" + age + ", dayBorn=" + dayBorn + ", dayDied=" + dayDied + ", location="
				+ location + ", isApical=" + isApical + ", hasBranched=" + hasBranched + ", angleOfCreation="
				+ angleOfCreation + ", distanceForNewNode=" + distanceForNewNode + ", motherID=" + motherID
				+ ", developmentProgress=" + developmentProgress + "]";
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
	public int getId() {
		return id;
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

}
