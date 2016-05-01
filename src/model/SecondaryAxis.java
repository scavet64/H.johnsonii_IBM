package model;

/**
 * This class represents the SecondaryAxis growth strategy.
 * The values were calculated using image analysis on the H.johnsonii experimental growth maps.
 * @author Vincent Scavetta
 * @version 3/22/16
 */
public class SecondaryAxis extends GrowthAxis {

	public SecondaryAxis() {
		//branchingMean, BranchingSD, nonbranchingMean, nonbranchingSD, Units
		super(72.00, 33.62, 0, 21.19, GrowthAxis.DEGREES);
	}

	@Override
	public GrowthAxis getNextAxis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDaysAdded(double lightValueForDay) {
		double daysAdded = (0.0004 * (lightValueForDay * lightValueForDay)) - (0.1015 * lightValueForDay) + 14.487;
		daysAdded += rng.nextGaussian() + 5.530;
		
		return daysAdded;
	}

}
