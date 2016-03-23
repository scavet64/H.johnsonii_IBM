package model;

import java.util.Random;

public abstract class GrowthAxis {
	
	private final Random rng = new Random();		//Random number generator
	
	protected double branchingMean;
	protected double branchingSD;
	protected double nonBranchingMean;
	protected double nonBranchingSD;

	public double getTheta(boolean isApical, double creationAngle){
		double theta;
		
		if(isApical){
			//TODO properly implement nonbranching
			theta = creationAngle;
		} else {
			//branching angle
			//theta = getRandomBranchingAngle(creationAngle);
			theta = getRandomBranchingAngle();
		}
		
		return theta;
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
	 * @return
	 */
	private double getRandomBranchingAngle(double creationAngle) {
		double branchingAngle = ((rng.nextGaussian() * branchingSD) + branchingMean) + creationAngle;
		return branchingAngle;
	}

	public abstract GrowthAxis getNextAxis();
	
	
}
