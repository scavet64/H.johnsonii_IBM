package model;

import java.util.Random;

/**
 * This class is the base class that represents the overall growth strategy of the seagrass.
 * @author Vincent Scavetta
 * @version 3/22/16
 */
public abstract class GrowthAxis {
	
	protected final Random rng = new Random();		//Random number generator
	public static final boolean RADIANS = true;
	public static final boolean DEGREES = false;
	
	//Angles in radians
	private double branchingMean;
	private double branchingSD;
	private double nonBranchingMean;
	private double nonBranchingSD;
	


	public GrowthAxis(double branchingMean, double branchingSD, double nonBranchingMean, double nonBranchingSD, boolean isRadians) {
		if(isRadians){
			this.branchingMean = branchingMean;
			this.branchingSD = branchingSD;
			this.nonBranchingMean = nonBranchingMean;
			this.nonBranchingSD = nonBranchingSD;
		} else {
			//must convert into radians
			double piOver180 = (Math.PI / 180);
			
			this.branchingMean = branchingMean * piOver180;
			this.branchingSD = branchingSD * piOver180;
			this.nonBranchingMean = nonBranchingMean * piOver180;
			this.nonBranchingSD = nonBranchingSD * piOver180;
		}
	}

	/**
	 * Returns a random theta value based upon the data collected during the experimental analysis of the seagrass.
	 * This methods calls different private methods depending on the passed in boolean isApical. If the seagrass is
	 * an apical node, the angle is treated differently when compared to a non apical node.
	 * @param isApical true if the node is apical
	 * @param creationAngle the angle the parent was created.
	 * @return random theta value based upon real data.
	 */
	public double getTheta(boolean isApical, double creationAngle){
		double theta;
		
		if(isApical){
			//TODO properly implement nonbranching
			//theta = creationAngle;				//old way, continue along parental growth.
			theta = getRandomNonBranch(creationAngle);
		} else {
			//branching angle
			//theta = getRandomBranchingAngle();	//old way, completely random based on nothing.
			theta = getRandomBranchingAngle(creationAngle);
			if(rng.nextInt(100) >= 50){
				theta *= -1;
			}
		}
		
		return theta;
	}
	
	/**
	 * returns a random theta based upon the mean and standard deviation for the
	 * current growthAxis
	 * @param creationAngle parent's creation angle
	 * @return random nonbranching angle
	 */
	private double getRandomNonBranch(double creationAngle){
		double branchingAngle = ((rng.nextGaussian() * nonBranchingSD) + nonBranchingMean) + creationAngle;
		return branchingAngle;
	}
	
	/**
	 * returns a random theta based upon the mean and standard deviation for the
	 * current growthAxis
	 * @param creationAngle
	 * @return random branching angle
	 */
	private double getRandomBranchingAngle(double creationAngle) {
		double branchingAngle = ((rng.nextGaussian() * branchingSD) + branchingMean) + creationAngle;
		return branchingAngle;
	}

	/**
	 * returns the next growth axis based on the current axis
	 * TODO: consider not making this abstract and concrete instead.
	 * @return
	 */
	public abstract GrowthAxis getNextAxis();

	/**
	 * Gets completely random theta
	 * @return
	 */
	private double getRandomBranchingAngle(){
		return rng.nextDouble() * 2.0 * Math.PI;
	}

	public abstract double getDaysAdded(double lightValueForDay);
	
	
}
