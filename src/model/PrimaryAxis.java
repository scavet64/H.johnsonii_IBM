package model;

/**
 * This class represents the PrimaryAxis growth strategy.
 * The values were calculated using image analysis on the H.johnsonii experimental growth maps.
 * @author Vincent Scavetta
 * @version 3/22/16
 */
public class PrimaryAxis extends GrowthAxis {
	
	private GrowthAxis nextAxis = new SecondaryAxis();
	
	public PrimaryAxis() {
		//branchingMean, BranchingSD, nonbranchingMean, nonbranchingSD, Units
		super(89.27, 26.3, 0, 10.35, GrowthAxis.DEGREES);
	}

	@Override
	public GrowthAxis getNextAxis() {
		// TODO Auto-generated method stub
		return nextAxis;
	}

	@Override
	public double getDaysAdded(double lightValueForDay) {
		double daysAdded = (0.00005 * (lightValueForDay * lightValueForDay)) - (0.0289 * lightValueForDay) + 8.7137;
		daysAdded += rng.nextGaussian() + 3.304;
		
		return daysAdded;
	}

}
