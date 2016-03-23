package model;

import java.util.Random;

/**
 * This class is the base class that represents the overall growth strategy of the seagrass.
 * @author Vincent Scavetta
 * @version 3/22/16
 */
public abstract class GrowthAxis {
	
	private final Random rng = new Random();		//Random number generator
	
	private double branchingMean;
	private double branchingSD;
	private double nonBranchingMean;
	private double nonBranchingSD;
	


	public GrowthAxis(double branchingMean, double branchingSD, double nonBranchingMean, double nonBranchingSD) {
		this.branchingMean = branchingMean;
		this.branchingSD = branchingSD;
		this.nonBranchingMean = nonBranchingMean;
		this.nonBranchingSD = nonBranchingSD;
	}

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
	 * Gets completely random theta
	 * @return
	 */
	private double getRandomBranchingAngle(){
		return rng.nextDouble() * 2.0 * Math.PI;
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

	public abstract GrowthAxis getNextAxis();
	
	
}
