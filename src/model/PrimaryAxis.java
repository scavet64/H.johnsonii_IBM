package model;

public class PrimaryAxis extends GrowthAxis {
	
	public PrimaryAxis(){
		this.branchingMean 		= 0;
		this.branchingSD 		= 0;
		this.nonBranchingMean 	= 0;
		this.nonBranchingSD 	= 0;
	}

	@Override
	public GrowthAxis getNextAxis() {
		// TODO Auto-generated method stub
		return new SecondaryAxis();
	}

}
